using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;
using MySql.Data.MySqlClient;

class Receive
{

    private static string serverIp = "172.20.10.9";
    private static string username = "geoffrey";
    private static string password = "";
    private static string databaseName = "dbtestcalculation";

    public static void Main()
    {
        var factory = new ConnectionFactory() {HostName = "172.20.10.11", UserName = "cyril", Password = "azerty"};
        using (var connection = factory.CreateConnection())
        using (var channel = connection.CreateModel())
        {
            var actualDate = DateTime.Now;
            
            var Dictionnary = new Dictionary<string, Stat>();
            
            channel.QueueDeclare(queue: "spring",
                durable: true,
                exclusive: false,
                autoDelete: false,
                arguments: null);

            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, ea) =>
            {
                var body = ea.Body;
                Device device = JsonConvert.DeserializeObject<Device>(System.Text.Encoding.Default.GetString(body));
                var message = Encoding.UTF8.GetString(body);
                Console.WriteLine(" [x] Received {0}", body);
                
                device.date = DateTime.Now;

                if (actualDate.Hour != device.date.Hour)
                {
                    Console.Write("Date " + device.date);
                }
                
                string dbConnectionString = string.Format("server={0};uid={1};pwd={2};database={3};", serverIp,
                    username, password, databaseName);

                MySqlCommand cmd = new MySqlCommand();
                MySqlConnection conn = new MySqlConnection(dbConnectionString);
                cmd.Connection = conn;
                conn.Open();

                foreach (var sensor in device.sensors)
                {
                    Console.WriteLine(sensor.uid);
                    cmd.CommandText = "INSERT INTO datas(sensor_id, value)" +
                                      " SELECT id, " + sensor.value +
                                      " FROM sensors WHERE uid = '" + sensor.uid +
                                      "'";
                    cmd.ExecuteNonQuery();
                }

                //cmd.ExecuteNonQuery();
            };
            channel.BasicConsume(queue: "spring",
                autoAck: true,
                consumer: consumer);

            Console.WriteLine(" Press [enter] to exit.");
            Console.ReadLine();
        }
    }

    public class Device
    {
        public String uid;
        public List<Sensor> sensors;
        public DateTime date;
    }

    public class Sensor
    {
        public String uid;
        public String type;
        public Double value;
    }

    public class Stat
    {
        public Double value;
        public DateTime date;
    }
}
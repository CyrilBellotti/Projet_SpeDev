using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;

namespace Calculation
{
    class Calculator
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello World!");
            Console.WriteLine(DateTime.Now.AddHours(-1).ToString("yyyy-MM-dd HH:mm:ss"));
            DateTime date1 = new DateTime(2008, 5, 1, 8, 30, 52);
            Console.WriteLine(date1);

            NormalJobs job = new NormalJobs();
            DateTime theStartDate = new DateTime(2019, 6, 30, 19, 40, 00); // DateDepart DateTime.now ????
            job.CalculationModule(theStartDate, true);

            Console.ReadLine();
        }


    }

    public class Sensors
    {
        public dynamic sensor_id { get; set; }
        public dynamic value { get; set; }
        public dynamic date { get; set; }
        public dynamic type { get; set; }
        public dynamic numberOfValue { get; set; }
    }


    public class NormalJobs
    {
        
        bool loopIsGoingToEnd = false;
        LinkActionDataBase db = new LinkActionDataBase();
        CalculationMultiThreading calc = new CalculationMultiThreading();
        TimerToUse theTimer = new TimerToUse();
        List<Sensors> theListOfCurrentSensors = null;
        public void CalculationModule(DateTime theStartDate, bool isItSimulation_old)
        {

            while (loopIsGoingToEnd == false)
            {
                DateTime theEndDate = theTimer.TheCalculationTimer(theStartDate);
                if (theEndDate < DateTime.Now)
                {
                    theListOfCurrentSensors = db.GetFromDataBase(theStartDate, theEndDate); // envoie date

                    if (theListOfCurrentSensors != null)
                    {

                        if (theListOfCurrentSensors.Count > 0)
                        {
                            theListOfCurrentSensors = calc.Calculator(theListOfCurrentSensors); // param List

                            db.PutInDataBase(theListOfCurrentSensors, theEndDate);
                            Console.WriteLine(theStartDate + "       " + theEndDate);
                        }
                    }
                    if (isItSimulation_old == false)
                    {
                        theStartDate = theEndDate;
                    }
                }
                if (isItSimulation_old == true)
                {
                    theStartDate = theEndDate;
                }
            }
        }
    }


    public class CalculationMultiThreading
    {
        public bool objComparaison(dynamic objectUsedA, dynamic objectUsedB)
        {
            if (objectUsedA == objectUsedB)
            {
                return true;
            }

            return false;
        }
        public List<Sensors> Calculator(List<Sensors> currentList)
        {
        Foo:
        foreach (var item in currentList)
        {

            foreach (var item2 in currentList)
            {
                if (item != item2)
                {
                    if (objComparaison(item.sensor_id, item2.sensor_id))
                    {
                        item.value += item2.value;
                        item.numberOfValue += 1;
                        currentList.Remove(item2);
                        goto Foo;
                       
                    }
                }
            }
        }
        return currentList;
        }
    }

    class TimerToUse
    {
        double waitUntil = 10;  //                minutes
        public DateTime TheBddTimer(DateTime dateTime)
        {
            return dateTime.AddSeconds(- waitUntil);
        }
        public DateTime TheCalculationTimer(DateTime theOldTime)
        {
            return theOldTime.AddSeconds(waitUntil);
        }
    }


    class LinkActionDataBase
    {
        private string serverIp = "192.168.1.20";
        private string username = "root";
        private string password = "root";
        private string databaseName = "dbcal";

        public void PutInDataBase(List<Sensors> theObjToPush, DateTime endDate)
        {
            string dbConnectionString = string.Format("server={0};uid={1};pwd={2};database={3};", serverIp, username, password, databaseName);

            MySqlCommand cmd = new MySqlCommand();
            MySqlConnection conn = new MySqlConnection(dbConnectionString);
            cmd.Connection = conn;
            conn.Open();

            TimerToUse time = new TimerToUse();
            foreach (var item in theObjToPush)
            {
                double result = 0;
                if (item.type == true)
                {
                    result = Math.Round((item.value / item.numberOfValue), 0);
                }
                else
                {
                    result = item.value / item.numberOfValue;
                }
                cmd.CommandText = "INSERT INTO stats(sensor_id,value,stat_type_id,date) VALUES(" +
                    item.sensor_id + "," +
                    result + ",1,'"+
                    endDate.ToString("yyyy-MM-dd HH:mm:ss") + "');";
                Console.WriteLine(cmd.CommandText);
                cmd.ExecuteNonQuery();
            }


            conn.Close();

        }
        public List<Sensors> GetFromDataBase(DateTime startDate, DateTime endeDate)
        {
            TimerToUse time = new TimerToUse();
            string dbConnectionString = string.Format("server={0};uid={1};pwd={2};database={3};", serverIp, username, password, databaseName);
            string query =  "SELECT * FROM datas WHERE date BETWEEN '"+
                             startDate.ToString("yyyy-MM-dd HH:mm:ss") +
                             "' AND '" +
                             endeDate.ToString("yyyy-MM-dd HH:mm:ss") +
                             "';";
            Console.WriteLine(query);
            var conn = new MySqlConnection(dbConnectionString);
            conn.Open();

            var cmd = new MySqlCommand(query, conn);
            var reader = cmd.ExecuteReader();
            
            List<Sensors> theListOfSensor = new List<Sensors>();
            int i = 0;
            while (reader.Read())
            {
                var someValue = reader["id"];
                var someValue1 = reader["sensor_id"]; // sensor
                var someValue2 = reader["value"]; // valeur
                var someValue4 = reader["date"]; // valeur
                var someValue3 = reader["isBool"]; // type
                if (someValue3 == null)
                {
                    someValue3 = false;
                }
                else
                {
                    someValue3 = true;
                }
                i++;
                var sensorValue = new Sensors();
                sensorValue.sensor_id = someValue1;
                sensorValue.value = someValue2;
                sensorValue.type = someValue3;

                sensorValue.numberOfValue = 1;
                theListOfSensor.Add(sensorValue);
            }
            conn.Close();
            Console.WriteLine("ID number is :     " + i);
            return theListOfSensor;

        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using RabbitMQ.Client;

namespace DeviceApi.Controllers
{
    [Route("action")]
    public class ValuesController : Controller
    {
        [HttpPost]
        public void Post([FromBody] Command command)
        {
            var factory = new ConnectionFactory() { HostName = "172.20.10.11", UserName = "cyril", Password = "azerty"};
            using(var connection = factory.CreateConnection())
            using(var channel = connection.CreateModel())
            {
                channel.QueueDeclare(queue: "actionDevice",
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null);
            
                String message = JsonConvert.SerializeObject(command);
                var body = Encoding.UTF8.GetBytes(message);

                channel.BasicPublish(exchange: "atlantis-exchange-action",
                    routingKey: "",
                    basicProperties: null,
                    body: body);
                Console.WriteLine(" [x] Sent {0}", body);
            };
        }
    }
    
    public class Command
    {
        public String sensor;
        public String actionEvent;
    }
}
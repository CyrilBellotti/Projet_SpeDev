using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using api.Models;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;

namespace api.Controllers
{
    [Route("stats/")]
    public class ValuesController : Controller
    {
        
        private MySqlConnection cnx;

        private void dbConnection ()
        {
            string connectionString = "DATABASE=dbtestcalculation;SERVER=172.20.10.9;USER=geoffrey;PASSWORD=";
            this.cnx = new MySqlConnection(connectionString);
        }

        private int getSensorId(string uidSensor)
        {
            this.dbConnection();
            this.cnx.Open();

            MySqlCommand cmdId = this.cnx.CreateCommand();
            int idId = 0;
            int id = 0;

            cmdId.CommandText = "SELECT id FROM sensors WHERE uid = @uidSensor;";
            cmdId.Parameters.AddWithValue("@uidSensor", uidSensor);

            MySqlDataReader readerSensor = cmdId.ExecuteReader();
            
            if (readerSensor.HasRows)
            {
                while (readerSensor.Read())
                {
                    idId = readerSensor.GetOrdinal("id");
                    id = readerSensor.GetInt32(idId);
                }
            }
            readerSensor.Close();
            cnx.Close();
            return id;
        }
        
        private int getCalculType(string calculType)
        {
            this.dbConnection();
            this.cnx.Open();

            MySqlCommand cmdId = this.cnx.CreateCommand();
            int idId = 0;
            int id = 0;

            cmdId.CommandText = "SELECT id FROM stat_type WHERE name = @calculType;";
            cmdId.Parameters.AddWithValue("@calculType", calculType);

            MySqlDataReader readerCalcType = cmdId.ExecuteReader();

            if (readerCalcType.HasRows)
            {
                while (readerCalcType.Read())
                {
                    idId = readerCalcType.GetOrdinal("id");
                    id = readerCalcType.GetInt32(idId);
                }
            }
            readerCalcType.Close();
            cnx.Close();
            return id;
        }
        
        [EnableCors("")]
        [HttpGet("{uid}/duration/{duration}/calculType/{calculType}")]
        public List<Stat> Get(string uid, int duration, string calculType)
        {
            Stat stat = new Stat
            {
                value = 12,
                date = "12h"
            };
            Stat stat2 = new Stat
            {
                value = 12,
                date = "13h"
            };
            Stat stat3 = new Stat
            {
                value = 12,
                date = "14h"
            };
            Stat stat4 = new Stat
            {
                value = 12,
                date = "15h"
            };
            var Sensors = new List<Stat>();
            Sensors.Add(stat);
            Sensors.Add(stat2);
            Sensors.Add(stat3);
            Sensors.Add(stat4);
            return Sensors;
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
        
        [HttpGet("get")]
        public String getVal()
        {
            return "toto";
        }
        
        //[EnableCors("sensor/{uidSensor}/duration/{duration}/calculType/{calculType}")]
        [HttpGet("sensor/{uidSensor}/duration/{duration}/calculType/{calculType}")]
        public List<ValueInfo> GetSensorMetric(string uidSensor, int duration, string calculType)
        {   
            this.dbConnection();
            this.cnx.Open();
            
            MySqlCommand cmdStat = this.cnx.CreateCommand();
            var valueList = new ValueListInfo();
            var values = new List<ValueInfo>();
            var today = DateTime.Now.ToUniversalTime();
            
            cmdStat.CommandText = "SELECT * FROM stats WHERE sensor_id = @sensorId AND date BETWEEN @date and NOW() AND stat_type_id = @calcTypeId ORDER BY DATE ;";
            cmdStat.Parameters.AddWithValue("@sensorId", getSensorId(uidSensor));
            cmdStat.Parameters.AddWithValue("@date", today.AddHours(-duration));
            cmdStat.Parameters.AddWithValue("@calcTypeId", getCalculType(calculType));

            MySqlDataReader readerStat = cmdStat.ExecuteReader();
            var MyStats = new List<ValueInfo>();

            if (readerStat.HasRows)
            {
                while (readerStat.Read())
                {
                    int valueId = readerStat.GetOrdinal("value");
                    int dateId = readerStat.GetOrdinal("date");
                    var value = new ValueInfo();
                    value.value = readerStat.GetFloat(valueId);
                    value.date = readerStat.GetDateTime(dateId);
                    MyStats.Add(value);
                }
            }

            //valueList.statValues = values;
 

            return MyStats;
        }
    }
    
    
    
    

    public class Stat
    {
        public String date;
        public Double value;
    }

    public class Stats
    {
        public List<Stat> test;
    }
}


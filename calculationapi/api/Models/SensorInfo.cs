using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace api.Models
{
    [DataContract]
    public class SensorInfo
    {
        [DataMember(Name = "id")]
        public string id { get; set; }

        [DataMember(Name = "iddevice")]
        public string iddevice { get; set; }

        [DataMember(Name = "date")]
        public DateTime date { get; set; }

        [DataMember(Name = "value")]
        public float value { get; set; }

        [DataMember(Name = "type")]
        public string type { get; set; }

        [DataMember(Name = "sensorUid")]
        public string sensorUid { get; set; }

        [DataMember(Name = "stats")]
        public IEnumerable<StatInfo> stats { get; set; }
    }
}
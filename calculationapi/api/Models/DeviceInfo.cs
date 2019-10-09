using System.Collections.Generic;
using System.Runtime.Serialization;

namespace api.Models
{
    [DataContract]
    public class DeviceInfo
    {
        [DataMember(Name = "id")]
        public string id { get; set; }

        [DataMember(Name = "uid")]
        public string uid { get; set; }

        [DataMember(Name = "name")]
        public string name { get; set; }

        [DataMember(Name = "sensors")]
        public IEnumerable<SensorInfo> sensors { get; set; }
    }
}
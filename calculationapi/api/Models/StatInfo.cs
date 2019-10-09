using System;
using System.Runtime.Serialization;

namespace api.Models
{
    [DataContract]
    public class StatInfo
    {
        [DataMember(Name = "id")]
        public string id { get; set; }

        [DataMember(Name = "idsensor")]
        public string idsensor { get; set; }

        [DataMember(Name ="date")]
        public DateTime date { get; set; }

        [DataMember(Name = "value")]
        public float value { get; set; }

        [DataMember(Name = "calcType")]
        public string calcType { get; set; }

        [DataMember(Name = "duration")]
        public string duration { get; set; }
    }
}
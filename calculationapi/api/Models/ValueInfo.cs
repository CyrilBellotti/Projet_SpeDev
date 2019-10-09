using System;
using System.Runtime.Serialization;

namespace api.Models
{
    [DataContract]
    public class ValueInfo
    {
        [DataMember(Name = "value")]
        public float value { get; set; }

        [DataMember(Name = "date")]
        public DateTime date { get; set; }
    }
}
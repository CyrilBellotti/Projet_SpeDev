using System.Collections.Generic;
using System.Runtime.Serialization;

namespace api.Models
{
    public class ValueListInfo
    {
        [DataMember(Name = "values")]
        public IEnumerable<ValueInfo> statValues { get; set; }
    }
}
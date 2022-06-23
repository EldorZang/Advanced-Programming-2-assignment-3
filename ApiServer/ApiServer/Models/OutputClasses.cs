namespace ApiServer.Models
{
    public class OutputContact
    {
        public string? id { get; set; }
        public string? name { get; set; }
        public string? server { get; set; }
        public string? last { get; set; }
        public string? lastdate { get; set; }
        public OutputContact(string? id, string? name, string? server, string? last, string? lastdate)
        {
            this.id = id;
            this.name = name;
            this.server = server;
            this.last = last;
            this.lastdate = lastdate;
        }
    }
    public class OutputMessage
    {
        public int id { get; set; }
        public string content { get; set; }
        public string created { get; set; }
        public bool sent { get; set; }
        public OutputMessage( string content, int msgId, bool sent,string created)
        {
            this.created = created;
            this.id = msgId;
            this.content = content;
            this.sent = sent;
        }
    }
}

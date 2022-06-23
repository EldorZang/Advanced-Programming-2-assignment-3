namespace ApiServer;

public class Message1
{
    public string user1Id { get; set; }
    public string user2Id { get; set; }
    public int msgId { get; set; }
    public string content { get; set; }
    public string created { get; set; }
    public bool sent { get; set; }
    public Message1(string user1Id, string user2Id, string content, int msgId, bool sent)
    {
        this.created = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ss.fffffff");
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.msgId = msgId;
        this.content = content;
        this.sent = sent;
    }


    
}
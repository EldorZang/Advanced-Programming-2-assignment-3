namespace ApiServer;

public class Contact1
{
    public string? userId { get; set; }
    public string? contactId { get; set; }
    public string? name { get; set; }
    public string? server { get; set; }
    public string? last { get; set; }
    public string? lastdate { get; set; }
    public Contact1(string? userId, string? contactId, string? name, string? server, string? last, string? lastdate)
    {
        this.userId = userId;
        this.contactId = contactId;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
    }
    public Contact1(string? userId, string? contactId, string? name, string? server)
    {
        this.userId = userId;
        this.contactId = contactId;
        this.name = name;
        this.server = server;
        this.last = null;
        this.lastdate = null;
    }
    
}
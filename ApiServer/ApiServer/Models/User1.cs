namespace ApiServer;

public class User1
{
    public string? id { get; set; }
    public string? password { get; set; }

    public string? nickName { get; set; }
    public User1(string? id, string? password, string nickName)
    {
        this.id = id;
        this.password = password;
        this.nickName = nickName;
    }
}
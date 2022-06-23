namespace ApiServer.Models;

public class AndroidUsers
{
    public string id { get; set; }
    public string firebaseToken { get; set; }


    public AndroidUsers(string id, string firebaseToken)
    {
        this.id = id;
        this.firebaseToken = firebaseToken;
    }
}
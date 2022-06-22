using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace ApiServer.Services;

public interface IAndroidMessaging
{
    public Task SendNotification(string token, string title, string body);

}

public class AndroidMessaging : IAndroidMessaging
{
    private readonly FirebaseMessaging fireBaseMessaging;
    public AndroidMessaging()
    {
        var firebaseApp = FirebaseApp.Create(new AppOptions() { Credential = GoogleCredential.FromFile("key.json").CreateScoped("https://www.googleapis.com/auth/firebase.messaging")});           
        fireBaseMessaging = FirebaseMessaging.GetMessaging(firebaseApp);
    }
    private Message CreateNotificationObject(string title, string notificationBody, string token)
    {    
        return new Message()
        {
            Token = token,
            Notification = new Notification()
            {
                Body = notificationBody,
                Title = title
            }
        };
    }
    public async Task SendNotification(string token, string title, string body)
    {
        await fireBaseMessaging.SendAsync(CreateNotificationObject(title, body, token));
    }
}
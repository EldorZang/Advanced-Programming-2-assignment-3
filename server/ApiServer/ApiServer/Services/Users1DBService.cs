using ApiServer.Context;
namespace ApiServer.Services
{
    public interface IUsers1DBService
    {
        public bool Login(string id, string password);
        public bool IsUserExists(string id);
        public string? GetNickName(string id);
        public Contact1? AddContact(string loggedUserID, string newContact, string newName, string server);
        public Contact1? GetOneContact(string loggedUserID, string contactID);
        public bool UpdateContactNameServer(string loggedUser, string contactId, string newName, string newServer);
        public bool DeleteContact(string loggedUser, string contactId);
        public Message[]? GetMessages(string user1, string user2);
        public Message? AddMessage(string user1, string user2, string content);
        public Message? AddMessage(string user1, string user2, string content, bool sent);
        public Message? GetMessageById(string loggedUser, string user2, int msgId);
        public bool UpdateMessageContentById(string loggedUser, string user2, int msgId, string newContent);
        public bool DeleteMessageById(string loggedUser, string user2, int msgId);

    }
    public class Users1DBService : IUsers1DBService
    {
        private static UsersDb db = new UsersDb();
        public List<User> GetUsers()
        {
            return db.getUsers();
        }

        public List<Contact1>? GetAllContacts(string currUserID)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(currUserID) == null)
                {
                    return null;
                }
                var contacts = db.Contacts.Where(e => e.userId == currUserID).ToList();
                return contacts;
            }
        }

        public Contact1? AddContact(string loggedUserID, string newContact, string newName, string server)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUserID) == null)
                {
                    return null;
                }
                if (db.Contacts.Where(e=>e.userId == loggedUserID && e.contactId==newContact) != null)
                {
                    return null;
                }
                var addedContact = new Contact1(loggedUserID, newContact, newName, server);
                db.Contacts.Add(addedContact);
                db.SaveChanges();
                return addedContact;
            }
        }

        public bool AddNewUser(string id, string nickName, string password)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(id) == null)
                {
                    return false;
                }
                var newUser = new User1(id, password, nickName);
                db.Users.Add(newUser);
                db.SaveChanges();
                return true;
            }
        }
        public Contact1? GetOneContact(string loggedUserID, string contactID)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUserID) == null)
                {
                    return null;
                }
                var contact = db.Contacts.Where(e => e.userId == loggedUserID && e.contactId == contactID);
                if (contact  == null)
                {
                    return null;
                }
                return contact.ElementAt(0);
            }
        }
        public bool UpdateContactNameServer(string loggedUser, string contactId, string newName, string newServer)
        {
            User? user = db.getUsers().Find(element => element.id == loggedUser);
            if (user == null) return false;
            Contact? contact = user.contacts.Find(element => element.id == contactId);
            if (contact == null) return false;
            contact.name = newName;
            contact.server = newServer;
            return true;

        }
        public bool DeleteContact(string loggedUser, string contactId)
        {
            var user = db.getUsers().Find(element => element.id == loggedUser);
            if (user == null) return false;
            var contact = user.contacts.Find(element => element.id == contactId);
            var userMsgs = user.userMessages.Find(e => e.userId == contactId);
            if (contact == null || userMsgs == null) return false;
            user.contacts.Remove(contact);
            user.userMessages.Remove(userMsgs);
            return true;
        }
        // by using user1 prespective
        public Message[]? GetMessages(string user1, string user2)
        {
            User? user = db.getUsers().Find(element => element.id == user1);
            if (user == null) return null;
            var user2Messages = user.userMessages.Find(e => e.userId == user2);
            if (user2Messages == null) return null;
            return user2Messages.messages.ToArray();
        }
        // by using user1 prespective
        public Message? AddMessage(string user1, string user2, string content)
        {
            User? user = db.getUsers().Find(element => element.id == user1);
            if (user == null) return null;
            var user2Messages = user.userMessages.Find(e => e.userId == user2);
            if (user2Messages == null) return null;
            var output = user2Messages.AddMessage(content, true);
            return output;
        }
        // by using user1 prespective
        public Message? AddMessage(string user1, string user2, string content, bool sent)
        {
            User? user = db.getUsers().Find(element => element.id == user1);
            if (user == null) return null;
            var user2Messages = user.userMessages.Find(e => e.userId == user2);
            if (user2Messages == null) return null;
            var output = user2Messages.AddMessage(content, sent);
            return output;
        }

        public Message? GetMessageById(string loggedUser, string user2, int msgId)
        {
            User? user = db.getUsers().Find(element => element.id == loggedUser);
            if (user == null) return null;
            var user2Messages = user.userMessages.Find(e => e.userId == user2);
            if (user2Messages == null) return null;
            return user2Messages.GetMessage(msgId);
        }
        public bool UpdateMessageContentById(string loggedUser, string user2, int msgId, string newContent)
        {
            var msg = GetMessageById(loggedUser, user2, msgId);
            if (msg == null) return false;
            msg.content = newContent;
            return true;
        }
        public bool DeleteMessageById(string loggedUser, string user2, int msgId)
        {
            User? user = db.getUsers().Find(element => element.id == loggedUser);
            if (user == null) return false;
            var user2Messages = user.userMessages.Find(e => e.userId == user2);
            if (user2Messages == null) return false;
            return user2Messages.DeleteMessage(msgId);
        }
        public bool IsUserExists(string id)
        {
            using (var db = new UsersContext())
            {
                var user = db.Users.Find(id);
                if (user == null)
                {
                    return false;
                }
                return true;
            }
        }
        public bool Login(string id, string password)
        {
            using (var db = new UsersContext())
            {
                var user = db.Users.Find(id);
                if (user == null)
                {
                    return false;
                }
                return user.password == password;
            }
        }
        public string? GetNickName(string id)
        {
            using (var db = new UsersContext())
            {
                User1? usr = db.Users.Find(id);
                if(usr == null)
                {
                    return null;
                }
                return usr.nickName;
            }
        }
    }






}


using ApiServer.Context;
using ApiServer.Models;
using Microsoft.EntityFrameworkCore;

namespace ApiServer.Services
{
    public interface IUsers1DBService
    {
        public bool AddNewAndroidClient(String id, String firebaseToken);
        public bool Login(string id, string password);
        public bool IsUserExists(string id);
        public string? GetNickName(string id);
        public OutputContact? AddContact(string loggedUserID, string newContact, string newName, string server);
        public OutputContact? GetOneContact(string loggedUserID, string contactID);
        public bool UpdateContactNameServer(string loggedUser, string contactId, string newName, string newServer);
        public bool DeleteContact(string loggedUser, string contactId);
        public List<OutputMessage>? GetMessages(string user1, string user2);
        public OutputMessage? AddMessage(string user1, string user2, string content, bool sent);
        public OutputMessage? GetMessageById(string loggedUser, string user2, int msgId);
        public bool UpdateMessageContentById(string loggedUser, string user2, int msgId, string newContent);
        public bool DeleteMessageById(string loggedUser, string user2, int msgId);
        public bool DeleteAndroidUser(string id);
        public bool IsAndroidUser(string id);
        public string? GetAndroidToken(string id);
    }
    public class Users1DBService : IUsers1DBService
    {
        public string? GetAndroidToken(string id)
        {
            using (var db = new UsersContext())
            {
                var androidUser = db.AndroidConnectedUsers.FirstOrDefault(e => e.id == id);
                if (androidUser  == null)
                {
                    return null;
                }
                return androidUser.firebaseToken;
            }
        }
        public bool IsAndroidUser(string id)
        {
            using (var db = new UsersContext())
            {
                if (db.AndroidConnectedUsers.Find(id) == null)
                {
                    return false;
                }
                return true;
            }
        }
        public bool DeleteAndroidUser(string id)
        {
            using (var db = new UsersContext())
            {
                if (db.AndroidConnectedUsers.Find(id) == null)
                {
                    return false;
                }
                var androidUser = db.AndroidConnectedUsers.FirstOrDefault(e => e.id == id);
                if (androidUser  == null)
                {
                    return false;
                }
                db.AndroidConnectedUsers.Remove(androidUser);
                db.SaveChanges();
                return true;
            }
        }
        public void UpdateLastMsg(string user1, string user2)
        {
            using (var db = new UsersContext())
            {
                var contact = db.Contacts.Find(user1, user2);
                if (contact == null)
                {
                    return;
                }
                var msg = db.Messages.Where(e => e.user1Id == user1 && e.user2Id == user2);
                if (!msg.Any())
                {
                    if (contact.lastdate != null || contact.last != null)
                    {
                        contact.lastdate = null;
                        contact.last = null;
                        db.Entry(contact).State = EntityState.Modified;
                        db.SaveChanges();
                    }
                    return;
                }
                var recentMsg = msg.OrderByDescending(e => e.msgId).FirstOrDefault(); ;
                contact.lastdate = recentMsg.created;
                contact.last = recentMsg.content;
                db.Entry(contact).State = EntityState.Modified;
                db.SaveChanges();

            }
        }
            public OutputContact? CastContactToOutputContact(Contact1? contact) {
            if (contact == null)
            {
                return null;
            }
            return new OutputContact(contact.contactId, contact.name, contact.server, contact.last, contact.lastdate);
        }
        public OutputMessage? CastMessageToOutputMessage(Message1? msg)
        {
            if (msg == null)
            {
                return null;
            }
            return new OutputMessage(msg.content,msg.msgId,msg.sent,msg.created);
        }
        public List<OutputContact>? GetAllContacts(string currUserID)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(currUserID) == null)
                {
                    return null;
                }
                var contactsQuery = db.Contacts.Where(e => e.userId == currUserID);
                var outputList = new List<OutputContact>();
                foreach (Contact1 contact in contactsQuery)
                {
                    var newContact = CastContactToOutputContact(contact);
                    if (newContact != null) {
                        outputList.Add(newContact);
                    }
                }
                return outputList;
            }
        }

        public bool AddNewAndroidClient(String id, String firebaseToken)
        {
            var userExists = false;
            using (var db = new UsersContext())
            {
                if (db.AndroidConnectedUsers.Find(id) != null)
                {
                    userExists = true;
                }
            }

            if (userExists == true)
            {
                DeleteAndroidUser(id);
            }

            IQueryable<AndroidUsers> users;
            using (var db = new UsersContext())
            {
                users = db.AndroidConnectedUsers.Where(e => e.firebaseToken == firebaseToken);
            }

            foreach (var e in users)
            {
                DeleteAndroidUser(e.id);
            }

            using (var db = new UsersContext())
            {
                var addedAndroidUser = new AndroidUsers(id, firebaseToken);
                db.AndroidConnectedUsers.Add(addedAndroidUser);
                db.SaveChanges();
                return true;
            }
        }

        public OutputContact? AddContact(string loggedUserID, string newContact, string newName, string server)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUserID) == null)
                {
                    return null;
                }
                if (db.Contacts.Find(loggedUserID,newContact) != null)
                {
                    return null;
                }
                var addedContact = new Contact1(loggedUserID, newContact, newName, server);
                db.Contacts.Add(addedContact);
                db.SaveChanges();
                return CastContactToOutputContact(addedContact);
            }
        }

        public bool AddNewUser(string id, string nickName, string password)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(id) != null)
                {
                    return false;
                }
                var newUser = new User1(id, password, nickName);
                db.Users.Add(newUser);
                db.SaveChanges();
                return true;
            }
        }
        public OutputContact? GetOneContact(string loggedUserID, string contactID)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUserID) == null)
                {
                    return null;
                }
                var contact = db.Contacts.FirstOrDefault(e => e.userId == loggedUserID && e.contactId == contactID);
                if (contact  == null)
                {
                    return null;
                }
                return CastContactToOutputContact(contact);
            }
        }
        public bool UpdateContactNameServer(string loggedUser, string contactId, string newName, string newServer)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUser) == null)
                {
                    return false;
                }
                var contact = db.Contacts.Find(loggedUser, contactId);

                if (contact == null)
                {
                    return false;
                }
                contact.name = newName;
                contact.server = newServer;
                db.Entry(contact).State = EntityState.Modified;
                db.SaveChanges();
                return true;
            }
        }
        public bool DeleteContact(string loggedUser, string contactId)
        {
            using (var db = new UsersContext())
            {
                if (db.Users.Find(loggedUser) == null)
                {
                    return false;
                }
                var contact = db.Contacts.FirstOrDefault(e => e.userId == loggedUser && e.contactId == contactId);
                if (contact  == null)
                {
                    return false;
                }
                var msgs = db.Messages.Where(e=>e.user1Id==loggedUser && e.user2Id==contactId);
                foreach(var msg in msgs)
                {
                    db.Messages.Remove(msg);

                }
                db.Contacts.Remove(contact);
                db.SaveChanges();
                return true;
            }
        }
        // by using user1 prespective
        public List<OutputMessage>? GetMessages(string user1, string user2)
        {

            using (var db = new UsersContext())
            {
                if (db.Contacts.Find(user1,user2) == null)
                {
                    return null;
                }
                var output = new List<OutputMessage>();
                var messagesQuery = db.Messages.Where(e => e.user1Id == user1 && e.user2Id == user2).OrderBy(e => e.msgId);
                foreach(Message1 msg in messagesQuery)
                {
                    var castedMsg = CastMessageToOutputMessage(msg);
                    if (castedMsg != null) {
                        output.Add(castedMsg);
                    }
                }
                return output;
            }
        }
        // by using user1 prespective
        public OutputMessage? AddMessage(string user1, string user2, string content, bool sent)
        {
            using (var db = new UsersContext())
            {
                if (db.Contacts.Find(user1, user2) == null)
                {
                    return null;
                }
                var newMsgId = 1;
                var currMessagesQuery = db.Messages.Where(e => e.user1Id == user1 && e.user2Id == user2);
                if (currMessagesQuery.Any())
                {
                    newMsgId = currMessagesQuery.Max(e => e.msgId) + 1;
                }
                var newMsg = new Message1(user1, user2, content, newMsgId, sent);
                db.Messages.Add(newMsg);
                db.SaveChanges();
                UpdateLastMsg(user1, user2);
                return CastMessageToOutputMessage(newMsg);
            }
        }

        public OutputMessage? GetMessageById(string loggedUser, string user2, int msgId)
        {
            using (var db = new UsersContext())
            {
                return CastMessageToOutputMessage(db.Messages.Find(loggedUser,user2,msgId));
            }
        }
        public bool UpdateMessageContentById(string loggedUser, string user2, int msgId, string newContent)
        {
            using (var db = new UsersContext())
            {
                var msg = db.Messages.Find(loggedUser, user2, msgId);
                if (msg == null)
                {
                    return false;
                }
                msg.content = newContent;
                db.Entry(msg).State = EntityState.Modified;
                db.SaveChanges();
                UpdateLastMsg(loggedUser, user2);
                return true;
            }
        }
        public bool DeleteMessageById(string loggedUser, string user2, int msgId)
        {
            using (var db = new UsersContext())
            {
                var msg = db.Messages.Find(loggedUser, user2, msgId);
                if (msg == null)
                {
                    return false;
                }
                db.Messages.Remove(msg);
                db.SaveChanges();
                UpdateLastMsg(loggedUser, user2);
                return true;
            }
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


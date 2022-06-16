using Microsoft.EntityFrameworkCore;


namespace ApiServer.Context
{
    public class UsersContext: DbContext
    {
        private const string connectionString = "server=localhost;port=3306;database=db;user=root;password=";

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySql(connectionString, MariaDbServerVersion.AutoDetect(connectionString));
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configuring the Name property as the primary
            // key of the Users table
            modelBuilder.Entity<User1>().HasKey(e => e.id);
            modelBuilder.Entity<Contact1>().HasKey(e=>new {e.userId,e.contactId});
            modelBuilder.Entity<Message1>().HasKey(e => new { e.user1Id, e.user2Id, e.msgId});

        }
        
        public DbSet<User1> Users { get; set; }
        public DbSet<Contact1> Contacts { get; set; }
        public DbSet<Message1> Messages { get; set; }
    }
}

package com.example.myapplication5;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDB_Impl extends AppDB {
  private volatile ContactDAO _contactDAO;

  private volatile MessagesDAO _messagesDAO;

  private volatile UserPictureDAO _userPictureDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Contact` (`id` TEXT NOT NULL, `name` TEXT, `server` TEXT, `last` TEXT, `lastdate` TEXT, `lastMassageSendingTime` TEXT, `pictureId` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Message` (`baseUser` TEXT NOT NULL, `contactName` TEXT NOT NULL, `id` INTEGER NOT NULL, `content` TEXT, `created` TEXT, `name` INTEGER NOT NULL, PRIMARY KEY(`baseUser`, `contactName`, `id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserPicture` (`userId` TEXT NOT NULL, `path` TEXT, PRIMARY KEY(`userId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0e593ec1b55fdebb1058e7b866e90047')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Contact`");
        _db.execSQL("DROP TABLE IF EXISTS `Message`");
        _db.execSQL("DROP TABLE IF EXISTS `UserPicture`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsContact = new HashMap<String, TableInfo.Column>(7);
        _columnsContact.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("server", new TableInfo.Column("server", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("last", new TableInfo.Column("last", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("lastdate", new TableInfo.Column("lastdate", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("lastMassageSendingTime", new TableInfo.Column("lastMassageSendingTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsContact.put("pictureId", new TableInfo.Column("pictureId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysContact = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesContact = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoContact = new TableInfo("Contact", _columnsContact, _foreignKeysContact, _indicesContact);
        final TableInfo _existingContact = TableInfo.read(_db, "Contact");
        if (! _infoContact.equals(_existingContact)) {
          return new RoomOpenHelper.ValidationResult(false, "Contact(com.example.myapplication5.Contact).\n"
                  + " Expected:\n" + _infoContact + "\n"
                  + " Found:\n" + _existingContact);
        }
        final HashMap<String, TableInfo.Column> _columnsMessage = new HashMap<String, TableInfo.Column>(6);
        _columnsMessage.put("baseUser", new TableInfo.Column("baseUser", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("contactName", new TableInfo.Column("contactName", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("id", new TableInfo.Column("id", "INTEGER", true, 3, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("created", new TableInfo.Column("created", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMessage.put("name", new TableInfo.Column("name", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMessage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMessage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMessage = new TableInfo("Message", _columnsMessage, _foreignKeysMessage, _indicesMessage);
        final TableInfo _existingMessage = TableInfo.read(_db, "Message");
        if (! _infoMessage.equals(_existingMessage)) {
          return new RoomOpenHelper.ValidationResult(false, "Message(com.example.myapplication5.Message).\n"
                  + " Expected:\n" + _infoMessage + "\n"
                  + " Found:\n" + _existingMessage);
        }
        final HashMap<String, TableInfo.Column> _columnsUserPicture = new HashMap<String, TableInfo.Column>(2);
        _columnsUserPicture.put("userId", new TableInfo.Column("userId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserPicture.put("path", new TableInfo.Column("path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserPicture = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserPicture = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserPicture = new TableInfo("UserPicture", _columnsUserPicture, _foreignKeysUserPicture, _indicesUserPicture);
        final TableInfo _existingUserPicture = TableInfo.read(_db, "UserPicture");
        if (! _infoUserPicture.equals(_existingUserPicture)) {
          return new RoomOpenHelper.ValidationResult(false, "UserPicture(com.example.myapplication5.UserPicture).\n"
                  + " Expected:\n" + _infoUserPicture + "\n"
                  + " Found:\n" + _existingUserPicture);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0e593ec1b55fdebb1058e7b866e90047", "d88370bc665a72b0f801447007d7f5e5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Contact","Message","UserPicture");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Contact`");
      _db.execSQL("DELETE FROM `Message`");
      _db.execSQL("DELETE FROM `UserPicture`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ContactDAO.class, ContactDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(MessagesDAO.class, MessagesDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserPictureDAO.class, UserPictureDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ContactDAO contactDao() {
    if (_contactDAO != null) {
      return _contactDAO;
    } else {
      synchronized(this) {
        if(_contactDAO == null) {
          _contactDAO = new ContactDAO_Impl(this);
        }
        return _contactDAO;
      }
    }
  }

  @Override
  public MessagesDAO messagesDao() {
    if (_messagesDAO != null) {
      return _messagesDAO;
    } else {
      synchronized(this) {
        if(_messagesDAO == null) {
          _messagesDAO = new MessagesDAO_Impl(this);
        }
        return _messagesDAO;
      }
    }
  }

  @Override
  public UserPictureDAO UserPictureDao() {
    if (_userPictureDAO != null) {
      return _userPictureDAO;
    } else {
      synchronized(this) {
        if(_userPictureDAO == null) {
          _userPictureDAO = new UserPictureDAO_Impl(this);
        }
        return _userPictureDAO;
      }
    }
  }
}

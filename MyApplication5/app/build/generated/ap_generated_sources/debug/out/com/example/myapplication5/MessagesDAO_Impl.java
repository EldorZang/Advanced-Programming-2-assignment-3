package com.example.myapplication5;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MessagesDAO_Impl implements MessagesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Message> __insertionAdapterOfMessage;

  private final EntityDeletionOrUpdateAdapter<Message> __deletionAdapterOfMessage;

  private final SharedSQLiteStatement __preparedStmtOfDeleteConversation;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTable;

  public MessagesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMessage = new EntityInsertionAdapter<Message>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Message` (`baseUser`,`contactName`,`id`,`content`,`created`,`name`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Message value) {
        if (value.getBaseUser() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getBaseUser());
        }
        if (value.getContactName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getContactName());
        }
        stmt.bindLong(3, value.getId());
        if (value.getContent() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getContent());
        }
        if (value.getCreated() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCreated());
        }
        final int _tmp = value.isSent() ? 1 : 0;
        stmt.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfMessage = new EntityDeletionOrUpdateAdapter<Message>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Message` WHERE `baseUser` = ? AND `contactName` = ? AND `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Message value) {
        if (value.getBaseUser() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getBaseUser());
        }
        if (value.getContactName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getContactName());
        }
        stmt.bindLong(3, value.getId());
      }
    };
    this.__preparedStmtOfDeleteConversation = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM message WHERE baseUser = ? AND contactName = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM message";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<Message> messages) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMessage.insert(messages);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Message message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMessage.handle(message);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteConversation(final String baseUser, final String contactName) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteConversation.acquire();
    int _argIndex = 1;
    if (baseUser == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, baseUser);
    }
    _argIndex = 2;
    if (contactName == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, contactName);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteConversation.release(_stmt);
    }
  }

  @Override
  public void deleteTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteTable.release(_stmt);
    }
  }

  @Override
  public List<Message> getConversion(final String baseUser, final String contactName) {
    final String _sql = "SELECT * FROM message WHERE baseUser = ? AND contactName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (baseUser == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, baseUser);
    }
    _argIndex = 2;
    if (contactName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, contactName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfBaseUser = CursorUtil.getColumnIndexOrThrow(_cursor, "baseUser");
      final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(_cursor, "contactName");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
      final int _cursorIndexOfCreated = CursorUtil.getColumnIndexOrThrow(_cursor, "created");
      final int _cursorIndexOfSent = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final List<Message> _result = new ArrayList<Message>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Message _item;
        _item = new Message();
        final String _tmpBaseUser;
        if (_cursor.isNull(_cursorIndexOfBaseUser)) {
          _tmpBaseUser = null;
        } else {
          _tmpBaseUser = _cursor.getString(_cursorIndexOfBaseUser);
        }
        _item.setBaseUser(_tmpBaseUser);
        final String _tmpContactName;
        if (_cursor.isNull(_cursorIndexOfContactName)) {
          _tmpContactName = null;
        } else {
          _tmpContactName = _cursor.getString(_cursorIndexOfContactName);
        }
        _item.setContactName(_tmpContactName);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpContent;
        if (_cursor.isNull(_cursorIndexOfContent)) {
          _tmpContent = null;
        } else {
          _tmpContent = _cursor.getString(_cursorIndexOfContent);
        }
        _item.setContent(_tmpContent);
        final String _tmpCreated;
        if (_cursor.isNull(_cursorIndexOfCreated)) {
          _tmpCreated = null;
        } else {
          _tmpCreated = _cursor.getString(_cursorIndexOfCreated);
        }
        _item.setCreated(_tmpCreated);
        final boolean _tmpSent;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfSent);
        _tmpSent = _tmp != 0;
        _item.setSent(_tmpSent);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int vacuumDb(final SupportSQLiteQuery supportSQLiteQuery) {
    final SupportSQLiteQuery _internalQuery = supportSQLiteQuery;
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _internalQuery, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

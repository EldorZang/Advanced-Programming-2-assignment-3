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
public final class ContactDAO_Impl implements ContactDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Contact> __insertionAdapterOfContact;

  private final EntityDeletionOrUpdateAdapter<Contact> __deletionAdapterOfContact;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTable;

  public ContactDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContact = new EntityInsertionAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Contact` (`id`,`name`,`server`,`last`,`lastdate`,`lastMassageSendingTime`,`pictureId`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getServer() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getServer());
        }
        if (value.getLast() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getLast());
        }
        if (value.getLastdate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLastdate());
        }
        if (value.getLastMassageSendingTime() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getLastMassageSendingTime());
        }
        if (value.getPictureId() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPictureId());
        }
      }
    };
    this.__deletionAdapterOfContact = new EntityDeletionOrUpdateAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Contact` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM contact";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<Contact> contacts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfContact.insert(contacts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Contact contact) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfContact.handle(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
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
  public List<Contact> getAll() {
    final String _sql = "SELECT * FROM contact";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfServer = CursorUtil.getColumnIndexOrThrow(_cursor, "server");
      final int _cursorIndexOfLast = CursorUtil.getColumnIndexOrThrow(_cursor, "last");
      final int _cursorIndexOfLastdate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastdate");
      final int _cursorIndexOfLastMassageSendingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "lastMassageSendingTime");
      final int _cursorIndexOfPictureId = CursorUtil.getColumnIndexOrThrow(_cursor, "pictureId");
      final List<Contact> _result = new ArrayList<Contact>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Contact _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpServer;
        if (_cursor.isNull(_cursorIndexOfServer)) {
          _tmpServer = null;
        } else {
          _tmpServer = _cursor.getString(_cursorIndexOfServer);
        }
        final String _tmpLast;
        if (_cursor.isNull(_cursorIndexOfLast)) {
          _tmpLast = null;
        } else {
          _tmpLast = _cursor.getString(_cursorIndexOfLast);
        }
        final String _tmpLastdate;
        if (_cursor.isNull(_cursorIndexOfLastdate)) {
          _tmpLastdate = null;
        } else {
          _tmpLastdate = _cursor.getString(_cursorIndexOfLastdate);
        }
        _item = new Contact(_tmpId,_tmpName,_tmpServer,_tmpLastdate,_tmpLast);
        final String _tmpLastMassageSendingTime;
        if (_cursor.isNull(_cursorIndexOfLastMassageSendingTime)) {
          _tmpLastMassageSendingTime = null;
        } else {
          _tmpLastMassageSendingTime = _cursor.getString(_cursorIndexOfLastMassageSendingTime);
        }
        _item.setLastMassageSendingTime(_tmpLastMassageSendingTime);
        final String _tmpPictureId;
        if (_cursor.isNull(_cursorIndexOfPictureId)) {
          _tmpPictureId = null;
        } else {
          _tmpPictureId = _cursor.getString(_cursorIndexOfPictureId);
        }
        _item.setPictureId(_tmpPictureId);
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

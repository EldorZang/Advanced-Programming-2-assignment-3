package com.example.myapplication5;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDAO {

        @Query("SELECT * FROM contact")
        List<Contact> getAll();

        @Insert
        void insertAll(List<Contact> contacts);

        @Delete
        void delete(Contact contact);
        @Query("DELETE FROM contact")
        void deleteTable();
        @RawQuery
        int vacuumDb(SupportSQLiteQuery supportSQLiteQuery);
}

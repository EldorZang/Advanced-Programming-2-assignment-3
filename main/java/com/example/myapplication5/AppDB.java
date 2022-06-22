package com.example.myapplication5;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class,Message.class,UserPicture.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDAO contactDao();
    public abstract MessagesDAO messagesDao();
    public abstract UserPictureDAO UserPictureDao();
}

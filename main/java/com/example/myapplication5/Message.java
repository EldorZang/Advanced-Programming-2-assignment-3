package com.example.myapplication5;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"baseUser","contactName","id"})
public class Message {
    @NonNull
    @ColumnInfo(name = "baseUser")
    private String baseUser;
    @NonNull
    @ColumnInfo(name = "contactName")
    private String ContactName;
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "created")
    private String created;

    public String getBaseUser() {
        return baseUser;
    }

    public void setBaseUser(String baseUser) {
        this.baseUser = baseUser;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    @ColumnInfo(name = "name")
    private boolean sent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}

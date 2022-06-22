package com.example.myapplication5;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "server")
    private String server;
    @ColumnInfo(name = "last")
    private String last;
    @ColumnInfo(name = "lastdate")
    private String lastdate;
    @ColumnInfo(name = "lastMassageSendingTime")
    private String lastMassageSendingTime;

    private String pictureId = null;
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }


    public Contact(String id, String name, String server,String lastdate,String last) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.lastdate = lastdate;
        this.last = last;
        pictureId = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public void setLastMassageSendingTime(String lastMassageSendingTime) {
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

}

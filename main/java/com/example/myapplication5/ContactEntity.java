package com.example.myapplication5;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ContactEntity {

        @PrimaryKey
        private String id;

        @ColumnInfo(name = "name")
        private String name;

        @ColumnInfo(name = "server")
        private String server;
    @ColumnInfo(name = "last")
    private String last;
    @ColumnInfo(name = "lastdate")
    private String lastdate;
    @ColumnInfo(name = "last_name")
    private int pictureId;
}

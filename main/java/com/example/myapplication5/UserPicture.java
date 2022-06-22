package com.example.myapplication5;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserPicture {
    @PrimaryKey
    @NonNull
    private String userId;
    @ColumnInfo(name = "path")
    private String path;

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserPicture(@NonNull String userId, String path) {
        this.userId = userId;
        this.path = path;
    }
}

package com.example.myapplication5;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserPictureDAO {
    @Insert
    void insert(UserPicture userPicture);
    @Query("SELECT * FROM userpicture WHERE userId = :userId")
    List<UserPicture> getUserPicture(String userId);
    @Query("DELETE FROM userpicture WHERE userId = :userId")
    void delete(String userId);
}

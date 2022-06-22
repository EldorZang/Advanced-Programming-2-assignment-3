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
public interface MessagesDAO {

    @Query("SELECT * FROM message WHERE baseUser = :baseUser AND contactName = :contactName")
    List<Message> getConversion(String baseUser, String contactName);

    @Insert
    void insertAll(List<Message> messages);
    @Query("DELETE FROM message WHERE baseUser = :baseUser AND contactName = :contactName")
    void deleteConversation(String baseUser, String contactName);
    @Delete
    void delete(Message message);
    @Query("DELETE FROM message")
    void deleteTable();
    @RawQuery
    int vacuumDb(SupportSQLiteQuery supportSQLiteQuery);
}

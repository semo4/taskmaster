package com.example.taskmaster;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM Tasks WHERE id IN (:taskIds)")
    List<Tasks> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM Tasks WHERE title LIKE :title LIMIT 1")
    Tasks findByTitle(String title);

    @Insert
    void insertAll(Tasks... tasks);

    @Delete
    void delete(Tasks task);

    @Query("DELETE FROM Tasks")
    void deleteAll();

    @Query("DELETE FROM Tasks WHERE id = :Id")
     void deleteByTaskId(int Id);


}

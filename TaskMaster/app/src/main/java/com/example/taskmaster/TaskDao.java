package com.example.taskmaster;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE id IN (:taskIds)")
    List<Task> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM tasks WHERE title LIKE :title LIMIT 1")
    Task findByTitle(String title);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("DELETE FROM tasks WHERE id = :Id")
     void deleteByTaskId(int Id);


}

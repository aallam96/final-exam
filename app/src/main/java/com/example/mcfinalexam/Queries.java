package com.example.mcfinalexam;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface Queries {
    @Query("SELECT * FROM todo_list")
    List<TodoItem> getAll();
    @Insert
    void insert(TodoItem todoItem);
    @Query("DELETE FROM todo_list WHERE item = :item")
    void delete(String item);
}


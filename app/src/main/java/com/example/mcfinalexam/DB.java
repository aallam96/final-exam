package com.example.mcfinalexam;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TodoItem.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public abstract Queries mQueries();
}

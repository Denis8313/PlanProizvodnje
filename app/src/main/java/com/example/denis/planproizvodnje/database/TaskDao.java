package com.example.denis.planproizvodnje.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// Create Dao or data access object for all entities
@Entity
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY priority")
    List<TaskEntry> loadAllTasks();
    @Insert
    void insertTask(TaskEntry taskEntry);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntry taskEntry);
    @Delete
    void deleteTask(TaskEntry taskEntry);
}

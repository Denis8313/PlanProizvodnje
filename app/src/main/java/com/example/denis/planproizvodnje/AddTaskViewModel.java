package com.example.denis.planproizvodnje;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.denis.planproizvodnje.database.AppDatabase;
import com.example.denis.planproizvodnje.database.TaskEntry;

class AddTaskViewModel extends ViewModel{

    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase mDb, int mTaskId) {
        task = mDb.taskDao().loadTaskById(mTaskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}

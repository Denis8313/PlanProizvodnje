package com.example.denis.planproizvodnje;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.denis.planproizvodnje.database.AppDatabase;
import com.example.denis.planproizvodnje.database.TaskEntry;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    //Constant for loging
    private static final String TAG = AddTaskActivity.class.getSimpleName();

    //Constants for priority
    public static final int HIGH_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int LOW_PRIORTY = 3;

    // Extra for task id to be receved in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";

    private Button addButton;
    private EditText taskDescription;
    private RadioGroup mRadioGroup;

    private AppDatabase mDb;

    // Constant for task id when wee are not in update mode
    private static final int DEFAOULT_TASK_ID = -1;

    private int mTaskId = DEFAOULT_TASK_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            addButton.setText("Obnovi zadatak");
            if(mTaskId == DEFAOULT_TASK_ID) {
                // Populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAOULT_TASK_ID);

                final LiveData<TaskEntry> task = mDb.taskDao().loadTaskById(mTaskId);
                task.observe(this, new Observer<TaskEntry>() {
                    @Override
                    public void onChanged(@Nullable TaskEntry taskEntry) {
                        task.removeObserver(this);
                        Log.d(TAG, "Receiving database update from LiveData");
                        populateUI(taskEntry);
                    }
                });
            }
        }
    }

    private void initViews() {

        addButton = (Button) findViewById(R.id.add_task_button);
        taskDescription = (EditText) findViewById(R.id.task_edit_text);
        mRadioGroup = (RadioGroup) findViewById(R.id.priority_radio_group);

        mDb = AppDatabase.getsInstance(getApplicationContext());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        String addTaskDescription = taskDescription.getText().toString();
        int taskPriority = getPriorityFromViews();
        Date date = new Date();


        final TaskEntry taskEntry = new TaskEntry(addTaskDescription, taskPriority, date);
        AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAOULT_TASK_ID) {
                    mDb.taskDao().insertTask(taskEntry);
                }else
                {
                    //update task
                    taskEntry.setId(mTaskId);
                    mDb.taskDao().updateTask(taskEntry);
                }
                finish();
            }
        });

    }

    //Get priority from checkbox
    public int getPriorityFromViews() {

        int priority = 1;
        int checkedId = (mRadioGroup.getCheckedRadioButtonId());

        switch (checkedId) {
            case R.id.high_priority:
                priority = HIGH_PRIORITY;
                break;
            case R.id.medium_priority:
                priority = MEDIUM_PRIORITY;
                break;
            case R.id.low_priority:
                priority = LOW_PRIORTY;
        }
        return priority;
    }

    //Set priority in Views
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case HIGH_PRIORITY:
                (mRadioGroup).check(R.id.high_priority);
                break;
            case MEDIUM_PRIORITY:
                (mRadioGroup).check(R.id.medium_priority);
                break;
            case LOW_PRIORTY:
                (mRadioGroup).check(R.id.low_priority);
        }
    }

    private void populateUI(TaskEntry task) {
        if(task == null) {
            return;
        }
        taskDescription.setText(task.getTaskDescription());
        setPriorityInViews(task.getPriority());
    }
}

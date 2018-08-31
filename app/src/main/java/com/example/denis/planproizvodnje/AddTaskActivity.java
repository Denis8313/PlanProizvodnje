package com.example.denis.planproizvodnje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.denis.planproizvodnje.database.AppDatabase;
import com.example.denis.planproizvodnje.database.TaskEntry;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    //Constants for priority
    public static final int HIGH_PRIORITY = 1;
    public static final int MEDIUM_PRIORITY = 2;
    public static final int LOW_PRIORTY = 3;

    private Button addButton;
    private EditText taskDescription;
    private RadioGroup mRadioGroup;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();
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
                mDb.taskDao().insertTask(taskEntry);
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
}

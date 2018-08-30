package com.example.denis.planproizvodnje;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denis.planproizvodnje.database.TaskEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";
    private Context mContext;

    private List<TaskEntry> taskEntries;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    //Constructor for task adapter
    public TaskAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the task_view layout to a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_view, parent, false);
        return new TaskViewHolder(view);
    }

    //Coling from RecyclerView to display data from specific position of cursor
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        TaskEntry taskEntry = taskEntries.get(position);
        String description = taskEntry.getTaskDescription();
        int priority = taskEntry.getPriority();
        String priorityString = "" + priority;
        String date = dateFormat.format(taskEntry.getDate());

        holder.taskDescriptionView.setText(description);
        holder.updateAtView.setText(date);
        holder.priorityView.setText(priorityString);
    }

    @Override
    public int getItemCount() {
        if(taskEntries == null) {
            return 0;
        }
        return taskEntries.size();
    }

    public List<TaskEntry> getTask() {
        return taskEntries;
    }

    public void setTask(List<TaskEntry> taskEntries) {
        this.taskEntries = taskEntries;
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView taskDescriptionView;
        TextView updateAtView;
        TextView priorityView;

        public TaskViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = (TextView) itemView.findViewById(R.id.task_description_textview);
            updateAtView = (TextView) itemView.findViewById(R.id.date_textview);
            priorityView = (TextView) itemView.findViewById(R.id.priority_textview);
        }
    }
}

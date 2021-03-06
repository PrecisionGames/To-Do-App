package com.example.to_do_app_dma.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_app_dma.AddNewTask;
import com.example.to_do_app_dma.MainActivity;
import com.example.to_do_app_dma.Model.ToDoModel;
import com.example.to_do_app_dma.R;
import com.example.to_do_app_dma.Utils.DataBaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel>todoList;
    private MainActivity activity;
    private DataBaseHandler db;

    public ToDoAdapter(DataBaseHandler db, MainActivity activity){
        this.db =db;
        this.activity=activity;

    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.task_layout,parent, false);
    return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item=todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);

                }
                else{
                    db.updateStatus(item.getId(),0);
                }

            }
        });
    }

    public int getItemCount(){
        return todoList.size();
    }
    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        //Call this function so that recycler View is updated.
        notifyDataSetChanged();
    }

    public void editItem(int position){
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.todoCheckBox);
        }
    }
}

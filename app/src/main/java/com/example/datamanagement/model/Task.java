package com.example.datamanagement.model;

/**
 * Created by ribera-mac on 3/23/17.
 */

public class Task {
    int id;
    String taskName;
    String taskUser;
    boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked()

    {
        return isChecked;
    }

    public Task setChecked(boolean checked)

    {
        isChecked = checked;
        return null;
    }

    public String getTaskName()

    {
        return taskName;
    }

    public void setTaskName(String taskName)

    {
        this.taskName = taskName;
    }

    public String getTaskUser(){
        return taskUser;
    }

    public void setTaskUser(String taskUser){
        this.taskUser = taskUser;
    }

    public Task(){

    }

}

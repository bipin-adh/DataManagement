package com.example.datamanagement.model;

/**
 * Created by ribera-mac on 3/23/17.
 */

public class Task {
    String taskName;
    String taskUser;
    boolean isChecked;

    public boolean isChecked()

    {
        return isChecked;
    }

    public void setChecked(boolean checked)

    {
        isChecked = checked;
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

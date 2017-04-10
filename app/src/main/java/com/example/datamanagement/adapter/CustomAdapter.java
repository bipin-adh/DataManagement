package com.example.datamanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.datamanagement.R;
import com.example.datamanagement.model.Contact;
import com.example.datamanagement.model.Task;

import java.util.List;

import static android.R.attr.resource;


/**
 * Created by bpn-adh on 3/23/17.
 */

public class CustomAdapter extends ArrayAdapter{


    Context context ;
    List<Task> taskList;




    public CustomAdapter(Context context, List<Task> taskList) {
        super(context,R.layout.list_item, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        Task task = taskList.get(position);
        name.setText(task.getTaskName());
        boolean isChecked = task.isChecked();
        if(isChecked){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        return  convertView;
    }


    public List<Task> getData()
    {
        return taskList;
    }

}

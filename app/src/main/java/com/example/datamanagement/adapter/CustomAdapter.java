package com.example.datamanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.datamanagement.R;
import com.example.datamanagement.model.Task;

import java.util.List;


/**
 * Created by bpn-adh on 3/23/17.
 */

public class CustomAdapter extends ArrayAdapter{

    public static final String TAG =CustomAdapter.class.getSimpleName();
    Context context ;
    List<Task> taskList;
    private CheckboxListener checkboxListener;


    public interface CheckboxListener {

        void onCheckBoxTick(Task task,boolean isChecked);
    }




    public CustomAdapter(Context context, List<Task> taskList , CheckboxListener checkboxListener) {
        super(context,R.layout.list_item_checkbox, taskList);
        this.context = context;
        this.taskList = taskList;
        this.checkboxListener = checkboxListener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_item_checkbox, parent, false);
        final TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        final Task task = taskList.get(position);
        name.setText(task.getTaskName());



        boolean isChecked = task.isChecked();

        if(isChecked){
            cb.setChecked(true);
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            cb.setChecked(false);
            name.setPaintFlags(name.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
        }

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

//                if(isChecked) {
//                    name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                }else{
//                    name.setPaintFlags(name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                }
                Log.d(TAG, "onCheckChanged:"+ task.getTaskName());
                checkboxListener.onCheckBoxTick(task,isChecked);



            }
        });






        return  convertView;
    }


    public List<Task> getData()
    {
        return taskList;
    }

}

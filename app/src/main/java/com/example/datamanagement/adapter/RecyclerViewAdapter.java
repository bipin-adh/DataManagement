package com.example.datamanagement.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datamanagement.R;
import com.example.datamanagement.model.Task;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    public static final String TAG =RecyclerViewAdapter.class.getSimpleName();
    Context context ;
    List<Task> taskList;
    private CheckboxListener checkboxListener;

    private ImageviewListener imageviewListener;

    public interface CheckboxListener {

        void onCheckBoxTick(Task task,boolean isChecked);
    }

    public interface ImageviewListener{

        void onDeleteIconClick(Task task);

    }


    public  RecyclerViewAdapter(Context context,List<Task> taskList , CheckboxListener checkboxListener,ImageviewListener imageviewListener)

    {

        this.context = context;
        this.taskList = taskList;
        this.checkboxListener = checkboxListener;
        this.imageviewListener = imageviewListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_checkbox,
                parent, false);
        return new RecyclerViewAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.textViewName.setText(task.getTaskName());

        boolean isChecked = task.isChecked();


        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageviewListener.onDeleteIconClick(task);
            }
        });

        if(isChecked){
            holder.cb.setChecked(true);
            holder.textViewName.setPaintFlags(holder.textViewName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.cb.setChecked(false);
            holder.textViewName.setPaintFlags(holder.textViewName.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    holder.cb.setChecked(true);
                    holder.textViewName.setPaintFlags(holder.textViewName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.cb.setChecked(false);
                    holder.textViewName.setPaintFlags(holder.textViewName.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                }
                Log.d(TAG, "onCheckChanged:"+ task.getTaskName());
                checkboxListener.onCheckBoxTick(task,isChecked);
            }


        });



    }

    public List<Task> getData()

    {
        return taskList;
    }



    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        CheckBox cb;
        ImageView imageViewDelete;

        public MyHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textView1);
            cb = (CheckBox) view.findViewById(R.id.checkBox1);
            imageViewDelete = (ImageView) view.findViewById(R.id.imageButton);

        }
    }
}


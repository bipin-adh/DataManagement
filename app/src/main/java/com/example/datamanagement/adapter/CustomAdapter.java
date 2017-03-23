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

import static android.R.attr.resource;


/**
 * Created by bpn-adh on 3/23/17.
 */

public class CustomAdapter extends ArrayAdapter{


    Context context ;
    Contact contact = new Contact();




    public CustomAdapter(Context context, Contact contact) {
        super(context,R.layout.list_item, contact);
        this.context = context;
        this.contact = contact;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(Contact.getName());
        if(Contact.getChkvalue() == 1)
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }

}

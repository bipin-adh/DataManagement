package com.example.datamanagement.fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.datamanagement.R;


public class AddToListDialogFragment extends DialogFragment implements View.OnClickListener  {


    public static final String TAG = AddToListDialogFragment.class.getSimpleName();
    private DataEnteredListener dataEnteredListener;
    Button positiveButton , negativeButton;
    EditText editTextAddToList;
    String enteredData;

        //EditText editTextAddToList;

    public interface DataEnteredListener {


        void OnDataEntered(String string);

    }

    public AddToListDialogFragment(){

    }

    public void initView(View view){

        Log.d(TAG, "initView: inside initView");
        editTextAddToList = (EditText) view.findViewById(R.id.edittext_listdata);
        positiveButton = (Button) view.findViewById(R.id.btnPositive);
        negativeButton = (Button) view.findViewById(R.id.btnNegative);
    }

    @Override
    public void onAttach(Activity activity){

        super.onAttach(activity);

        try {
            dataEnteredListener = (DataEnteredListener) activity;

        }catch (ClassCastException e){

            throw new ClassCastException(activity.toString() + "must implement DataEnteredListener");
        }



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d(TAG, "onCreateView: oncreate view after first run");
        View rootView = inflater.inflate(R.layout.edittext_diagfrag, null);

        initView(rootView);

        getDialog().getWindow().setBackgroundDrawableResource(R.color.appBarColor);
        Log.d(TAG, "onCreateView: after initView");
        positiveButton.setOnClickListener(this);
        negativeButton.setOnClickListener(this);

        return rootView;


    }

    @Override
    public void onClick(View v){
            int id = v.getId();
            switch (id) {
                case R.id.btnPositive:
                    enteredData = editTextAddToList.getText().toString();
                    Log.d(TAG, "onClick: Entered data: " + enteredData);
                    dataEnteredListener.OnDataEntered(enteredData);
                    break;

                case R.id.btnNegative:
                    dismiss();
                    break;
            }


        }




}
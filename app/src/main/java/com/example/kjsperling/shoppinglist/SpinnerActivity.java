package com.example.kjsperling.shoppinglist;


import android.view.View;
import android.widget.AdapterView;

public class SpinnerActivity implements AdapterView.OnItemSelectedListener {


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

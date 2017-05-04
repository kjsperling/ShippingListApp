package com.example.kjsperling.shoppinglist;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Item> {


    private final Context context;
    private final ArrayList<Item> items;

    public CustomArrayAdapter(Context context, ArrayList items) {
        super(context, -1, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_multi, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.listItemName);
        TextView quantity = (TextView) rowView.findViewById(R.id.listItemQuantity);
        TextView cost = (TextView) rowView.findViewById(R.id.listItemCost);
        TextView priority = (TextView) rowView.findViewById(R.id.listItemPriority);
        String priorityText=null;
        if(items.get(position).getPriority()==1){
            priorityText="High";
        }else if(items.get(position).getPriority()==2){
            priorityText="Medium";
        }else if(items.get(position).getPriority()==3){
            priorityText="Low";
        }

        if(items.get(position).isPurchased()){
            name.setText(items.get(position).getName());
            quantity.setText("Quantity: " + items.get(position).getQuantityString());
            cost.setText("Cost: $" + items.get(position).getCostString());
            priority.setText(priorityText);
            name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            quantity.setPaintFlags(quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            priority.setPaintFlags(priority.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            cost.setPaintFlags(cost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            name.setText(items.get(position).getName());
            quantity.setText("Quantity: " + items.get(position).getQuantityString());
            cost.setText("Cost: $" + items.get(position).getCostString());
            priority.setText(priorityText);
        }
        return rowView;
    }



}

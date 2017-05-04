package com.example.kjsperling.shoppinglist;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Item item1 = new Item(5.00,"Chicken", 1,2);
    Item item2 = new Item(2.00,"Chips", 2,1);
    Item item3 = new Item(3.00,"Bread", 2,1);
    Item item4 = new Item(10.00,"Potatoes", 2,1);
    Item item5 = new Item(11.00,"Peas", 2,1);
    Item item6 = new Item(6.00,"Milk", 2,1);
    Item item7 = new Item(5.00,"Chicken", 1,2);
    Item item8 = new Item(2.00,"Chips", 2,1);
    Item item9 = new Item(3.00,"Bread", 2,1);
    Item item10 = new Item(10.00,"Potatoes", 2,1);
    Item item11 = new Item(11.00,"Peas", 2,1);
    Item item12 = new Item(6.00,"Milk", 2,1);
    Item item13 = new Item(5.00,"Chicken", 1,2);
    Item item14 = new Item(2.00,"Chips", 2,1);
    Item item15 = new Item(3.00,"Bread", 2,1);
    Item item16 = new Item(10.00,"Potatoes", 2,1);
    Item item17 = new Item(11.00,"Peas", 2,1);
    Item item18 = new Item(6.00,"Milk", 2,1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTitle(R.string.app_name);
//        //final List<Item> itemList = new ArrayList<>(Arrays.asList(item1,item2,item3,item4,item5,item6,item7,item8,item9,
//                item10,item11,item12,item13,item14,item15,item16,item17,item18));
        final List<Item> itemList = new ArrayList<>();
        final ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, R.layout.list_layout,itemList);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Button addButton = (Button) findViewById(R.id.addButton);
        Button budgetButton = (Button) findViewById(R.id.setBudgetbutton);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                item.setName(item.getName()+" Clicked");
                item.setPurchased(true);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View addDialogView = inflater.inflate(R.layout.add_item, null);
                builder.setView(addDialogView);
                final Spinner spinner = (Spinner) addDialogView.findViewById(R.id.priority_spinner);
                spinner.setAdapter(spinnerAdapter);
                builder.setCancelable(false);
                final EditText itemName = (EditText) addDialogView.findViewById(R.id.itemName);
                if(itemName.getText().toString().length()==0){
                    itemName.setError("Name is required!");
                }
                final EditText itemCost = (EditText) addDialogView.findViewById(R.id.itemCost);
                if(itemCost.getText().toString().length()==0){
                    itemCost.setError("Cost is required!");
                }
                final EditText itemQuantity = (EditText) addDialogView.findViewById(R.id.itemQuantity);
                if(itemQuantity.getText().toString().length()==0){
                    itemQuantity.setError("Quantity is required!");
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        if(itemName.getText().length()>0 && itemCost.getText().length()>0 && itemQuantity.getText().length()>0){
                            String name = itemName.getText().toString();
                            double cost = Double.parseDouble(itemCost.getText().toString());
                            int quantity = Integer.parseInt(itemQuantity.getText().toString());
                            int priority = spinner.getSelectedItemPosition() + 1;
                            Item item = new Item(cost, name, priority, quantity);
                            itemList.add(item);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else {
                         Toast.makeText(getApplicationContext(), "Fill in all required fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });


    }
}

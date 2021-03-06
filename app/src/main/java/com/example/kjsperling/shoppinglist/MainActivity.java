package com.example.kjsperling.shoppinglist;

import android.content.DialogInterface;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {


    final ArrayList<Item> itemList = new ArrayList<>();
    double initialBudget = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Budget budget = new Budget(initialBudget);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final CustomArrayAdapter adapter = new CustomArrayAdapter(this,itemList);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.priority_array,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Button addButton = (Button) findViewById(R.id.addButton);
        final Button budgetButton = (Button) findViewById(R.id.setBudgetbutton);
        final TextView budgetText = (TextView) findViewById(R.id.budget);

        budgetText.setText(budget.displayBudget());
        listView.setAdapter(adapter);


        //on click listener for items in list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                // if the item is not purchased it sets it as purchased, subtracts from budget, adjusts budget
                // and updates the listview
                if(!item.isPurchased()){
                    item.setPurchased(true);
                    budget.subtractFromBudget(item.getTotal());
                    budgetText.setText(budget.displayBudget());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), item.getName()+" Purchased", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //long click listener to handle deletes
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int pos, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View addDialogView = inflater.inflate(R.layout.delete, null);
                builder.setView(addDialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item = (Item) parent.getItemAtPosition(pos);
                        //if the item is purchased, it adds it back to budget
                        if(item.isPurchased()){
                            budget.addToBudget(item.getTotal());
                        }
                        budgetText.setText(budget.displayBudget());
                        itemList.remove(pos);
                        adapter.notifyDataSetChanged();
                        }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });

        //on click listener for the Add Item Button
        //pops an alert dialog that allows user to add item
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

                //field validation
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
                    //override of the onclick for the positive button
                    //allows dialog to stay open when there are errors, strange but it works
                    @Override
                    public void onClick(View v) {
                        if(itemName.getText().length()>0 && itemCost.getText().length()>0 && itemQuantity.getText().length()>0){
                            String name = itemName.getText().toString();
                            double cost = Double.parseDouble(itemCost.getText().toString());
                            int quantity = Integer.parseInt(itemQuantity.getText().toString());
                            int priority = spinner.getSelectedItemPosition() + 1;
                            Item item = new Item(cost, name, priority, quantity);
                            addToList(item);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else {
                         Toast.makeText(getApplicationContext(), "Fill in all required fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        });

        budgetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View addDialogView = inflater.inflate(R.layout.budget, null);
                builder.setView(addDialogView);
                final EditText listBudget = (EditText) addDialogView.findViewById(R.id.budgetValue);
                listBudget.setText(budget.getOriginalBudgetString());
                if(listBudget.getText().toString().length()==0){
                    listBudget.setError("Budget is required!");
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
                    //override of the onclick for the positive button
                    //allows dialog to stay open when there are errors, strange but it works
                    @Override
                    public void onClick(View v) {
                        //if the value is greater then 0, it sets the budget and displays it
                        if(listBudget.getText().length()>0){
                            budget.updateBudget(Double.parseDouble(listBudget.getText().toString()));
                            budgetText.setText(budget.displayBudget());
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

    //Adds and sorts item objects in arraylist using custom comparator
    public void addToList(Item item){
        if(itemList.contains(item)){
            int currentQuantity = itemList.get(itemList.indexOf(item)).getQuantity();
            itemList.get(itemList.indexOf(item)).setQuantity(currentQuantity + item.getQuantity());
        }else {
            itemList.add(item);
        }
        itemList.sort(new ItemComparator());
    }
}

package com.example.kjsperling.shoppinglist;


import java.text.DecimalFormat;

public class Item {

    private double cost;
    private String name;
    private int priority;
    private int quantity;
    private boolean purchased;
    DecimalFormat formatter = new DecimalFormat("#0.00");



    Item(double cost, String name, int priority, int quantity){
        this.cost = cost;
        this.name = name;
        this.priority = priority;
        this.quantity = quantity;
        this.purchased = false;
    }

    public double getCost() {
        return cost;
    }

    public String getCostString(){
        return formatter.format(cost);
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getQuantityString(){
        return Integer.toString(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String toString(){
        return this.getName();
    }

    public double getTotal(){
        return quantity*cost;
    }

    //for use with custom comparator, compares item objects by name ignoring case
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return name != null ? name.equalsIgnoreCase(item.name) : item.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

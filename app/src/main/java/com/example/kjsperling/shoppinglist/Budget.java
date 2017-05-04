package com.example.kjsperling.shoppinglist;

import java.text.DecimalFormat;

public class Budget {

    private double budget;
    private double originalBudget;

    DecimalFormat formatter = new DecimalFormat("#0.00");

    Budget(double budget){
        this.budget=budget;
        this.originalBudget=budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void updateBudget(double newBudget){
        originalBudget = newBudget;
        if(budget<0){
            budget += newBudget;
        }else{
            budget=newBudget;
        }
    }

    public String getOriginalBudgetString(){
        return formatter.format(originalBudget);
    }

    public double getBudget(){
        return budget;
    }

    public String budgetToString(){
        return formatter.format(budget);
    }

    public String displayBudget(){
        return "Budget $" + formatter.format(budget);
    }

    public void subtractFromBudget(double amount){
        budget-= amount;
    }

    public void addToBudget(double amount){
        budget += amount;
    }

}

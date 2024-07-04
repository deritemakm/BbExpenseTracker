/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bbtracker;

/**
 *
 * @author Mikaella Gonzales
 */
public class Sort {
    private String date;
    private double amount;
    private String category;

    public Sort(String date, double amount, String category) {
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }
    
}

package com.example.project3;

import java.text.DecimalFormat;

/**
 * Extends the Account class and is used to create a Checking account
 * @author Daniel Guan, Palak Singh
 */
public class Checking extends Account{

    /**
     * Constructor that creates a checking account object
     * @param fname String holder's first name
     * @param lname String holder's last name
     * @param dob String holder's date of birth
     * @param balance String the amount of balance associated with the instance created
     */
    public Checking(String fname, String lname, String dob, String balance){
        holder = new Profile(fname, lname, dob);
        this.balance = Double.parseDouble(balance);
    }

    /**
     * Alternate constructor that takes a profile as a parameter
     * @param holder Profile information
     * @param balance double amount of money in account
     */
    public Checking(Profile holder, double balance){
        this.holder = holder;
        this.balance = balance;
    }

    /**
     * Method that gets the monthly interest for checking accounts
     * @return double Amount of interest: 1% Annual interest means (1/12)% monthly interest, times balance
     */
    @Override
    public double monthlyInterest() {
        return (0.01 / 12) * balance;
    }

    /**
     * Method that gets the monthly fee for Checking accounts
     * @return double 0 if the account has a balance over $1000, otherwise $12 is the monthly fee
     */
    @Override
    public double monthlyFee() {
        if(balance >= 1000){
            return 0;
        }
        return 12;
    }

    /**
     * Method that helps us identify the account type
     * @return String "Checking" account type
     */
    @Override
    public String getType(){
        return "Checking";
    }

    /**
     * Alternate format of printing Checking account information
     * @return String FirstName LastName DOB (C), (i.e. Kate Lindsey 8/31/2001 (C))
     */
    @Override
    public String accountIdentification(){
        return holder.toString() + " (C)";
    }

    /**
     * Methods that compares accounts mainly by profile
     * @return int comparison of first name, last name, and/or date of birth
     */
    @Override
    public int compareTo(Account account) {
        return holder.compareTo(account.holder);
    }

    /**
     * Original format of printing checking account information
     * @return String Checking::FirstName LastName DOB::Balance $amount, (i.e. Checking::Jason Brown 3/31/1998::Balance $1200.00)
     */
    @Override
    public String toString(){
        DecimalFormat format = new DecimalFormat("0.00");
        return getType() + "::" + holder.toString() + "::Balance $" + format.format(balance);
    }
}
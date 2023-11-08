package com.example.project3;

import java.text.DecimalFormat;

/**
 * Savings is an extension of the parent Account class
 * @author Palak Singh, Daniel Guan
 */

public class Savings extends Account{

    protected boolean isLoyal;

    /**
     * Constructor for creating a Savings account
     * @param fname String holder's first name
     * @param lname String holder's last name
     * @param dob String holder's date of birth
     * @param balance double representing balance in account
     * @param isLoyal String representing whether the holder has loyal customer status
     */
    public Savings(String fname, String lname, String dob, String balance, String isLoyal){
        holder = new Profile(fname, lname, dob);
        this.balance = Double.parseDouble(balance);
        if(isLoyal.equals("0")){
            this.isLoyal = false;
        }else{
            this.isLoyal = true;
        }
    }

    /**
     * Second constructor for creating a Savings account (with boolean loyalt)
     * @param fname String holder's first name
     * @param lname String holder's last name
     * @param dob String holder's date of birth
     * @param balance double representing balance in account
     * @param isLoyal boolean representing whether the holder has loyal customer status
     */
    public Savings(String fname, String lname, String dob, String balance, boolean isLoyal){
        holder = new Profile(fname, lname, dob);
        this.balance = Double.parseDouble(balance);
        this.isLoyal = isLoyal;
    }

    /**
     * Method that gets the monthly interest for Saving accounts
     * @return double Amount of interest: 4% Annual interest means (4/12)% monthly interest, times balance
     * If the customer has loyal customer status, 4.25% Annual interest means (4.25/12)% monthly interest, times balance
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return (0.0425 / 12) * balance;
        }
        return (0.04 / 12) * balance;
    }

    /**
     * Method to get the monthly fee, which depends on loyal customer status
     * @return double 25 representing monthly fee for Savings accounts, 0 if balance is $500 or above
     */
    @Override
    public double monthlyFee() {
        if(balance >= 500){
            return 0;
        }
        return 25;
    }

    /**
     * Method that helps us identify the account type
     * @return String "Savings" account type
     */
    @Override
    public String getType(){
        return "Savings";
    }

    /**
     * Alternate format of printing Savings account information
     * @return String FirstName LastName DOB (S), (i.e. Kate Lindsey 8/31/2001 (S))
     */
    @Override
    public String accountIdentification(){
        return holder.toString() + " (S)";
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
     * Original format of printing Savings account information
     * @return String Savings::FirstName LastName DOB::Balance $amount::Campus
     * (i.e. Savings::Jason Brown 3/31/1998::Balance $1200.00::NEW_BRUNSWICK)
     */
    @Override
    public String toString(){
        DecimalFormat format = new DecimalFormat("0.00");
        if(isLoyal){
            return getType() + "::" + holder.toString() + "::Balance $" + format.format(balance) + "::is loyal";
        }
        return getType() + "::" + holder.toString() + "::Balance $" + format.format(balance);
    }
}
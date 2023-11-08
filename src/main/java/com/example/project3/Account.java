package com.example.project3;

/**
 * Account class is the parent class of Savings, Money Market, Checking, and College Checking classes
 * @author Palak Singh, Daniel Guan
 */

public abstract class Account implements Comparable<Account>{
    protected Profile holder;
    protected double balance;

    /**
     * Abstract method that gets the monthly interest of an account
     * @return double monthly interest depending on the type of account
     */
    public abstract double monthlyInterest();

    /**
     * Abstract method that gets the monthly fee associated with an account
     * @return double monthly fee of an account
     */
    public abstract double monthlyFee();

    /**
     * Get account type
     * @return String type of account ("Checking", "College Checking", "Savings", or "Money Market")
     */
    public abstract String getType();

    /**
     * Update isLoyalty customer loyalty status for Money Market Savings accounts
     */
    public void checkLoyalty() {
    }

    /**
     * Increment the number of withdrawals by 1 for Money Market Savings accounts
     */
    public void increaseWithdrawals() {
    }

    /**
     * Alternate format of printing account information
     * @return String FirstName LastName DOB (Type), (i.e. Kate Lindsey 8/31/2001 (CC))
     */
    public abstract String accountIdentification();

    /**
     * Original format of printing account information
     * @return String Type::FirstName LastName DOB::Balance $amount::(Extra Info), (i.e. Checking::Jason Brown 3/31/1998::Balance $1200.00)
     */
    public abstract String toString();

}
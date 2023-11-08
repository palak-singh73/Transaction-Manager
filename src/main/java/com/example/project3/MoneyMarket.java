package com.example.project3;

import java.text.DecimalFormat;

/**
 * Money Market is an extension of Savings account class, has more benefits and costs
 * @author Palak Singh, Daniel Guan
 */

public class MoneyMarket extends Savings{

    private int withdrawal;

    private final static int MIN_DEPOSIT_TO_AVOID_FEES = 2000;
    private final static int MAX_WITHDRAWALS_TO_AVOID_FEES = 3;
    private final static int FEE_FOR_EXCESS_WITHDRAWALS = 10;

    /**
     * Constructor for creating a Money Market account
     * @param fname String holder's first name
     * @param lname String holder's last name
     * @param dob String holder's date of birth
     * @param balance double representing balance in account
     */
    public MoneyMarket(String fname, String lname, String dob, String balance){
        super(fname, lname, dob, balance, "1");
        this.withdrawal = 0;
    }

    /**
     * Method that gets the monthly interest for Money Market accounts
     * @return double Amount of interest: 4.5% Annual interest means (4.5/12)% monthly interest, times balance
     * If the customer has loyal customer status, 4.75% Annual interest means (4.75/12)% monthly interest, times balance
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return (0.0475 / 12) * balance;
        }
        return (0.045 / 12) * balance;
    }

    /**
     * Method to get the monthly fee, which depends on account balance
     * @return double 25 representing monthly fee for Money Market accounts, 0 if balance is $2000 or above
     */
    @Override
    public double monthlyFee() {
        int fees = 0;
        if(balance < MIN_DEPOSIT_TO_AVOID_FEES){
            fees += 25;
        }
        if(withdrawal > MAX_WITHDRAWALS_TO_AVOID_FEES){
            fees += FEE_FOR_EXCESS_WITHDRAWALS;
        }
        return fees;
    }


    /**
     * Method that helps us identify the account type
     * @return String "Money Market" account type
     */
    @Override
    public String getType(){
        return "Money Market";
    }

    /**
     * Update loyalty based on balance on account balance
     * isLoyal is false (loses loyal customer status) if balance goes under $2000
     * Otherwise isLoyal is true (keep loyal customer status) if balance $2000 and above
     */
    @Override
    public void checkLoyalty(){
        isLoyal = !(balance < 2000);
    }

    /**
     * Method to increment number of withdrawals by 1
     * Deducts the one-time $10 fee if the number of withdrawals exceeds 3
     */
    @Override
    public void increaseWithdrawals(){
        withdrawal++;
    }


    /**
     * Alternate format of printing Money Market Savings account information
     * @return String FirstName LastName DOB (MM), (i.e. Kate Lindsey 8/31/2001 (MM))
     */
    @Override
    public String accountIdentification(){
        return holder.toString() + " (MM)";
    }

    /**
     * Original format of printing Money Market account information
     * @return String Money Market::FirstName LastName DOB::Balance $amount::(isLoyal or blank)::withdrawal: number
     * (i.e. Money::Jason Brown 3/31/1998::Balance $1200.00::isLoyal::withdrawal: 0)
     */
    @Override
    public String toString(){
        DecimalFormat format = new DecimalFormat("0.00");
        if (isLoyal){
            return getType() + "::Savings::" + holder.toString() + "::Balance $" + format.format(balance) + "::is loyal::withdrawal: " + withdrawal;
        }
        return getType() + "::Savings::" + holder.toString() + "::Balance $" + format.format(balance) + "::withdrawal: " + withdrawal;
    }
}
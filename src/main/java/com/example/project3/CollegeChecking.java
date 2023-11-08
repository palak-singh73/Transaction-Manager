package com.example.project3;

import java.text.DecimalFormat;

/**
 * Extends the Checking class and is used to create a College Checking account
 * @author Daniel Guan, Palak Singh
 */
public class CollegeChecking extends Checking{

    private Campus campus;

    /**
     * Constructor that creates a College Checking account object
     * @param fname String holder's first name
     * @param lname String holder's last name
     * @param dob String holder's date of birth
     * @param balance String the amount of balance associated with the instance created
     * @param campus Campus: 0 for NEW_BRUNSWICK, 1 for NEWARK, 2 for CAMDEN
     */
    public CollegeChecking(String fname, String lname, String dob, String balance, String campus){
        super(fname, lname, dob, balance);
        if(campus.equals("0")){
            this.campus = Campus.NEW_BRUNSWICK;
        }else if (campus.equals("1")){
            this.campus = Campus.NEWARK;
        }else if (campus.equals("2")){
            this.campus = Campus.CAMDEN;
        }
    }

    /**
     * Alternate constructor that takes a profile as a parameter
     * @param holder Profile information
     * @param balance double amount of money in account
     * @param campus Campus: 0 for NEW_BRUNSWICK, 1 for NEWARK, 2 for CAMDEN
     */
    public CollegeChecking(Profile holder, double balance, String campus){
        super(holder, balance);
        if(campus.equals("0")){
            this.campus = Campus.NEW_BRUNSWICK;
        }else if (campus.equals("1")){
            this.campus = Campus.NEWARK;
        }else if (campus.equals("2")){
            this.campus = Campus.CAMDEN;
        }else{
            throw new NullPointerException();
        }
    }

    /**
     * Method that gets the monthly fee for College Checking accounts
     * @return double 0
     */
    @Override
    public double monthlyFee() {
        return 0;
    }

    /**
     * Method that helps us identify the account type
     * @return String "College Checking" account type
     */
    @Override
    public String getType(){
        return "College Checking";
    }

    /**
     * Alternate format of printing College Checking account information
     * @return String FirstName LastName DOB (CC), (i.e. Kate Lindsey 8/31/2001 (CC))
     */
    @Override
    public String accountIdentification(){
        return holder.toString() + " (CC)";
    }

    /**
     * Original format of printing College Checking account information
     * @return String College Checking::FirstName LastName DOB::Balance $amount::Campus
     * (i.e. College Checking::Jason Brown 3/31/1998::Balance $1200.00::NEW_BRUNSWICK)
     */
    @Override
    public String toString(){
        DecimalFormat format = new DecimalFormat("0.00");
        return getType() + "::" + holder.toString() + "::Balance $" + format.format(balance) + "::" + campus.toString();
    }
}
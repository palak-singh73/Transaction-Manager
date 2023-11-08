package com.example.project3;

import java.util.Calendar;

/**
 * Writing our own Date class to initialize dates and check for validity
 * @author Daniel Guan, Palak Singh
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int MONTH_30_DAYS = 30;
    public static final int MONTH_31_DAYS = 31;
    public static final int FEBRUARY_DAYS = 28;
    public static final int FEBRUARY_LEAP_DAYS = 29;
    static final int JAN = 1;
    static final int MAR = 3;
    static final int APR = 4;
    static final int MAY = 5;
    static final int JUN = 6;
    static final int JUL = 7;
    static final int AUG = 8;
    static final int SEP = 9;
    static final int OCT = 10;
    static final int NOV = 11;
    static final int DEC = 12;

    /**
     * Constructor to create a date with year, month, and day
     * @param dateString date in format "mm/dd/yyyy"
     */
    public Date(String dateString){
        String[] dateSplit = dateString.split("/", 3);
        this.month = Integer.parseInt(dateSplit[0]);
        this.day = Integer.parseInt(dateSplit[1]);
        this.year = Integer.parseInt(dateSplit[2]);
    }

    /**
     * Overrides the compare to method to find the order of the dates
     * @param date the object to be compared.
     * @return -1, 0, or 1 depending on if this.date comes before, at, or after date, respectively
     */
    @Override
    public int compareTo(Date date){
        if(this.year < date.year){
            return -1;
        }else if (this.year > date.year){
            return 1;
        }

        if (this.month < date.month){
            return -1;
        }else if(this.month > date.month){
            return 1;
        }

        if (this.day < date.day){
            return -1;
        }else if(this.day > date.day){
            return 1;
        }

        return 0;
    }

    /**
     * Getter method for age of a person with respect to current date
     * @return int age of person
     */
    public int age(){
        Calendar today = Calendar.getInstance();
        int today_year = today.get(Calendar.YEAR);
        int today_month = today.get(Calendar.MONTH) + 1;   //to make the range for months 1-12
        int today_day = today.get(Calendar.DATE);
        int age = today_year - year;
        if((today_month < month) || (today_month == month && today_day < day))
            age--;
        return age;
    }

    /**
     * Checks if a date is a valid calendar date
     * @return true or false whether the date is valid
     */
    public boolean isValid(){
        if(year < 0){ // valid year
            return false;
        }
        if((month < JAN) || (month > DEC)){ // valid month
            return false;
        }
        if((month == JAN) || (month == MAR) || (month == MAY) || (month == JUL)
                || (month == AUG) || (month == OCT) || (month == DEC)){
            if((day < 1) || (day > MONTH_31_DAYS)){ // checks months for 31 days max
                return false;
            }
        }else if((month == APR) || (month == JUN) || (month == SEP) || (month == NOV)){
            if((day < 1) || (day > MONTH_30_DAYS)){ // checks months for 30 days max
                return false;
            }
        }else{
            if(leapDetect()){
                if((day < 1) || (day > FEBRUARY_LEAP_DAYS)){ // checks February has 29 days max on a leap year
                    return false;
                }
            }else{
                if((day < 1) || (day > FEBRUARY_DAYS)){ // checks February has 28 days max on a non-leap year
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines whether a given year is a leap year
     * @return true or false whether the year is a leap year
     */
    private boolean leapDetect(){
        if (year % QUADRENNIAL != 0){
            return false;
        }else if (year % CENTENNIAL != 0){
            return true;
        }else if (year % QUARTERCENTENNIAL == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * toString method for Date to print the date
     * @return String returns the date in mm/dd/yyyy format
     */
    public String toString(){
        return month + "/" + day + "/" + year;
    }
}
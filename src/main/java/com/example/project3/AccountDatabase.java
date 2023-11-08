package com.example.project3;

import java.text.DecimalFormat;

/**
 * Account Database performs the actions on the array of accounts (i.e open, close, find, sort, etc.)
 * @author Palak Singh, Daniel Guan
 */

public class AccountDatabase {

    private Account[] accounts;
    private int numAcct;
    static final int NOT_FOUND = -1;

    /**
     * Constructor for making an Account datebase
     * @param accounts array of accounts
     * @param numAcct number of accounts in array
     */
    AccountDatabase(Account[] accounts, int numAcct){
        this.accounts = accounts;
        this.numAcct = numAcct;
    }

    /**
     * Accessor method to get the number of accounts in the array
     * @return int number of accounts
     */
    public int getNumAcct(){
        return numAcct;
    }

    /**
     * Finding a specific account in the array
     * @param account we are trying to find in the array
     * @return int index at which the account is present, -1 if the event is not found
     */
    private int find(Account account){
        if(contains(account)){
            for(int i = 0; i < numAcct; i++){
                if ((accounts[i].holder.compareTo(account.holder) == 0)
                        && (accounts[i].getType().equals(account.getType()))){
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Adds 4 additional spots at the end of the list of accounts
     */
    private void grow(){
        int new_size = accounts.length + 4;
        Account [] new_list = new Account[new_size];
        for (int i = 0; i < accounts.length; i++){
            new_list[i] = accounts[i];
        }
        accounts = new_list;
    }

    /**
     * Checks to see if a given account is in the account list
     * @param account that we are looking for in the account list
     * @return boolean true is the account is found, false if the account is not in the account list
     */
    public boolean contains(Account account){
        for(int i = 0; i < numAcct; i++){
            if ((accounts[i].holder.compareTo(account.holder) == 0)
                    && (accounts[i].getType().equals(account.getType()))){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the account to the next null space
     * If there is no space left, it grows 4 more spaces and then adds to the list
     * @param account that we want to add to the array
     * @return true once the event is successfully added to the list
     */
    public boolean open(Account account){
        Account alternateChecking = null;
        if(account.getType().equals("Checking")){
            alternateChecking = new CollegeChecking(account.holder, account.balance, "0");
        }else if(account.getType().equals("College Checking")){
            alternateChecking = new Checking(account.holder, account.balance);
        }
        if((alternateChecking != null) && (contains(alternateChecking)) || contains(account)){
            return false;
        }
        if((!contains(account))){
            if (accounts.length == numAcct){
                grow();
            }
            accounts[numAcct] = account;
            numAcct++;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Removing a given account from the account list
     * @param account that we are trying to close
     * @return true if the method removes the event, false if even doesn't exist in the list
     */
    public boolean close(Account account){
        if(contains(account)){
            int index_removal = find(account);
            if (index_removal == (accounts.length - 1)){
                accounts[index_removal] = null;
            }else if(index_removal != -1){
                for(int i = index_removal; i < numAcct - 1; i++){
                    accounts[i] = accounts[i + 1];
                }
                accounts[numAcct - 1] = null;
            }
            numAcct--;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Withdraws an amount from the balance of the account
     * @param account we want to withdraw, account.balance being the amount we want to withdraw
     * @return boolean, true if the withdrawal was successful, false if balance will become negative
     */
    public boolean withdraw(Account account){
        if(contains(account)){
            int locationInArray = find(account);
            if(accounts[locationInArray].balance < account.balance){
                return false;
            }
            accounts[locationInArray].balance = accounts[locationInArray].balance - account.balance;
            if (accounts[locationInArray].getType().equals("Money Market")){
                accounts[locationInArray].checkLoyalty();
                accounts[locationInArray].increaseWithdrawals();
            }
            return true;
        }
        return false;
    }

    /**
     * Deposit money into an account
     * @param account to accept the deposit, account.balance being the amount we want to deposit
     */
    public boolean deposit(Account account){
        if(contains(account)){
            int locationInArray = find(account);
            accounts[locationInArray].balance = accounts[locationInArray].balance + account.balance;
            if (accounts[locationInArray].getType().equals("Money Market")) {
                accounts[locationInArray].checkLoyalty();
            }
            return true;
        }
        return false;
    }

    /**
     * Accounts are printed out just as they appear in the list
     * @return String toprint which the list of all the accounts
     */
    private String print(){
        String toprint = "";
        for (int i = 0; i < numAcct; i++) {
            toprint += accounts[i].toString() + "\n";
        }
        if(accounts.length == 0){
            toprint += "Account Database is empty!\n";
        }else{
            toprint += "* end of list.\n";
        }
        return toprint;
    }

    /**
     * Swaps two accounts in the array, given the two indices you want to swap
     */
    private void swap(int a, int b){
        Account temp_account;
        temp_account = accounts[a];
        accounts[a] = accounts[b];
        accounts[b] = temp_account;
    }

    /**
     * Sorts the account based on type and then profile
     */
    private void sortAccounts(){
        for (int i = 1; i < numAcct; i++){
            if(!accounts[i - 1].getType().equals(accounts[i].getType())){
                for (int j = i; j < numAcct; j++) {
                    if(accounts[i - 1].getType().equals(accounts[j].getType())){
                        swap(i, j);
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < numAcct; i++){
            for (int j = i; j<numAcct; j++) {
                if(accounts[i].compareTo(accounts[j]) > 0 && accounts[i].getType().equals(accounts[j].getType())){
                    swap(i, j);
                }
            }
        }
    }

    /**
     * Prints the accounts organized by account type and profile
     * @return String sortedlist which includes the list of all accounts sorted by account type
     */
    public String printSorted(){
        String sortedlist = "";
        if(accounts.length > 0){
            sortedlist += "*Accounts sorted by account type and profile.\n";
            sortAccounts();
            sortedlist += print();
        }else{
            sortedlist += "Account Database is empty!\n";
        }
        return sortedlist;
    }

    /**
     * Prints out all the accounts with their associated fees and interests
     * Sorts the account before printing in case any new accounts had been added since the last sort
     * @return String feesAndInterest that includes a sorted list of all the accounts with their fees and interests
     */
    public String printFeesAndInterests(){
        String feesAndInterest = "";
        sortAccounts();
        DecimalFormat format = new DecimalFormat("0.00");
        for (int i = 0; i < numAcct; i++) {
            feesAndInterest += accounts[i].toString() + "::fee $" + format.format(accounts[i].monthlyFee()) + "::monthly interest $" + format.format(accounts[i].monthlyInterest()) + "\n";
        }
        if(accounts.length == 0){
            feesAndInterest += "Account Database is empty!\n";
        }else{
            feesAndInterest += "* end of list.\n";
        }
        return feesAndInterest;
    }

    /**
     * Prints out the accounts with their updated balances
     * Sorts the account before printing in case any new accounts had been added since the last sort
     * @return String updatedAccounts that includes sorted list of all accounts after the fees and interests have been applied
     */
    public String printUpdatedBalances(){
        String updatedAccounts = "";
        sortAccounts();
        for (int i = 0; i < numAcct; i++) {
            accounts[i].balance = accounts[i].balance + accounts[i].monthlyInterest() - accounts[i].monthlyFee();
            updatedAccounts += accounts[i].toString() + "\n";
        }
        if(accounts.length == 0){
            updatedAccounts += "Account Database is empty!\n";
        }else{
            updatedAccounts += "* end of list.\n";
        }
        return updatedAccounts;
    }
}
package com.example.project3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Transaction Manager Controller contains the button click actions and functionality of our Transaction Manager GUI
 * These functionalities include opening, closing, depositing, withdrawing from accounts, cancel and clear buttons, etc.
 * @author Palak Singh, Daniel Guan
 */

public class TransactionManagerController {
    private static AccountDatabase database;
    @FXML
    private TextArea outputText;
    @FXML
    private TextField fname_octab_input, lname_octab_input, balance_input;
    @FXML
    private DatePicker dob_octab_input;
    @FXML
    private RadioButton is_checking_octab, is_collegechecking_octab, is_savings_octab, is_moneymarket_octab;
    @FXML
    private RadioButton is_NB, is_Newark, is_Camden;
    @FXML
    private CheckBox loyalty_input;
    @FXML
    private TextField fname_dwtab_input, lname_dwtab_input, amount_input;
    @FXML
    private DatePicker dob_dwtab_input;
    @FXML
    private RadioButton is_checking_dwtab, is_collegechecking_dwtab, is_savings_dwtab, is_moneymarket_dwtab;

    /**
     * Constructor of TransactionManagerController
     * To make an account database
     */
    public TransactionManagerController(){
        database = new AccountDatabase(new Account[]{}, 0);
    }

    /**
     * Checking or Money Market radio button selected disables campus and loyalty selection
     */
    @FXML
    protected void checkingOrMoneyMarketSelected(){
        is_NB.setDisable(true);
        is_Camden.setDisable(true);
        is_Newark.setDisable(true);
        loyalty_input.setDisable(true);
    }

    /**
     * College Checking radio button selected enables campus selection and disables loyalty selection
     */
    @FXML
    protected void collegeCheckingSelected(){
        loyalty_input.setDisable(true);
        is_NB.setDisable(false);
        is_Camden.setDisable(false);
        is_Newark.setDisable(false);
    }

    /**
     * Savings radio button selected enables loyalty selection and disables campus selection
     */
    @FXML
    protected void savingsSelected(){
        is_NB.setDisable(true);
        is_Camden.setDisable(true);
        is_Newark.setDisable(true);
        loyalty_input.setDisable(false);
    }

    /**
     * Method for error/alert popups
     * @param message showing what the message must read
     */
    private void invalidPopUp(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method to check the input account is a valid account (valid DOB, appropriate age, etc.)
     * @param account to be checked whether it is valid for creation
     * @param command String command identifier (Open, Close, Withdraw, Deposit, etc.)
     */
    private boolean validAcctCreation(Account account, String command){
        if (!account.holder.getDob().isValid()){
            invalidPopUp("DOB invalid: " + account.holder.getDob().toString() + " not a valid calendar date!");
            return false;
        } else if (account.holder.getDob().age() <= 0) {
            invalidPopUp("DOB invalid: " + account.holder.getDob().toString() + " cannot be today or a future day.");
            return false;
        }else if (account.holder.getDob().age() < 16){
            invalidPopUp("DOB invalid: " + account.holder.getDob().toString() + " under 16.");
            return false;
        }else if (account.getType().equals("College Checking") && account.holder.getDob().age() >= 24 && (!command.equals("Deposit")) && (!command.equals("Withdraw"))){
            invalidPopUp("DOB invalid: " + account.holder.getDob().toString() + " over 24.");
            return false;
        }else if ((command.equalsIgnoreCase("Open")) && (account.balance <= 0)){
            invalidPopUp("Initial deposit cannot be 0 or negative.");
            return false;
        }else if ((command.equalsIgnoreCase("Deposit")) && (account.balance <= 0)){
            invalidPopUp("Deposit - amount cannot be 0 or negative.");
            return false;
        }else if ((command.equalsIgnoreCase("Withdraw")) && (account.balance <= 0)){
            invalidPopUp("Withdraw - amount cannot be 0 or negative.");
            return false;
        }else if(account.getType().equals("Money Market") && (account.balance < 2000) && (command.equals("Open"))) {
            invalidPopUp("Minimum of $2000 to open a Money Market account.");
            return false;
        }else if (account.getType().equals("College Checking") && (command.equals("Open"))){
            String info = account.toString();
        }
        return true;
    }

    /**
     * Reformatting the date selection into the format we want (MM/DD/YYYY)
     * @param date from date picker selection in format YYYY-MM-DD
     * @return reformatted date in form (MM/DD/YYYY)
     */
    private String dateFormatter(String date){
        String formatedDate = "";
        String[] dateSplit = date.split("-", 3);
        formatedDate += dateSplit[1] + "/";
        formatedDate += dateSplit[2] + "/";
        formatedDate += dateSplit[0];
        return formatedDate;

    }

    /**
     * Changes the date into the desired format we want, based on text input or calendar date selection in Date Picker
     * @param dob DatePicker input
     * @return String date of form MM/DD/YYYY
     */
    private String dobToString(DatePicker dob){
        if(dob.getValue() == null){
            return dob.getEditor().getText();
        }else{
            return dateFormatter(dob.getValue().toString());
        }
    }

    /**
     * Finds which campus radio button is selected
     * @return String campus name selected
     */
    private String campusSelected(){
        if(is_NB.isSelected()){
            return "0";
        }else if(is_Newark.isSelected()){
            return "1";
        }else if(is_Camden.isSelected()){
            return "2";
        }
        return "";
    }

    /**
     * Used to disable all the campus radio buttons
     */
    private void disableCampusSelection(){
        is_NB.setDisable(true);
        is_Camden.setDisable(true);
        is_Newark.setDisable(true);
    }

    /**
     * For when the open button is selected
     * Method to process the inputs, create and open a new valid account that is not in the database already.
     */
    @FXML
    protected void openButtonClicked() {
        Account accntToAdd = null;
        String date = dobToString(dob_octab_input);
        try{
            if(fname_octab_input.getText().isEmpty() || lname_octab_input.getText().isEmpty() || date.isEmpty() || balance_input.getText().isEmpty() ||(is_collegechecking_octab.isSelected() && campusSelected().isEmpty())) {
                invalidPopUp("Missing data for opening an account.");
            }else if(is_checking_octab.isSelected()){
                accntToAdd = new Checking(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText());
            } else if (is_collegechecking_octab.isSelected()) {
                accntToAdd = new CollegeChecking(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText(), campusSelected());
            } else if (is_savings_octab.isSelected()) {
                accntToAdd = new Savings(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText(), loyalty_input.isSelected());
            } else if (is_moneymarket_octab.isSelected()) {
                accntToAdd = new MoneyMarket(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText());
            }
            if(accntToAdd!=null && validAcctCreation(accntToAdd, "Open")){
                if (database.open(accntToAdd)){
                    outputText.appendText(accntToAdd.accountIdentification() + " opened.\n");
                }else{
                    outputText.appendText(accntToAdd.accountIdentification() + " is already in the database.\n");
                }
                clearOCButtonClicked();
                disableCampusSelection();
            }
        }catch (NoSuchElementException e){
            invalidPopUp("Missing data for opening an account.");
        }catch (NumberFormatException e){
            invalidPopUp("Not a valid amount.");
        }catch (NullPointerException e){
            invalidPopUp("Invalid Campus Code.");
        }
    }

    /**
     * For when the close button is clicked
     * Method to process the inputs and close a valid account that is in the database already.
     */
    @FXML
    protected void closeButtonClicked(){
        Account accntToClose = null;
        String date = dobToString(dob_octab_input);
        disableCampusSelection();
        balance_input.setText("0");
        try{
            if(fname_octab_input.getText().isEmpty() || lname_octab_input.getText().isEmpty() || date.isEmpty() || (is_collegechecking_octab.isSelected() && campusSelected().isEmpty())){
                invalidPopUp("Missing data for closing an account.");
            }else if(is_checking_octab.isSelected()){
                accntToClose = new Checking(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText());
            } else if (is_collegechecking_octab.isSelected()) {
                accntToClose = new CollegeChecking(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText(), campusSelected());
            } else if (is_savings_octab.isSelected()) {
                accntToClose = new Savings(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText(), loyalty_input.isSelected());
            } else if (is_moneymarket_octab.isSelected()) {
                accntToClose = new MoneyMarket(fname_octab_input.getText(), lname_octab_input.getText(), date, balance_input.getText());
            }
            if (accntToClose!=null && validAcctCreation(accntToClose, "Close")){
                if (!database.close(accntToClose)){
                    outputText.appendText(accntToClose.accountIdentification() + " is not in the database.\n");
                }else{
                    outputText.appendText(accntToClose.accountIdentification() + " has been closed.\n");
                }
                clearOCButtonClicked();
            }
        }catch (NoSuchElementException e){
            invalidPopUp("Missing data for closing an account.");
        }catch (NullPointerException e){
            invalidPopUp(accntToClose.accountIdentification() + " is not in the database.");
        }
    }

    /**
     * For when the clear button is clicked on the Open/Close tab
     * Clears all input fields (first name, last name, date, etc.) and resets all the selections on the Open/Close tab
     */
    @FXML
    protected void clearOCButtonClicked(){
        fname_octab_input.clear();
        lname_octab_input.clear();
        dob_octab_input.getEditor().clear();
        balance_input.clear();
        is_checking_octab.setSelected(false);
        is_collegechecking_octab.setSelected(false);
        is_savings_octab.setSelected(false);
        is_moneymarket_octab.setSelected(false);
        is_NB.setSelected(false);
        is_Newark.setSelected(false);
        is_Camden.setSelected(false);
        loyalty_input.setSelected(false);
        loyalty_input.setDisable(true);
    }

    /**
     * For when the clear button is clicked on the Deposit/Withdraw tab
     * Clears all input fields (first name, last name, date, etc.) and selections on the Deposit/Withdraw tab
     */
    @FXML
    protected void clearDWButtonClicked(){
        fname_dwtab_input.clear();
        lname_dwtab_input.clear();
        dob_dwtab_input.getEditor().clear();
        amount_input.clear();
        is_checking_dwtab.setSelected(false);
        is_collegechecking_dwtab.setSelected(false);
        is_savings_dwtab.setSelected(false);
        is_moneymarket_dwtab.setSelected(false);
    }

    /**
     * For when the Deposit button is clicked
     * Method to process the input line, find and deposit to an account in the database already.
     */
    @FXML
    protected void depositButtonClicked(){
        Account accntToDeposit = null;
        String date = dobToString(dob_dwtab_input);
        try{
            if(fname_dwtab_input.getText().isEmpty() || lname_dwtab_input.getText().isEmpty() || date.isEmpty()){
                invalidPopUp("Missing data for depositing into an account.");
            }else if (is_checking_dwtab.isSelected()){
                accntToDeposit = new Checking(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText());
            }else if (is_collegechecking_dwtab.isSelected()){
                accntToDeposit = new CollegeChecking(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText(), "");
            }else if (is_savings_dwtab.isSelected()){
                accntToDeposit = new Savings(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText(), "");
            }else if (is_moneymarket_dwtab.isSelected()){
                accntToDeposit = new MoneyMarket(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText());
            }
            if(accntToDeposit!=null && validAcctCreation(accntToDeposit, "Deposit")){
                if(database.deposit(accntToDeposit)){
                    outputText.appendText(accntToDeposit.accountIdentification() + " Deposit - balance updated.\n");
                }else{
                    outputText.appendText(accntToDeposit.accountIdentification() + "  is not in the database.\n");
                }
                clearDWButtonClicked();
            }
        }catch (NoSuchElementException e){
            invalidPopUp("Missing data for depositing into an account.");
        }catch (NumberFormatException e){
            invalidPopUp("Not a valid amount.");
        }catch (NullPointerException e){
            invalidPopUp("Invalid Campus Code");
        }
    }

    /**
     * Method to process the input line, find and withdraw from an account in the database already.
     */
    @FXML
    protected void withdrawButtonClicked(){
        Account accntToWithdraw = null;
        String date = dobToString(dob_dwtab_input);
        try{
            if(fname_dwtab_input.getText().isEmpty() || lname_dwtab_input.getText().isEmpty() || date.isEmpty()){
                invalidPopUp("Missing data for withdrawing from an account.");
            }else if (is_checking_dwtab.isSelected()){
                accntToWithdraw = new Checking(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText());
            }else if (is_collegechecking_dwtab.isSelected()){
                accntToWithdraw = new CollegeChecking(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText(), "");
            }else if (is_savings_dwtab.isSelected()){
                accntToWithdraw = new Savings(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText(), "");
            }else if (is_moneymarket_dwtab.isSelected()){
                accntToWithdraw = new MoneyMarket(fname_dwtab_input.getText(), lname_dwtab_input.getText(), date, amount_input.getText());
            }
            if(accntToWithdraw!= null && validAcctCreation(accntToWithdraw, "Withdraw")){
                if(database.withdraw(accntToWithdraw)){
                    outputText.appendText(accntToWithdraw.accountIdentification() + " Withdraw - balance updated.\n");
                }else if(database.contains(accntToWithdraw)){
                    outputText.appendText(accntToWithdraw.accountIdentification() + " Withdraw - insufficient funds.\n");
                }else{
                    outputText.appendText(accntToWithdraw.accountIdentification() + " is not in the database.\n");
                }
                clearDWButtonClicked();
            }
        }catch (NoSuchElementException e){
            invalidPopUp("Missing data for withdrawing from an account.");
        }catch (NumberFormatException e){
            invalidPopUp("Not a valid amount.");
        }catch (NullPointerException e){
            invalidPopUp("Invalid Campus Code");
        }
    }

    /**
     * For when the Print All Accounts button is clicked
     * Prints all accounts in database (sorted by accounts) in the output text field
     */
    @FXML
    protected void printAllButtonClicked(){
        outputText.clear();
        outputText.appendText(database.printSorted());
    }

    /**
     * For when the Print Fees and Interests button is clicked
     * Prints each accounts' interest and fees in the output text field
     */
    @FXML
    protected void printInterestFeesButtonClicked(){
        outputText.clear();
        if (database.getNumAcct() == 0) {
            outputText.appendText("Account Database is empty!");
        }else{
            outputText.appendText("*list of accounts with fee and monthly interest\n" + database.printFeesAndInterests());
        }
    }

    /**
     * For when the Update Accounts button is clicked
     * Prints all accounts in the database, with updated balances, into the output text field
     **/
    @FXML
    protected void updateAccountsButtonClicked(){
        outputText.clear();
        if (database.getNumAcct() == 0) {
            outputText.appendText("Account Database is empty!");
        }else{
            outputText.appendText("*list of accounts with fees and interests applied\n" + database.printUpdatedBalances());
        }
    }

    /**
     * Load accounts from a .txt file and starts to read it using BufferReader
     */
    @FXML
    protected void loadAccountsButtonClicked(){
        outputText.clear();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Accounts Text File to Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.isEmpty()){
                    lineProcessor(line);
                }
            }
        }catch (FileNotFoundException e){
            outputText.appendText("FILE NOT FOUND.");
        }catch (IOException e){
            outputText.appendText("ERROR: TRY RELOADING THE FILE OR ENTERING DATA MANUALLY.");
        }catch (RuntimeException e){
            outputText.appendText("NO FILE SELECTED.");
        }

    }

    /**
     * Process the lines of an input file, opens all accounts read
     * @param line each line from the input txt file
     */
    private void lineProcessor(String line){
        StringTokenizer accountInfo = new StringTokenizer(line, ",");
        String type = accountInfo.nextToken();
        Account accntToAdd = null;
        try{
            if (type.equalsIgnoreCase("C")){
                accntToAdd = new Checking(accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken());
            }else if (type.equalsIgnoreCase("CC")){
                accntToAdd = new CollegeChecking(accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken());
            }else if (type.equalsIgnoreCase("S")){
                accntToAdd = new Savings(accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken());
            }else if (type.equalsIgnoreCase("MM")){
                accntToAdd = new MoneyMarket(accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken(), accountInfo.nextToken());
            }
            if (validAcctCreation(accntToAdd, "Open")){
                if (database.open(accntToAdd)){
                    outputText.appendText(accntToAdd.accountIdentification() + " opened.\n");
                }else{
                    outputText.appendText(accntToAdd.accountIdentification() + " is already in the database.\n");
                }
            }
        }catch (NoSuchElementException e){
            invalidPopUp("Missing data for opening an account.");
        }catch (NumberFormatException e){
            invalidPopUp("Not a valid number input.");
        }catch (NullPointerException e){
            invalidPopUp("Invalid Campus Code");
        }
    }
}
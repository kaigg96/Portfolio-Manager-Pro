package ui;

import model.Company;
import model.EventLog;
import model.ListedCompanies;
import model.Portfolio;
import model.exceptions.CompanyNotFoundException;
import model.exceptions.InsufficientFundsException;
import model.exceptions.NegativeAmountException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static model.ListedCompanies.findInListedCompanies;

// The code in this class was adapted from code in the given AccountNotRobust - TellerApp class
//   file (especially parts using the scanner), and JsonSterilizationDemo - WorkRoomApp class.

// Runs the Portfolio Manager app
public class ConsoleUIApp {

    private static final String PATH = "./data/portfolio.json";
    private Portfolio yourPortfolio;
    private Scanner userInput;
    private JsonWriter writer;
    private JsonReader reader;

    // EFFECTS: constructs a PortfolioApp then runs the application
    public ConsoleUIApp() {
        yourPortfolio = new Portfolio(0);
        userInput = new Scanner(System.in);
        writer = new JsonWriter(PATH);
        reader = new JsonReader(PATH);
        runPortfolio();
    }

    // EFFECTS: runs the PortfolioApp
    private void runPortfolio() {
        boolean keepGoing = true; //initialize keepGoing as true
        String nextStep; //initialize ui as null

        while (keepGoing) { //continues as long as keepGoing bool is true
            displayMenu(); //start by displaying menu
            nextStep = userInput.next();

            if (nextStep.equals("9")) {
                EventLog.getInstance().printEvents();
                keepGoing = false; //end the loop if the user chooses "Exit Portfolio Manager Pro"
            } else {
                processUserInput(nextStep);
            }
        }
        System.out.println("Thank you for using Portfolio Manager Pro!");
    }

    // EFFECTS: displays the main menu
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Deposit cash"); // \t adds an indent to make the display nicer
        System.out.println("\t2 -> Withdraw cash");
        System.out.println("\t3 -> Purchase stocks");
        System.out.println("\t4 -> Sell stocks");
        System.out.println("\t5 -> View my portfolio");
        System.out.println("\t6 -> Browse listed companies");
        System.out.println("\t7 -> Save portfolio");
        System.out.println("\t8 -> Load portfolio");
        System.out.println("\t9 -> Exit Portfolio Manager Pro");
    }

    // MODIFIES: this
    // EFFECTS: process user input from the main menu
    //             inputting 9 exits the app (part of the runPortfolio method)
    private void processUserInput(String userInput) {
        if (userInput.equals("1")) {
            doCashDeposit();
        } else if (userInput.equals("2")) {
            doCashWithdrawal();
        } else if (userInput.equals("3")) {
            purchaseStocksMenu();
        } else if (userInput.equals("4")) {
            sellStocksMenu();
        } else if (userInput.equals("5")) {
            displayCurrentHoldings();
        } else if (userInput.equals("6")) {
            displayListedCompanies();
        } else if (userInput.equals("7")) {
            savePortfolio();
        } else if (userInput.equals("8")) {
            loadPortfolio();
        } else {
            System.out.println("Invalid selection! Please try again:");
        }
    }

    // MODIFIES: this
    // EFFECTS: deposits cash into YourPortfolio
    private void doCashDeposit() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Please enter the amount of cash you would like to deposit:");
        double amount = userInput.nextDouble();

        try {
            yourPortfolio.addToBalance(amount);
        } catch (NegativeAmountException e) {
            negativeDepositEntry();
        }
        System.out.println("Deposit successful! Your new balance is: " + yourPortfolio.getCashBalance());
    }

    // MODIFIES: this
    // EFFECTS: displays negative deposit amount menu. Gives user the option to try again or return to main menu
    private void negativeDepositEntry() {
        System.out.println("Please enter a POSITIVE amount to deposit. Would you like to try again?");
        System.out.println("Select from:");
        System.out.println("1 -> Deposit a different amount.");
        System.out.println("2 -> Return to main menu.");

        int nextChoice = userInput.nextInt();

        if (nextChoice == 1) {
            doCashDeposit();
        } else {
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: withdraws cash from YourPortfolio
    private void doCashWithdrawal() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Please enter the amount of cash you would like to withdraw:");
        double amount = userInput.nextDouble();

        try {
            yourPortfolio.subFromBalance(amount);
        } catch (NegativeAmountException e) {
            negativeWithdrawalEntry();
        } catch (InsufficientFundsException e) {
            insufficientFundsEntry();
        }
        System.out.println("Withdrawal successful! Your new balance is: " + yourPortfolio.getCashBalance());
    }

    // MODIFIES: this
    // EFFECTS: displays insufficient funds' menu. Gives user the option to try again or return to main menu
    private void insufficientFundsEntry() {
        System.out.println("Insufficient balance! Would you like to try again?");
        System.out.println("Select from:");
        System.out.println("1 -> Withdraw a different amount.");
        System.out.println("2 -> Return to main menu.");

        int nextChoice = userInput.nextInt();

        if (nextChoice == 1) {
            doCashWithdrawal();
        } else {
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays negative withdrawal amount menu. Gives user the option to try again or return to main menu
    private void negativeWithdrawalEntry() {
        System.out.println("Please enter a POSITIVE amount to withdraw. Would you like to try again?");
        System.out.println("Select from:");
        System.out.println("1 -> Withdraw a different amount.");
        System.out.println("2 -> Return to main menu.");

        int nextChoice = userInput.nextInt();

        if (nextChoice == 1) {
            doCashWithdrawal();
        } else {
            displayMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays purchasing stocks menu. Gives user the option buy shares, browse listed companies or exit
    private void purchaseStocksMenu() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Please select from:");
        System.out.println("1 -> Buy shares of a listed company");
        System.out.println("2 -> Browse listed companies");
        System.out.println("3 -> Return to main menu.");

        int nextChoice = userInput.nextInt();

        if (nextChoice == 1) {
            doPurchaseStocks();
        } else if (nextChoice == 2) {
            displayListedCompanies();
            purchaseStocksMenu();
        } else if (nextChoice == 3) {
            displayMenu();
        } else {
            System.out.println("Invalid selection! Please try again:");
            purchaseStocksMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user enter a company name/ticker and a number of shares,
    //              if company is in ListedCompanies && sufficient balance, buy shares
    //              else return to purchase stocks menu
    private void doPurchaseStocks() {
        System.out.println("Please enter the name or ticker of the company you'd like to buy shares of:");
        String companyID = userInput.next();
        System.out.println("Please enter the number of shares you'd like to purchase:");
        int shareNum = userInput.nextInt();

        try {
            findInListedCompanies(companyID);
        } catch (CompanyNotFoundException e) {
            System.out.println("Company not found!");
            purchaseStocksMenu();
        }
        try {
            yourPortfolio.purchaseShares(companyID, shareNum);
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient balance!");
            purchaseStocksMenu();
        } catch (CompanyNotFoundException e) { //should never happen since we just checked for it above
            throw new RuntimeException();
        }
        System.out.println("Shares successfully purchased!");
    }

    // EFFECTS: displays all ListedCompanies
    private void displayListedCompanies() {
        for (ListedCompanies c : ListedCompanies.values()) {
            System.out.println("- Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMktcap());
        }
    }

    // MODIFIES: this
    // EFFECTS: displays sell stocks menu. Gives user the option to sell shares, browse held companies or exit
    private void sellStocksMenu() {
        System.out.println("Please select from:");
        System.out.println("1 -> Sell shares of a company in your portfolio");
        System.out.println("2 -> Browse current holdings");
        System.out.println("3 -> Return to main menu.");

        int nextChoice = userInput.nextInt();

        if (nextChoice == 1) {
            doSellStocks();
        } else if (nextChoice == 2) {
            displayCurrentHoldings();
            sellStocksMenu();
        } else if (nextChoice == 3) {
            displayMenu();
        } else {
            System.out.println("Invalid selection! Please try again:");
            sellStocksMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user enter a company name/ticker and a number of shares,
    //              if company is in ListedCompanies && sufficient balance, buy shares
    //              else return to sell stocks menu
    private void doSellStocks() {
        System.out.println("Please enter the name or ticker of the company you'd like to sell shares of:");
        String companyID = userInput.next();
        System.out.println("Please enter the number of shares you'd like to sell:");
        int shareNum = userInput.nextInt();

        Company c = yourPortfolio.findCompanyInStocks(companyID);
        if (c != null) {
            try {
                yourPortfolio.sellShares(companyID, shareNum);
            } catch (NegativeAmountException e) {
                System.out.println("You cannot sell that many shares!");
                sellStocksMenu();
            }
        } else {
            System.out.println("Company not found in list of current holdings");
            sellStocksMenu();
        }
    }

    // EFFECTS: displays cash balance and list of current holdings
    private void displayCurrentHoldings() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Your current holdings are: ");
        for (Company c : yourPortfolio.getStocks()) {
            System.out.println("- Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Shares held: " + c.getSharesHeld()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMarketCap());
        }
    }

    // EFFECTS: save yourPortfolio to a JSON file
    private void savePortfolio() {
        try {
            writer.openWriter();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.write(yourPortfolio);
        writer.closeWriter();
        System.out.println("Your portfolio has been saved to " + PATH);
    }

    // EFFECTS: load yourPortfolio to a JSON file
    private void loadPortfolio() {
        try {
            yourPortfolio = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Your portfolio has been loaded from " + PATH);
    }
}


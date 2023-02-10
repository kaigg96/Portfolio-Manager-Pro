package ui;

import model.Company;
import model.Portfolio;
import model.ListedCompanies;

import java.util.Scanner;

import static model.ListedCompanies.findInListedCompanies;

// The code in this class is largely based on code from the given TellerApp class in the AccountNotRobust - TellerApp
//   file, with modifications to suit the needs of this program.
public class PortfolioApp {

    private Portfolio yourPortfolio;
    private Scanner userInput;

    public PortfolioApp() {
        runPortfolio();
    }

    private void runPortfolio() {
        boolean keepGoing = true; //initialize keepGoing as true
        String nextStep = null; //initialize ui as null

        initialize();

        while (keepGoing) { //continues as long as keepGoing bool is true
            displayMenu(); //start by displaying menu
            nextStep = userInput.next();
            nextStep = nextStep.toLowerCase();

            if (nextStep.equals("7")) {
                keepGoing = false; //end the loop if the user inputs "exit"
            } else {
                processUserInput(nextStep);
            }
        }
        System.out.println("Thank you for using Portfolio Manager Pro!");
    }

    private void initialize() {
        yourPortfolio = new Portfolio(0);
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n"); //not sure if this is needed
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Deposit cash");
        System.out.println("\t2 -> Withdraw cash");
        System.out.println("\t3 -> Purchase stocks");
        System.out.println("\t4 -> Sell stocks");
        System.out.println("\t5 -> View my portfolio");
        System.out.println("\t6 -> Browse listed companies");
        System.out.println("\t7 -> Exit Portfolio Manager Pro");
    }

    private void processUserInput(String userInput) {
        if (userInput.equals("1")) {
            // go to deposit menu
            doCashDeposit();
        } else if (userInput.equals("2")) {
            // go to withdrawal menu
            doCashWithdrawal();
        } else if (userInput.equals("3")) {
            //go to purchasing menu
            purchaseStocksMenu();
        } else if (userInput.equals("4")) {
            //go to selling menu
            sellStocksMenu();
        } else if (userInput.equals("5")) {
            // view portfolio menu
            displayCurrentHoldings();
        } else if (userInput.equals("6")) {
            //browse listed companies menu
            displayListedCompanies();
 //       } else if (userInput.equals("7")) {
            //quit
  //          doSomething7();
        } else {
            System.out.println("Invalid selection! Please try again:");
            displayMenu();
        }
    }

    private void doCashDeposit() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Please enter the amount of cash you would like to deposit:");
        double amount = userInput.nextDouble();

        if (amount > 0) {
            yourPortfolio.addToBalance(amount);
            System.out.println("Deposit successful! Your new balance is: " + yourPortfolio.getCashBalance());
        } else {
            negativeDepositEntry();
        }
    }

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

    //method too long probably
    private void doCashWithdrawal() {
        System.out.println("Your current cash balance is " + yourPortfolio.getCashBalance());
        System.out.println("Please enter the amount of cash you would like to withdraw:");
        double amount = userInput.nextDouble();

        if (amount > yourPortfolio.getCashBalance()) {
            insufficientFundsEntry();
        } else if (amount <= 0) {
            negativeWithdrawalEntry();
        } else {
            yourPortfolio.subFromBalance(amount);
        }
    }

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

    private void doPurchaseStocks() {
        System.out.println("Please enter the name or ticker of the company you'd like to buy shares of:");
        String companyID = userInput.next();
        System.out.println("Please enter the number of shares you'd like to purchase:");
        int shareNum = userInput.nextInt();

        ListedCompanies c = findInListedCompanies(companyID);
        if (c != null) {
            if (yourPortfolio.getCashBalance() >= shareNum * c.getSharePrice()) {
                yourPortfolio.purchaseShares(companyID, shareNum);
            } else {
                System.out.println("Insufficient balance!");
                purchaseStocksMenu();
            }
        } else {
            System.out.println("Company not found!");
            purchaseStocksMenu();
        }
    }

    private void displayListedCompanies() {
        for (ListedCompanies c : ListedCompanies.values()) {
            System.out.println("- Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMktcap());
        }
    }

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

    private void doSellStocks() {
        System.out.println("Please enter the name or ticker of the company you'd like to sell shares of:");
        String companyID = userInput.next();
        System.out.println("Please enter the number of shares you'd like to sell:");
        int shareNum = userInput.nextInt();

        Company c = yourPortfolio.findCompanyInStocks(companyID);
        if (c != null) {
            if (c.getSharesHeld() >= shareNum) {
                yourPortfolio.sellShares(companyID, shareNum);
            } else {
                System.out.println("You cannot sell more shares than you currently hold!");
                sellStocksMenu();
            }
        } else {
            System.out.println("Company not found in list of current holdings");
            sellStocksMenu();
        }
    }

    private void displayCurrentHoldings() {
        for (Company c : yourPortfolio.getStocks()) {
            System.out.println("- Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Shares held: " + c.getSharesHeld()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMarketCap());
        }
    }
}


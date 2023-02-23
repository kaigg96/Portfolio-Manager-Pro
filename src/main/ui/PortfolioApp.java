package ui;

import model.Company;
import model.Portfolio;
import model.ListedCompanies;

import java.sql.SQLOutput;
import java.util.Scanner;

import static model.ListedCompanies.findInListedCompanies;

// The code in this class is somewhat based on code from the given TellerApp class in the AccountNotRobust - TellerApp
//   file (especially parts using the scanner), with modifications to suit the needs of this program.

// Runs the Portfolio Manager app
public class PortfolioApp {

    private Portfolio yourPortfolio;
    private Scanner userInput;

    public PortfolioApp() {
        runPortfolio();
    }

    // EFFECTS: runs the PortfolioApp
    private void runPortfolio() {
        boolean keepGoing = true; //initialize keepGoing as true
        String nextStep; //initialize ui as null

        initialize();

        while (keepGoing) { //continues as long as keepGoing bool is true
            displayMenu(); //start by displaying menu
            nextStep = userInput.next();

            if (nextStep.equals("7")) {
                keepGoing = false; //end the loop if the user chooses "Exit Portfolio Manager Pro"
            } else {
                processUserInput(nextStep);
            }
        }
        System.out.println("Thank you for using Portfolio Manager Pro!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the PortfolioApp
    private void initialize() {
        yourPortfolio = new Portfolio(0);
        userInput = new Scanner(System.in);
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
        System.out.println("\t7 -> Exit Portfolio Manager Pro");
    }

    // MODIFIES: this
    // EFFECTS: process user input from the main menu
    //             inputting 7 exits the app (part of the runPortfolio method)
    private void processUserInput(String userInput) {
        switch (userInput) {
            case "1":
                doCashDeposit();
                break;
            case "2":
                doCashWithdrawal();
                break;
            case "3":
                purchaseStocksMenu();
                break;
            case "4":
                sellStocksMenu();
                break;
            case "5":
                displayCurrentHoldings();
                break;
            case "6":
                displayListedCompanies();
                break;
            default:
                System.out.println("Invalid selection! Please try again:");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: deposits cash into YourPortfolio
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

        if (amount > yourPortfolio.getCashBalance()) {
            insufficientFundsEntry();
        } else if (amount <= 0) {
            negativeWithdrawalEntry();
        } else {
            yourPortfolio.subFromBalance(amount);
            System.out.println("Withdrawal successful! Your new balance is: " + yourPortfolio.getCashBalance());
        }
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

        ListedCompanies c = findInListedCompanies(companyID);
        if (c != null) {
            if (yourPortfolio.getCashBalance() >= shareNum * c.getSharePrice()) {
                yourPortfolio.purchaseShares(companyID, shareNum);
                System.out.println("Shares successfully purchased!");
            } else {
                System.out.println("Insufficient balance!");
                purchaseStocksMenu();
            }
        } else {
            System.out.println("Company not found!");
            purchaseStocksMenu();
        }
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
}


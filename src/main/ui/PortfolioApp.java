package ui;

import model.Portfolio;
import model.Company;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PortfolioApp {

    public PortfolioApp() {
        runPortfolio();
    }

    public runPortfolio() {
        boolean keepGoing = true;
        String userInput = null;

        initialize();

        while (keepGoing) {
            menu();
            userInput = input.next();
            userInput = userInput.toLowerCase();

            if (userInput.equals("exit")) {
                keepGoing = false;
            } else {
                processUserInput(userInput);
            }
        }

        System.out.println("Thank you for using Portfolio Manager Pro!");
    }

    private void processUserInput(String userInput) {
        if (userInput.equals("1")) {
            doSomething();
        } else if (userInput.equals("2")) {
            doSomething2();
        } else if (userInput.equals("3")) {
            doSomething3();
        } else if (userInput.equals("4")) {
            doSomething4();
        } else {
            doSomething5();
        }
    }

    private void initialize() {
        p1 = new Portfolio(0);
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
    }

    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\td -> deposit");
        System.out.println("\tw -> withdraw");
        System.out.println("\tt -> transfer");
        System.out.println("\tq -> quit");
    }

}
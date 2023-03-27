package ui;

import model.Company;
import model.ListedCompanies;
import model.Portfolio;
import model.exceptions.CompanyNotFoundException;
import model.exceptions.InsufficientFundsException;
import model.exceptions.NegativeAmountException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//Runs the GUIApp
public class GraphicalUIApp extends JFrame {

    private static final String PATH = "./data/portfolio.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    Portfolio yourPortfolio;
    JsonWriter writer = new JsonWriter(PATH);
    JsonReader reader = new JsonReader(PATH);
    JFrame jf;

    public GraphicalUIApp() {
        openingMenu();

    }

    //EFFECTS: creates the opening menu for the GUIApp
    public void openingMenu() {

        int titleWidth = 300;
        int titleHeight = 100;
        int buttonWidth = 400;
        int buttonHeight = 100;

        this.jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Portfolio Manager Pro");
        JButton newPortfolioButton = new JButton("New Portfolio");
        JButton loadPortfolioButton = new JButton("Load Portfolio");

        //setup
        title.setBounds(WIDTH / 2 - titleWidth / 2,10, titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        newPortfolioButton.setBounds(WIDTH / 2  - buttonWidth / 2, HEIGHT * 1 / 3 - buttonHeight / 2,
                buttonWidth, buttonHeight);
        loadPortfolioButton.setBounds(WIDTH / 2 - buttonWidth / 2, HEIGHT * 2 / 3 - buttonHeight / 2,
                buttonWidth, buttonHeight);

        jf.add(title);
        jf.add(newPortfolioButton);
        jf.add(loadPortfolioButton);

        jf.setLayout(null);
        jf.setVisible(true);

        newPortfolioButton.addActionListener(e -> {
            jf.dispose();
            Portfolio portfolio = new Portfolio(0);
            mainMenu(portfolio);
        });

        loadPortfolioButton.addActionListener(e -> {
            jf.dispose();
            Portfolio portfolio = new Portfolio(0);
            try {
                portfolio = reader.read();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(jf,
                        "ERROR: There are no saved portfolios."
                               + "Loading a new portfolio instead.");
            }
            mainMenu(portfolio);
        });
    }

    public void mainMenu(Portfolio p) {

        int titleWidth = 300;
        int titleHeight = 100;
        int buttonWidth = 200;
        int buttonHeight = 75;
        int topButtonY = HEIGHT * 1 / 4 - buttonHeight / 2;

        this.jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        yourPortfolio = p;
        JLabel title = new JLabel("Portfolio Manager Pro");
        JButton depositCashButton = new JButton("Deposit");
        JButton withdrawCashButton = new JButton("Withdraw");
        JButton purchaseStocksButton = new JButton("Purchase Stocks");
        JButton sellStocksButton = new JButton("Sell Stocks");
        JButton viewPortfolioButton = new JButton("View Portfolio");
        JButton browseCompaniesButton = new JButton("Browse Listed Companies");
        JButton saveButton = new JButton("Save Portfolio");
        JButton exitButton = new JButton("Exit");

        title.setBounds(WIDTH / 2 - titleWidth / 2,10, titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        depositCashButton.setBounds(WIDTH / 4  - buttonWidth / 2, topButtonY,
                buttonWidth, buttonHeight);
        withdrawCashButton.setBounds(WIDTH / 4  - buttonWidth / 2, topButtonY + buttonHeight + 20,
                buttonWidth, buttonHeight);
        purchaseStocksButton.setBounds(WIDTH / 4  - buttonWidth / 2, topButtonY + (buttonHeight + 20) * 2,
                buttonWidth, buttonHeight);
        sellStocksButton.setBounds(WIDTH / 4  - buttonWidth / 2, topButtonY + (buttonHeight + 20) * 3,
                buttonWidth, buttonHeight);
        viewPortfolioButton.setBounds(WIDTH * 3 / 4  - buttonWidth / 2, topButtonY,
                buttonWidth, buttonHeight);
        browseCompaniesButton.setBounds(WIDTH * 3 / 4  - buttonWidth / 2, topButtonY + buttonHeight + 20,
                buttonWidth, buttonHeight);
        saveButton.setBounds(WIDTH * 3 / 4  - buttonWidth / 2, topButtonY + (buttonHeight + 20) * 2,
                buttonWidth, buttonHeight);
        exitButton.setBounds(WIDTH * 3 / 4  - buttonWidth / 2, topButtonY + (buttonHeight + 20) * 3,
                buttonWidth, buttonHeight);

        depositCashButton.addActionListener(e -> depositMenu());
        withdrawCashButton.addActionListener(e -> {
            //jf.dispose();
            withdrawMenu();
        });
        purchaseStocksButton.addActionListener(e -> {
            purchaseStocksMenu();
            //jf.dispose();
        });
        sellStocksButton.addActionListener(e -> {
            //jf.dispose();
            sellStocksMenu();
        });
        viewPortfolioButton.addActionListener(e -> JOptionPane.showMessageDialog(jf, portfolioHoldings()));
        browseCompaniesButton.addActionListener(e -> JOptionPane.showMessageDialog(jf, listedCompanies()));
        saveButton.addActionListener(e -> savePortfolio());
        exitButton.addActionListener(e -> jf.dispose());

        jf.add(title);
        jf.add(viewPortfolioButton);
        jf.add(depositCashButton);
        jf.add(withdrawCashButton);
        jf.add(purchaseStocksButton);
        jf.add(sellStocksButton);
        jf.add(viewPortfolioButton);
        jf.add(browseCompaniesButton);
        jf.add(saveButton);
        jf.add(exitButton);

        jf.setLayout(null);
        jf.setVisible(true);
    }

    //EFFECTS: creates the menu for depositing cash
    private void depositMenu() {

        int titleWidth = 300;
        int titleHeight = 100;
        int textFieldWidth = 200;
        int textFieldHeight = 50;

        this.jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Portfolio Manager Pro");
        title.setBounds(WIDTH / 2 - titleWidth / 2,10, titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        JTextField textField = new JTextField();
        textField.setBounds(WIDTH / 2 - titleWidth / 2,200, textFieldWidth, textFieldHeight);
        ActionListener textListener = e -> {
            String depositAmount = textField.getText();
            try {
                yourPortfolio.addToBalance(Double.parseDouble(depositAmount));
                JOptionPane.showMessageDialog(jf, "Deposit successful!");
                mainMenu(yourPortfolio);
            } catch (NegativeAmountException ex) {
                JOptionPane.showMessageDialog(jf,
                        "ERROR: Please enter a positive deposit amount");
            }
        };

        JButton continueButton = new JButton("Deposit");
        continueButton.setBounds(WIDTH / 2 - titleWidth / 2,250, textFieldWidth, textFieldHeight);
        continueButton.addActionListener(textListener);


        jf.add(title);
        jf.add(textField);
        jf.add(continueButton);


        jf.setLayout(null);
        jf.setVisible(true);

    }

    //EFFECTS: creates the menu for depositing cash
    private void withdrawMenu() {
        JOptionPane.showMessageDialog(jf, "Feature not yet implemented!");
    }

    private void purchaseStocksMenu() {

        int titleWidth = 300;
        int titleHeight = 100;
        int textFieldWidth = 200;
        int textFieldHeight = 50;

        this.jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Portfolio Manager Pro");
        title.setBounds(WIDTH / 2 - titleWidth / 2,10, titleWidth, titleHeight);
        title.setFont(new Font("Serif", Font.BOLD, 26));

        JLabel tickerLabel = new JLabel("Company name/ticker: ");
        tickerLabel.setBounds(WIDTH / 2 - titleWidth / 2 - textFieldWidth + 140,175, titleWidth, titleHeight);
        tickerLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField tickerInput = new JTextField();
        tickerInput.setToolTipText("Name/Ticker");
        tickerInput.setBounds(WIDTH / 2 - titleWidth / 2 + 100,200, textFieldWidth, textFieldHeight);

        JLabel shareNumberLabel = new JLabel("Shares to purchase: ");
        shareNumberLabel.setBounds(WIDTH / 2 - titleWidth / 2 - textFieldWidth + 140,225, titleWidth, titleHeight);
        shareNumberLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField shareNumberInput = new JTextField();
        tickerInput.setToolTipText("Share number");
        shareNumberInput.setBounds(WIDTH / 2 - titleWidth / 2 + 100,250, textFieldWidth, textFieldHeight);

        ActionListener purchaseListener = e -> {
            String ticker = tickerInput.getText();
            String shareNumber = shareNumberInput.getText();
            try {
                yourPortfolio.purchaseShares(ticker, Integer.parseInt(shareNumber));
                JOptionPane.showMessageDialog(jf,
                        "Purchase successful!");
                mainMenu(yourPortfolio);
            } catch (InsufficientFundsException ex) {
                JOptionPane.showMessageDialog(jf,
                        "ERROR: Insufficient funds!");
            } catch (CompanyNotFoundException ex) {
                JOptionPane.showMessageDialog(jf,
                        "ERROR: Please enter a valid name or ticker!");
            }
        };


        JButton continueButton = new JButton("Deposit");
        continueButton.setBounds(WIDTH / 2 - titleWidth / 2,300, textFieldWidth, textFieldHeight);
        continueButton.addActionListener(purchaseListener);


        jf.add(title);
        jf.add(tickerLabel);
        jf.add(tickerInput);
        jf.add(shareNumberLabel);
        jf.add(shareNumberInput);
        jf.add(continueButton);


        jf.setLayout(null);
        jf.setVisible(true);
    }

    private void sellStocksMenu() {
        JOptionPane.showMessageDialog(jf, "Feature not yet implemented!");
    }

    //EFFECTS: Returns a String containing yourPortfolio cashBalance and list of holdings
    public String portfolioHoldings() {
        String info = "Cash balance: " + yourPortfolio.getCashBalance();
        for (Company c : yourPortfolio.getStocks()) {
            info += "\n - Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Shares held: " + c.getSharesHeld()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMarketCap();
        }
        return info;
    }

    public String listedCompanies() {
        String info = "";
        for (ListedCompanies c : ListedCompanies.values()) {
            info += "\n Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMktcap();
        }
        return info;
    }

    private void savePortfolio() {
        try {
            writer.openWriter();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.write(yourPortfolio);
        writer.closeWriter();
        JOptionPane.showMessageDialog(jf, "Your portfolio has been saved!");
    }

}



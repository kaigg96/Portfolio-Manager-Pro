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
import java.util.ArrayList;
import java.util.Collections;

//Runs the GUIApp
public class GraphicalUIApp extends JFrame {

    private static final String PATH = "./data/portfolio.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    Portfolio yourPortfolio;
    JsonWriter writer = new JsonWriter(PATH);
    JsonReader reader = new JsonReader(PATH);
    JFrame jf;

    //EFFECTS: runs the openingMenu
    public GraphicalUIApp() {
        this.jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(new BorderLayout());
        jf.setVisible(true);
        jf.getContentPane().setBackground(new Color(163, 220, 239));
        openingMenu();
    }

    //EFFECTS: create a JLabel title object
    private JLabel createTitle() {

        JLabel title = new JLabel("Portfolio Manager Pro");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        return title;
    }

    //MODIFIES: this
    //EFFECTS: creates the opening menu for the GUIApp
    public void openingMenu() {

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(createOpeningPanel(), BorderLayout.CENTER);
        jf.pack();
    }

    //EFFECTS: sets up the openingMenu panel
    private JPanel createOpeningPanel() {

        JPanel openingPanel = new JPanel();
        openingPanel.setLayout(new FlowLayout());

        ArrayList<JButton> buttons = createOpeningButtons();

        openingPanel.add(Box.createVerticalStrut(450));
        for (JButton button : buttons) {
            openingPanel.add(button);
        }

        openingPanel.setBackground(new Color(163, 220, 239));

        return openingPanel;
    }

    //MODIFIES: this
    //EFFECTS: sets up the openingPanel buttons
    //         catches IOException and displays a popup of the error.
    private ArrayList<JButton> createOpeningButtons() {
        ArrayList<JButton> buttons = new ArrayList<>();

        JButton newPortfolioButton = new JButton("New Portfolio");
        JButton loadPortfolioButton = new JButton("Load Portfolio");

        newPortfolioButton.addActionListener(e -> {
            Portfolio portfolio = new Portfolio(0);
            mainMenu(portfolio);
        });

        loadPortfolioButton.addActionListener(e -> {
            Portfolio portfolio = new Portfolio(0);
            try {
                portfolio = reader.read();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(jf,
                        "ERROR: There are no saved portfolios. Loading a new portfolio instead.");
            }
            mainMenu(portfolio);
        });

        newPortfolioButton.setPreferredSize(new Dimension(200, 100));
        loadPortfolioButton.setPreferredSize(new Dimension(200, 100));

        Collections.addAll(buttons, newPortfolioButton, loadPortfolioButton);

        return buttons;
    }

    //MODIFIES: this
    //EFFECTS: sets up the mainMenu
    public void mainMenu(Portfolio p) {

        yourPortfolio = p;

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);

        jf.add(createMainPanel(), BorderLayout.CENTER);

        jf.pack();
    }

    //EFFECTS: sets up the mainPanel
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        //add buttons to mainPanel
        ArrayList<JButton> buttons = createMainButtons();
        for (JButton button : buttons) {
            mainPanel.add(button);
        }

        mainPanel.setBackground(new Color(163, 220, 239));

        return mainPanel;
    }

    //EFFECTS: creates the mainMenuButtons
    private ArrayList<JButton> createMainButtons() {

        ArrayList<JButton> buttons = new ArrayList<>();

        //create buttons
        JButton depositCashButton = new JButton("Deposit");
        JButton withdrawCashButton = new JButton("Withdraw");
        JButton purchaseStocksButton = new JButton("Purchase Stocks");
        JButton sellStocksButton = new JButton("Sell Stocks");
        JButton viewPortfolioButton = new JButton("View Portfolio");
        JButton browseCompaniesButton = new JButton("Browse Listed Companies");
        JButton saveButton = new JButton("Save Portfolio");
        JButton exitButton = new JButton("Exit");

        //add action listeners
        depositCashButton.addActionListener(e -> depositMenu());
        withdrawCashButton.addActionListener(e -> withdrawMenu());
        purchaseStocksButton.addActionListener(e -> purchaseStocksMenu());
        sellStocksButton.addActionListener(e -> sellStocksMenu());
        viewPortfolioButton.addActionListener(e -> browseHoldingsMenu());
        browseCompaniesButton.addActionListener(e -> browseCompaniesMenu());
        saveButton.addActionListener(e -> savePortfolio());
        exitButton.addActionListener(e -> jf.dispose());

        Collections.addAll(buttons, depositCashButton, withdrawCashButton, purchaseStocksButton, sellStocksButton,
                viewPortfolioButton, browseCompaniesButton, saveButton, exitButton);

        return buttons;
    }

    //EFFECTS: creates the menu for depositing cash
    private void depositMenu() {

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(createDepositPanel(), BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);
        jf.pack();
    }

    //MODIFIES: this
    //EFFECTS: creates the depositPanel
    //         catches NegativeAmountException and displays a popup of the error.
    private JPanel createDepositPanel() {

        JPanel depositPanel = new JPanel();
        depositPanel.setLayout(new FlowLayout());

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 50));
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
        continueButton.setPreferredSize(new Dimension(100, 50));
        continueButton.addActionListener(textListener);

        depositPanel.add(Box.createVerticalStrut(WIDTH / 2 + 50));
        depositPanel.add(textField);
        depositPanel.add(continueButton);

        depositPanel.setBackground(new Color(163, 220, 239));

        return depositPanel;
    }

    //EFFECTS: creates the menu for withdrawing cash
    private void withdrawMenu() {

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(createWithdrawalPanel(), BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);
        jf.pack();
    }

    //MODIFIES: this
    //EFFECTS: creates the depositPanel
    //         catches NegativeAmountException and displays a popup of the error.
    private JPanel createWithdrawalPanel() {

        JPanel depositPanel = new JPanel();
        depositPanel.setLayout(new FlowLayout());

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 50));
        ActionListener textListener = e -> tryWithdrawal(textField);

        JButton continueButton = new JButton("Withdraw");
        continueButton.setPreferredSize(new Dimension(100, 50));
        continueButton.addActionListener(textListener);

        depositPanel.add(Box.createVerticalStrut(WIDTH / 2 + 50));
        depositPanel.add(textField);
        depositPanel.add(continueButton);

        depositPanel.setBackground(new Color(163, 220, 239));

        return depositPanel;
    }

    private void tryWithdrawal(JTextField textField) {
        String withdrawalAmount = textField.getText();
        try {
            yourPortfolio.subFromBalance(Double.parseDouble(withdrawalAmount));
            JOptionPane.showMessageDialog(jf, "Withdrawal successful!");
            mainMenu(yourPortfolio);
        } catch (NegativeAmountException ex) {
            JOptionPane.showMessageDialog(jf,
                    "ERROR: Please enter a positive withdrawal amount");
        } catch (InsufficientFundsException ex) {
            JOptionPane.showMessageDialog(jf,
                    "ERROR: Insufficient funds");
        }
    }

    //MODIFIES: this
    //EFFECTS: creates the purchaseStocksMenu
    private void purchaseStocksMenu() {

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(purchasePanel(), BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);
        jf.pack();
    }

    //MODIFIES: this
    //EFFECTS: creates the purchaseStocksPanel
    private JPanel purchasePanel() {
        JPanel textAndLabelPanel = new JPanel();
        JButton continueButton = createContinueButton("Purchase shares");
        textAndLabelPanel.setLayout(new GroupLayout(textAndLabelPanel));

        JLabel tickerLabel = new JLabel("Company name/ticker: ");
        tickerLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField tickerInput = new JTextField();
        tickerInput.setPreferredSize(new Dimension(200, 50));

        JLabel shareNumberLabel = new JLabel("Number of shares: ");
        shareNumberLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField shareNumberInput = new JTextField();
        shareNumberInput.setPreferredSize(new Dimension(200, 50));

        addPurchaseListenerToContinueButton(continueButton, tickerInput, shareNumberInput);

        textAndLabelPanel.setBackground(new Color(163, 220, 239));

        setGroupLayout(textAndLabelPanel, continueButton, tickerLabel, tickerInput, shareNumberLabel, shareNumberInput);

        return textAndLabelPanel;
    }

    //MODIFIES: layout
    //EFFECTS: sets up the groupLayout object
    private void setGroupLayout(JPanel textAndLabelPanel, JButton continueButton, JLabel tickerLabel,
                                JTextField tickerInput, JLabel shareNumberLabel, JTextField shareNumberInput) {
        GroupLayout layout = new GroupLayout(textAndLabelPanel);
        textAndLabelPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        setHorizontalGroup(layout, tickerLabel, tickerInput, shareNumberLabel, shareNumberInput, continueButton);
        setVerticalGroup(layout, tickerLabel, tickerInput, shareNumberLabel, shareNumberInput, continueButton);
    }

    //MODIFIES: layout
    //EFFECTS: sets the HorizontalGroup of layout
    private void setHorizontalGroup(GroupLayout layout, JLabel tickerLabel, JTextField tickerInput,
                                    JLabel shareNumberLabel, JTextField shareNumberInput, JButton continueButton) {
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tickerLabel)
                                .addComponent(tickerInput))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(shareNumberLabel)
                                .addComponent(shareNumberInput))
                        .addComponent(continueButton)
        );
    }

    //MODIFIES: layout
    //EFFECTS: sets the VerticalGroup of layout
    private void setVerticalGroup(GroupLayout layout, JLabel tickerLabel, JTextField tickerInput,
                                  JLabel shareNumberLabel, JTextField shareNumberInput, JButton continueButton) {
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tickerLabel)
                                .addComponent(tickerInput))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(shareNumberLabel)
                                .addComponent(shareNumberInput))
                        .addComponent(continueButton)
        );
    }

    //EFFECTS: creates the continueButton
    private JButton createContinueButton(String text) {
        JButton continueButton = new JButton(text);
        continueButton.setPreferredSize(new Dimension(200, 50));
        return continueButton;
    }

    //EFFECTS: adds an ActionListener to the continueButton. When pressed, try to purchaseShares.
    //         catches InsufficientFundsException and CompanyNotFoundException, and displays a popup of the error.
    private void addPurchaseListenerToContinueButton(JButton continueButton, JTextField tickerInput,
                                                     JTextField shareNumberInput) {
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
        continueButton.addActionListener(purchaseListener);
    }

    //MODIFIES: this
    //EFFECTS: creates the purchaseStocksMenu
    private void sellStocksMenu() {

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(sellPanel(), BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);
        jf.pack();
    }

    private JPanel sellPanel() {
        JPanel textAndLabelPanel = new JPanel();
        JButton continueButton = createContinueButton("Sell shares");
        textAndLabelPanel.setLayout(new GroupLayout(textAndLabelPanel));

        JLabel tickerLabel = new JLabel("Company name/ticker: ");
        tickerLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField tickerInput = new JTextField();
        tickerInput.setPreferredSize(new Dimension(200, 50));

        JLabel shareNumberLabel = new JLabel("Number of  shares: ");
        shareNumberLabel.setFont(new Font("Serif", Font.BOLD, 16));
        JTextField shareNumberInput = new JTextField();
        shareNumberInput.setPreferredSize(new Dimension(200, 50));

        addSaleListenerToContinueButton(continueButton, tickerInput, shareNumberInput);

        textAndLabelPanel.setBackground(new Color(163, 220, 239));

        setGroupLayout(textAndLabelPanel, continueButton, tickerLabel, tickerInput, shareNumberLabel, shareNumberInput);

        return textAndLabelPanel;
    }

    private void addSaleListenerToContinueButton(JButton continueButton, JTextField tickerInput,
                                                 JTextField shareNumberInput) {
        ActionListener purchaseListener = e -> {
            String ticker = tickerInput.getText();
            String shareNumber = shareNumberInput.getText();
            if (yourPortfolio.findCompanyInStocks(ticker) != null) {
                try {
                    yourPortfolio.sellShares(ticker, Integer.parseInt(shareNumber));
                    JOptionPane.showMessageDialog(jf,
                            "Sale successful!");
                    mainMenu(yourPortfolio);
                } catch (NegativeAmountException ex) {
                    JOptionPane.showMessageDialog(jf,
                            "Please enter a valid number of shares");
                }
            } else {
                JOptionPane.showMessageDialog(jf,
                        "Company not found in list of current holdings");
            }
        };
        continueButton.addActionListener(purchaseListener);
    }

    //MODIFIES: this
    //EFFECTS: setup the browseHoldingsMenu
    private void browseHoldingsMenu() {

        JPanel holdingsPanel = new JPanel();
        holdingsPanel.setLayout(new GridLayout(20, 1));

        for (JPanel subPanel : generateHoldingsSubPanels()) {
            holdingsPanel.add(subPanel);
        }

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.add(createCashPanel(), BorderLayout.NORTH);
        displayPanel.add(holdingsPanel, BorderLayout.CENTER);
        displayPanel.setBackground(new Color(163, 220, 239));

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(displayPanel, BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);

        jf.pack();
    }

    //EFFECTS: creates and returns cashPanel
    private JPanel createCashPanel() {
        JPanel cashPanel = new JPanel();
        JLabel cashAmount = new JLabel("Cash Balance: " + yourPortfolio.getCashBalance());
        cashAmount.setFont(new Font("Serif", Font.BOLD, 16));

        cashPanel.add(cashAmount);

        return cashPanel;
    }

    //EFFECTS: creates subpanel for each Company with held shares
    private ArrayList<JPanel> generateHoldingsSubPanels() {
        ArrayList<String> tickerList = new ArrayList<>();
        for (Company c : yourPortfolio.getStocks()) {
            tickerList.add(c.getTicker());
        }

        ArrayList<JPanel> subPanels = new ArrayList<>();

        for (int i = 0; i < tickerList.size(); i++) {
            String ticker = tickerList.get(i);
            String imagePath = "./data/images/" + ticker + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(35, 25, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            JLabel logo = new JLabel(scaledIcon);

            JPanel subPanel = new JPanel();
            subPanel.add(logo);
            ArrayList<String> companyInfo = portfolioHoldings();
            subPanel.add(new JLabel(companyInfo.get(i)));

            subPanels.add(subPanel);
        }
        return subPanels;
    }

    private JPanel createBackButtonPanel() {

        JPanel backButtonPanel = new JPanel();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            jf.getContentPane().removeAll();
            mainMenu(yourPortfolio);
        });

        backButtonPanel.add(backButton);

        return backButtonPanel;
    }

    //EFFECTS: Returns a String containing yourPortfolio cashBalance and list of holdings
    public ArrayList<String> portfolioHoldings() {
        ArrayList<String> companyInfo = new ArrayList<>();
        for (Company c : yourPortfolio.getStocks()) {
            companyInfo.add("\n - Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Shares held: " + c.getSharesHeld()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMarketCap());
        }
        return companyInfo;
    }

    //MODIFIES: this
    //EFFECTS: setup the browseCompaniesMenu
    private void browseCompaniesMenu() {

        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(new Color(163, 220, 239));
        displayPanel.setLayout(new GridLayout(20, 1));

        for (JPanel subPanel : generateSubPanels()) {
            displayPanel.add(subPanel);
        }

        jf.getContentPane().removeAll();
        jf.add(createTitle(), BorderLayout.BEFORE_FIRST_LINE);
        jf.add(displayPanel, BorderLayout.CENTER);
        jf.add(createBackButtonPanel(), BorderLayout.SOUTH);

        jf.pack();
    }

    //EFFECTS: generate a subPanel for each company-logo pair
    private ArrayList<JPanel> generateSubPanels() {
        ArrayList<String> tickerList = new ArrayList<>();
        for (ListedCompanies c : ListedCompanies.values()) {
            tickerList.add(c.getTicker());
        }

        ArrayList<JPanel> subPanels = new ArrayList<>();

        for (int i = 0; i < tickerList.size(); i++) {
            String ticker = tickerList.get(i);
            String imagePath = "./data/images/" + ticker + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage().getScaledInstance(35, 25, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            JLabel logo = new JLabel(scaledIcon);

            JPanel subPanel = new JPanel();
            subPanel.add(logo);
            ArrayList<String> companyInfo = listedCompanies();
            subPanel.add(new JLabel(companyInfo.get(i)));

            subPanels.add(subPanel);
        }
        return subPanels;
    }

    //EFFECTS: creates a list of string objects of all ListedCompanies
    public ArrayList<String> listedCompanies() {
        ArrayList<String> info = new ArrayList<>();
        for (ListedCompanies c : ListedCompanies.values()) {
            info.add("\n Name: " + c.getName()
                    + " || Ticker: " + c.getTicker()
                    + " || Share price: " + c.getSharePrice()
                    + " || Market cap: " + c.getMktcap());
        }
        return info;
    }

    //MODIFIES: this
    //EFFECTS: saves portfolio JSON data in PATH
    //         catches FileNotFoundException and throws new RuntimeException
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



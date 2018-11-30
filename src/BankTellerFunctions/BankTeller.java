package BankTellerFunctions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.*;

import DebtsRus.Application;

public class BankTeller {
	
	Application app;
	JFrame frame;
	JButton checkTransaction;
	JButton generateMonthlyStatement;
	JButton closedAccounts;
	JButton DTER;
	JButton customerReport;
	JButton addInterest;
	JButton createAccount;
	JButton deleteClosedAccountsCustomers;
	JButton deleteTransactions;
	JButton backButtonToBankTeller;
	JButton backButton;
	JPanel panel;
	JPanel backPanel;
	static Connection conn;
	static Statement stmt;
	
	public BankTeller(Application appl, JPanel pan, JFrame frame){
		// launch a bankteller interface with all the options.
		app = appl;
		panel = pan;
		this.frame = frame;
		this.frame.setTitle("Welcome Bank Teller!");
		
		panel.removeAll();
		
		setUpInitialScreen();
		
		panel.updateUI();
	
		frame.getContentPane().add(BorderLayout.NORTH, backPanel);
		frame.getContentPane().add(BorderLayout.CENTER, panel); 
	
		frame.setVisible(true);
	}
	
	public void setUpInitialScreen() {
		panel.setLayout(new GridLayout(3,3));
	
		backPanel = new JPanel();
		
		// create all buttons needed
		backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonListener());
		backButtonToBankTeller = new JButton("Back");
		backButtonToBankTeller.addActionListener(new BackButtonToBankTellerListener());
		
		checkTransaction = new JButton("Check Transaction");
		generateMonthlyStatement = new JButton("Generate Monthly Statement");
		closedAccounts = new JButton("List Closed Accounts");
		DTER = new JButton("Generate DTER");
	   	customerReport = new JButton("Generate Customer Report");
	   	addInterest = new JButton("Add Interest");
	   	createAccount = new JButton("Create Account");
	   	deleteClosedAccountsCustomers = new JButton("Delete Closed Accounts/Customers");
	   	deleteTransactions = new JButton("Delete Transactions");
	   	
	   	// add action listeners for buttons
	   	checkTransaction.addActionListener(new CheckTransactionListener(panel, backPanel, backButtonToBankTeller));
	   	generateMonthlyStatement.addActionListener(new GenerateMonthlyStatementListener(panel, backPanel, backButtonToBankTeller));
	   	closedAccounts.addActionListener(new ClosedAccountsListener(panel, backPanel, backButtonToBankTeller));
	   	DTER.addActionListener(new DTERListener(panel, backPanel, backButtonToBankTeller));
	   	customerReport.addActionListener(new CustomerReportListener(panel, backPanel, backButtonToBankTeller));
	   	addInterest.addActionListener(new AddInterestListener(panel, backPanel, backButtonToBankTeller));
	   	createAccount.addActionListener(new CreateAccountListener(panel, backPanel, backButtonToBankTeller));
	   	deleteClosedAccountsCustomers.addActionListener(new DeleteClosedAccountsCustomersListener(panel, backPanel, backButtonToBankTeller));
	   	deleteTransactions.addActionListener(new DeleteTransactionsListener(panel, backPanel, backButtonToBankTeller));
	   	
	   	bankTellerScreen();
	}
	
	public void bankTellerScreen() {
	   panel.add(checkTransaction);
	   panel.add(generateMonthlyStatement);
	   panel.add(closedAccounts);
	   panel.add(DTER);
	   panel.add(customerReport);
	   panel.add(addInterest);
	   panel.add(createAccount);
	   panel.add(deleteClosedAccountsCustomers);
	   panel.add(deleteTransactions);
	   
	   backPanel.add(backButton);
	}

	void setUpBackPanelToBankTeller(){
		backPanel.removeAll();
		backPanel.add(backButtonToBankTeller);
		backPanel.updateUI();
	}
	
	private class BackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the first back button");
			
			panel.removeAll();
			backPanel.removeAll();
			app.updateUI();
			panel.updateUI();
		}
	}
	
	private class BackButtonToBankTellerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the nested back button :D");
						
			panel.removeAll();
			backPanel.removeAll();
			bankTellerScreen();
			panel.updateUI();
			backPanel.updateUI();
		}
	}
}
//import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BankTeller {
	
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
	
	BankTeller(){
		// launch a bankteller interface with all the options.
       frame = new JFrame("Welcome Bank Teller!");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(900,200);
       frame.setLocationRelativeTo(null);
       
       setUpInitialScreen();

	   frame.getContentPane().add(BorderLayout.NORTH, backPanel);
       frame.getContentPane().add(BorderLayout.CENTER, panel); 

       frame.setVisible(true);
	}
	
	public void setUpInitialScreen() {
		
	   panel = new JPanel(new GridLayout(3,3));
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
	   checkTransaction.addActionListener(new TransactionListener(panel, backButtonToBankTeller));
	   generateMonthlyStatement.addActionListener(new MonthlyStatement());
	   closedAccounts.addActionListener(new AccountsClosed());
	   DTER.addActionListener(new GTDReport());
	   customerReport.addActionListener(new CustomerReport());
	   addInterest.addActionListener(new InterestAction());
	   createAccount.addActionListener(new AccountCreation());
	   deleteClosedAccountsCustomers.addActionListener(new DeletingAccountsCustomers());
	   deleteTransactions.addActionListener(new DeletingTransactions());

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
	
	public static void main(String[] args) {
		BankTeller a = new BankTeller();
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
	
	private class MonthlyStatement implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("monthly statements clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class AccountsClosed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("close accounts clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class GTDReport implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("GTDR clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class CustomerReport implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("customer report clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class InterestAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("add interest clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class AccountCreation implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("create accounts clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class DeletingAccountsCustomers implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("deleting accounts customers clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
	private class DeletingTransactions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("deleting transactions clicked");
			
			setUpBackPanelToBankTeller();
			
			panel.removeAll();
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			panel.updateUI();
		}
	}
}
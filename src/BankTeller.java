//import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BankTeller {
	
	JButton checkTransaction;
	JButton generateMonthlyStatement;
	JButton closedAccounts;
	JButton DTER;
	JButton customerReport;
	JButton addInterest;
	JButton createAccount;
	JButton deleteClosedAccountsCustomers;
	JButton deleteTransactions;
	JPanel panel;
	
	BankTeller(){
		// launch a bankteller interface with all the options.
       JFrame frame = new JFrame("Welcome Bank Teller!");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(900,300);
       frame.setLocationRelativeTo(null);
       
       // create all buttons needed
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
	   checkTransaction.addActionListener(new Transaction());
	   generateMonthlyStatement.addActionListener(new MonthlyStatement());
	   closedAccounts.addActionListener(new AccountsClosed());
	   DTER.addActionListener(new GTDReport());
	   customerReport.addActionListener(new CustomerReport());
	   addInterest.addActionListener(new InterestAction());
	   createAccount.addActionListener(new AccountCreation());
	   deleteClosedAccountsCustomers.addActionListener(new DeletingAccountsCustomers());
	   deleteTransactions.addActionListener(new DeletingTransactions());
	   
	   // add buttons to grid
	   panel = new JPanel(new GridLayout(3,3));
	   panel.add(checkTransaction);
	   panel.add(generateMonthlyStatement);
	   panel.add(closedAccounts);
	   panel.add(DTER);
	   panel.add(customerReport);
	   panel.add(addInterest);
	   panel.add(createAccount);
	   panel.add(deleteClosedAccountsCustomers);
	   panel.add(deleteTransactions);
	  
       frame.getContentPane().add(panel); // Adds Button to content pane of frame
       frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		BankTeller a = new BankTeller();
	}
	
	private class Transaction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("transaction clicked");
			panel.removeAll();
			
			JButton trans = new JButton("YAY MADE IT");
			panel.add(trans);
			
			panel.updateUI();
		}
	}
	private class MonthlyStatement implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("monthly statements clicked");
		}
	}
	private class AccountsClosed implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("close accounts clicked");
		}
	}
	private class GTDReport implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("GTDR clicked");
		}
	}
	private class CustomerReport implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("customer report clicked");
		}
	}
	private class InterestAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("add interest clicked");
		}
	}
	private class AccountCreation implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("create accounts clicked");
		}
	}
	private class DeletingAccountsCustomers implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("deleting accounts customers clicked");
		}
	}
	private class DeletingTransactions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("deleting transactions clicked");
		}
	}
}
package BankTellerFunctions;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import DebtsRus.Application;

public class CheckTransactionListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	JTextField accountNumber;
	JTextField checkAmount;
	JTextField customerSSN;
	
	int accountId;
	double amountCheck;
	int ssn;
	
	CheckTransactionListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("transaction clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		panel.setLayout(new GridLayout(4,2));
		
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JTextField(20);
		
		JLabel customerId = new JLabel("Enter Customer SSN: ");
		customerSSN = new JTextField(20);
		
		JLabel checkAmountLabel = new JLabel("Enter Amount for Check: ");
		checkAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(customerId);
		panel.add(customerSSN);
		panel.add(checkAmountLabel);
		panel.add(checkAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Enter was clicked.");
			
			try {
				accountId = Integer.parseInt(accountNumber.getText());
				amountCheck = Double.parseDouble(checkAmount.getText());
				ssn = Integer.parseInt(customerSSN.getText());
				
				if(amountCheck <= 0) {
					BankTellerUtility.showPopUpMessage("Amount to withdraw must be a positive number. Please try again.");
				} else {
					updateBalance();
				}
				
			} catch(SQLException e) {
				BankTellerUtility.showPopUpMessage(e.toString());
			} catch(Exception e) {
				BankTellerUtility.showPopUpMessage("Invalid inputs were entered. AccountId and AmountCheck must be positive numbers"
						+ ". Please try again.");
			}
		}
		
		public void updateBalance() throws SQLException {	
			if(existsAccount()) {
				if(notClosed()) {
					subtractBalanceAmount();
				} else {
					BankTellerUtility.showPopUpMessage("That account has been closed. Please try again.");
				}
			} else {
				BankTellerUtility.showPopUpMessage("No account with that id exists. Please try again.");
			}
		}
		
		public boolean notClosed() throws SQLException {
			String query = "SELECT isClosed FROM CR_ACCOUNTS WHERE AccountId = " + accountId;
			
			int closed = 0;
			ResultSet res = Application.stmt.executeQuery(query);
			while(res.next()) {
				closed = res.getInt("accountId");
			}
			if(closed == 1) return false;
			return true;
		}
		
		public boolean existsAccount() throws SQLException {
			String accountExists = "SELECT * FROM CR_ACCOUNTS WHERE accountId =" + accountId;
			
			ResultSet exists = Application.stmt.executeQuery(accountExists);
			return exists.next();
		}
				
		
		public void subtractBalanceAmount() {
			String balanceQuery = "SELECT balance FROM CR_ACCOUNTS WHERE accountId =" + accountId;
			double balance = -1;
			try {
				ResultSet set = Application.stmt.executeQuery(balanceQuery);
				if(set.next()) balance = set.getInt(1);
				if(balance < amountCheck) {					
					BankTellerUtility.showPopUpMessage("You do not have enough money in your account: " + accountId + 
							" to withdraw $" + amountCheck);

					accountNumber.removeAll();
					checkAmount.removeAll();
					panel.updateUI();
					
				} else {
					String query = "UPDATE CR_ACCOUNTS SET balance = balance - " + amountCheck;
					int id = Application.stmt.executeUpdate(query);
					
					int checkNumber = generateCheckNumber();
					assert(id == 1);
					BankTellerUtility.showPopUpMessage("You have written a check for $" + amountCheck + ". Your check"
							+ " number is: " + checkNumber);
					
					if(balance - amountCheck <= 0.01) {
						String closeAccount = "UPDATE CR_ACCOUNTS SET ISCLOSED = 1 WHERE ACCOUNTID = " + accountId;
						int numRowsUpdated = Application.stmt.executeUpdate(closeAccount);
						assert(numRowsUpdated == 1);
						
						BankTellerUtility.showPopUpMessage("Since your account: " + accountId + " balance was less than or "
								+ "equal to $0.01, your account was closed.");
					}
					
					int transactionId = BankTellerUtility.getNumberTransactions() + 1;
					String type = "'Write check'";
					String check = "checkNumber: " + checkNumber;
					String transactionsQuery = "INSERT INTO CR_TRANSACTIONS "
							+ "VALUES(" + transactionId + ", " + type + ", " + ssn + ", " + accountId + ", null, " + amountCheck + ", "
									+ "'" + check + "', to_date('" + Application.getDate() + "', 'mm-dd-yyyy'))";
					System.out.println(transactionsQuery);
					int numRowsUpdated = Application.stmt.executeUpdate(transactionsQuery);
					assert(numRowsUpdated == 1);
					
					accountNumber.removeAll();
					checkAmount.removeAll();
					panel.updateUI();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int generateCheckNumber() {
		
		return (int) (Math.random() * 250.234);
	}
}
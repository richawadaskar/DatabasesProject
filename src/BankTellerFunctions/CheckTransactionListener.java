package BankTellerFunctions;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class CheckTransactionListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	//JLabel accountNumberLabel;
	JTextField accountNumber;
	JTextField checkAmount;
	
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
		
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JTextField(20);
		
		JLabel checkAmountLabel = new JLabel("Enter Amount for Check: ");
		checkAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(checkAmountLabel);
		panel.add(checkAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			System.out.println("Enter was clicked.");
			
			int accountId = Integer.parseInt(accountNumber.getText());
			int amountCheck = Integer.parseInt(checkAmount.getText());
		
			System.out.println("Account number entered was: " + accountId);
			System.out.println("Amount for check is: " + amountCheck);

			System.out.println("Hi.");
			
			// check if account exists and that its type has ability to write check from. 
			String accountExists = "SELECT COUNT(accountId) FROM RICHA_WADASKAR_CUSTOMERS WHERE accountId =" + accountId;
			// ResultSet exists = Main.stmt.executeQuery(accountExists);
			// if(exists != 1) {
			// 	 error bitches...  exit function.
			// }
			
			// check if account has enough money. 
			String balanceQuery = "SELECT balance FROM RICHA_WADASKAR_CUSTOMERS WHERE accountId =" + accountId; 
			
			System.out.println("Hii.");

			double balance = 0;
			try {
				ResultSet set = Application.stmt.executeQuery(balanceQuery);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Hii.");
			
			// Update account
			if(balance < amountCheck) {
				
				System.out.println("Yikes balance is low.");
			
			} else {
				
				String query = "UPDATE RICHA_WADASKAR_CUSTOMERS SET balance = balance - " + balance;
				// ResultSet accountId = MAIN.stmt.executeQuery(query);

			}
		}
		
	}
	
}
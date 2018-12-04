package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DebtsRus.*;

public class DepositListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField accountNumber;
	JTextField depositAmount;
	
	DepositListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Deposit clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JTextField(20);
		
		JLabel depositAmountLabel = new JLabel("Enter Amount for Deposit: ");
		depositAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(3,3));
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(depositAmountLabel);
		panel.add(depositAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int accountId = Integer.parseInt(accountNumber.getText());
			float amountDeposit = Float.parseFloat(depositAmount.getText());
			
			// check if account exists and that its type is either checking or saving
			String accountExists = "SELECT accountId, accountType FROM CR_ACCOUNTS WHERE accountId =" + accountId;
			try {
				ResultSet tables1 = Application.stmt.executeQuery(accountExists);
		    	if(tables1.next()){
		    		String accountType = tables1.getString("accountType");
		    		
		    		//if account type is NOT pocket
		    		if(!accountType.toLowerCase().equals("pocket")) {
		    		
			    		String getBalance = "SELECT balance FROM CR_ACCOUNTS WHERE accountId =" + accountId;
			    		ResultSet balanceTable = Application.stmt.executeQuery(getBalance);
			    		while(balanceTable.next() ) {
				    		Float balance = balanceTable.getFloat("balance");
				    		System.out.println("Initial Money:" + balance);
				    		
				    		ATMOptionUtility.addMoneyToAccountId(accountId, amountDeposit);
				    		ATMOptionUtility.addToTransactionsTable("Deposit", ssn, accountId, amountDeposit);
				    		JOptionPane.showMessageDialog(frame, "Deposit succeeded.");
			    		}
		    		} else {
		    			JOptionPane.showMessageDialog(frame, "Tis is a pocket account. Cannot deposit.");
		    		}
		    		
		    	} else {
		    		System.out.println("Account ID does not exist.");
		    		JOptionPane.showMessageDialog(frame, "Account ID does not exist.");
		    		//panel.add(wrongPIN);
		    		panel.updateUI();
		    	}
		    	String getBalance = "SELECT balance FROM CR_ACCOUNTS WHERE accountId =" + accountId;
	    		ResultSet balanceTable = Application.stmt.executeQuery(getBalance);
	    		while(balanceTable.next() ) {
		    		Float balance = balanceTable.getFloat("balance");
		    		System.out.println("After Money:" + balance);
	    		}
		    	
				} catch (SQLException error) {
					error.printStackTrace();
			}

		}
		
	}

}

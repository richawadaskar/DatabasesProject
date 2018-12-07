package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import BankTellerFunctions.BankTellerUtility;
import DebtsRus.Application;


public class PurchaseListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;

	JComboBox accountNumber;
	JTextField purchaseAmount;
	
	PurchaseListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame  = incomingFrame;
		ssn = customerId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Deposit clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel accountNumberLabel = new JLabel("Enter Pocket Account Number: ");
		accountNumber = new JComboBox(ATMOptionUtility.findAllPocketAccountNumbers(ssn).toArray());
		
		JLabel purchaseAmountLabel = new JLabel("Enter Amount for Purchase: ");
		purchaseAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(3,3));
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(purchaseAmountLabel);
		panel.add(purchaseAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int accountId = Integer.parseInt(accountNumber.getSelectedItem().toString());
			float amountPurchase = Float.parseFloat(purchaseAmount.getText());
			Float balance = ATMOptionUtility.getBalanceFromAccountId(accountId);

			// check if account exists and that its type a pocket
			try {
				if(ATMOptionUtility.checkEnoughBalance(accountId, amountPurchase)) {
					ATMOptionUtility.subtractMoneyToAccountId(accountId, amountPurchase);
					ATMOptionUtility.addToTransactionsTable("Purchase", ssn, accountId, amountPurchase);
					JOptionPane.showMessageDialog(frame, "Purchase succeeded.");
					if(balance - amountPurchase <= 0.01) {
						String closeAccount = "UPDATE CR_ACCOUNTS SET ISCLOSED = 1 WHERE ACCOUNTID = " + accountId;
						int numRowsUpdated = Application.stmt.executeUpdate(closeAccount);
						assert(numRowsUpdated == 1);
						
						BankTellerUtility.showPopUpMessage("Since your account: " + accountId + " balance was less than or "
								+ "equal to $0.01, your account was closed.");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}

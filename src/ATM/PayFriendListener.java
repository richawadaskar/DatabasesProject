package ATM;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import BankTellerFunctions.BankTellerUtility;
import DebtsRus.Application;

public class PayFriendListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;

	JComboBox fromAccount;
	JComboBox toAccount;
	JTextField payFriendAmount;
	
	PayFriendListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton,  JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Transfer clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel fromAccountNumberLabel = new JLabel("Enter From Pocket Account Number: ");
		fromAccount = new JComboBox(ATMOptionUtility.findAllPocketAccountNumbers(ssn).toArray());
		
		JLabel toAccountNumberLabel = new JLabel("Enter To Pocket Account Number: ");
		toAccount = new JComboBox(ATMOptionUtility.findAllPocketAccountNumbers().toArray());
		
		JLabel payFriendAmountLabel = new JLabel("Enter Amount to Pay Friend: ");
		payFriendAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(fromAccountNumberLabel);
		panel.add(fromAccount);
		panel.add(toAccountNumberLabel);
		panel.add(toAccount);
		panel.add(payFriendAmountLabel);
		panel.add(payFriendAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int fromAccountId = Integer.parseInt(fromAccount.getSelectedItem().toString());
			int toAccountId = Integer.parseInt(toAccount.getSelectedItem().toString());
			float amountPayFriend = Float.parseFloat(payFriendAmount.getText());
			Float balance = ATMOptionUtility.getBalanceFromAccountId(fromAccountId);

			try {
				if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountPayFriend)) {
					ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, amountPayFriend);
					ATMOptionUtility.addMoneyToAccountId(toAccountId, amountPayFriend);
					ATMOptionUtility.addToTransactionsTable("Pay-Friend", ssn, fromAccountId, toAccountId, amountPayFriend);
					JOptionPane.showMessageDialog(frame, "Pay-Friend succeeded.");
					if(balance - amountPayFriend <= 0.01) {
						String closeAccount = "UPDATE CR_ACCOUNTS SET ISCLOSED = 1 WHERE ACCOUNTID = " + fromAccountId;
						int numRowsUpdated = Application.stmt.executeUpdate(closeAccount);
						assert(numRowsUpdated == 1);
						
						BankTellerUtility.showPopUpMessage("Since your account: " + fromAccountId + " balance was less than or "
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

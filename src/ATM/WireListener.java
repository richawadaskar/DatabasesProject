package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import BankTellerFunctions.BankTellerUtility;
import DebtsRus.Application;

public class WireListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;

	JComboBox fromAccount;
	JComboBox toAccount;
	JTextField wireAmount;
	
	WireListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Wire clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		panel.removeAll();
		
		JLabel fromAccountIdLabel = new JLabel("Enter from account ID");
		fromAccount = new JComboBox(ATMOptionUtility.findAllCheckingSavingAccountNumbers(ssn).toArray());
		
		JLabel toAccountIdLabel = new JLabel("Enter to account ID");
		toAccount = new JComboBox(ATMOptionUtility.findAllCheckingSavingAccountNumbers().toArray());
		JLabel wireAmountLabel = new JLabel("Enter Amount to Wire: ");
		wireAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());

		panel.setLayout(new GridLayout(4,3));

		panel.add(fromAccountIdLabel);
		panel.add(fromAccount);
		panel.add(toAccountIdLabel);
		panel.add(toAccount);
		panel.add(wireAmountLabel);
		panel.add(wireAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int fromAccountId = Integer.parseInt(fromAccount.getSelectedItem().toString());
			int toAccountId = Integer.parseInt(toAccount.getSelectedItem().toString());
			float amountWire = Float.parseFloat(wireAmount.getText());
			Float balance = ATMOptionUtility.getBalanceFromAccountId(fromAccountId);

			try {
				if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountWire)) {
					ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, (float)(amountWire*1.02));
					ATMOptionUtility.addMoneyToAccountId(toAccountId, amountWire);
					ATMOptionUtility.addToTransactionsTable("Wire", ssn, fromAccountId, toAccountId, amountWire);
					JOptionPane.showMessageDialog(frame, "Wire succeeded.");
					if(balance - amountWire <= 0.01) {
						String closeAccount = "UPDATE CR_ACCOUNTS SET ISCLOSED = 1 WHERE ACCOUNTID = " + fromAccountId;
						int numRowsUpdated = Application.stmt.executeUpdate(closeAccount);
						assert(numRowsUpdated == 1);
						
						BankTellerUtility.showPopUpMessage("Since your account: " + fromAccountId + " balance was less than or "
								+ "equal to $0.01, your account was closed.");
					}
				} else
					JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}

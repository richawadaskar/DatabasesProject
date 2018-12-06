package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import DebtsRus.Application;

public class TopUpListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;

	JComboBox topUpAccountNumber;
	JLabel fromAccount;
	JTextField topUpAmount;
	
	TopUpListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Top-Up clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel topUpAccountNumberLabel = new JLabel("Pick a Pocket Account: ");
		topUpAccountNumber = new JComboBox(ATMOptionUtility.findAllPocketAccountNumbers(ssn).toArray());

		JLabel fromAccountNumberLabel = new JLabel("To-Up money From Linked Account : ");
		fromAccount = new JLabel();
		
		JLabel topUpAmountLabel = new JLabel("Enter Amount to Top Up: ");
		topUpAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(topUpAccountNumberLabel);
		panel.add(topUpAccountNumber);
		panel.add(fromAccountNumberLabel);
		panel.add(fromAccount);
		panel.add(topUpAmountLabel);
		panel.add(topUpAmount);
		panel.add(enter);
		
		panel.updateUI();

		if(ATMOptionUtility.findAllPocketAccountNumbers(ssn).size() == 0)  {
			JOptionPane.showMessageDialog(frame, "You don't have a pocket account.");
		}
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int topUpAccountId = Integer.parseInt(topUpAccountNumber.getSelectedItem().toString());
			int fromAccountId = ATMOptionUtility.getLinkedAccount(topUpAccountId);
			fromAccount.setText(Integer.toString(fromAccountId));
			float amountTopUp = Float.parseFloat(topUpAmount.getText());

			try {
				// check if pocket account owner also owns parent account
				if (ATMOptionUtility.checkEnoughBalance(fromAccountId, amountTopUp)) {
					ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, amountTopUp);
					ATMOptionUtility.addMoneyToAccountId(topUpAccountId, amountTopUp);
					ATMOptionUtility.addToTransactionsTable("Top-up", ssn, topUpAccountId, fromAccountId, amountTopUp);
					JOptionPane.showMessageDialog(frame, "Top-up succeeded.");
				} else {
					JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
				}
			}catch(SQLException ee) {
				ee.printStackTrace();
			}


			
		}
	}

	

}

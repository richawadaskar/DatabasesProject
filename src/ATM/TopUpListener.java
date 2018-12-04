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

import DebtsRus.Application;

public class TopUpListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField topUpAccount;
	JTextField fromAccount;
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
		
		JLabel topUpAccountNumberLabel = new JLabel("Enter Pocket Account Number: ");
		topUpAccount = new JTextField(20);
		
		JLabel fromAccountNumberLabel = new JLabel("Enter To From Account Number: ");
		fromAccount = new JTextField(20);
		
		JLabel topUpAmountLabel = new JLabel("Enter Amount to Top Up: ");
		topUpAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(topUpAccountNumberLabel);
		panel.add(topUpAccount);
		panel.add(fromAccountNumberLabel);
		panel.add(fromAccount);
		panel.add(topUpAmountLabel);
		panel.add(topUpAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int topUpAccountId = Integer.parseInt(topUpAccount.getText());
			int fromAccountId = Integer.parseInt(fromAccount.getText());
			float amountTopUp = Float.parseFloat(topUpAmount.getText());
			
			//check if pocketAccount is pocket 
			// check if pocket account owner also owns parent account
			if(ATMOptionUtility.checkIfAccountIsPocket(topUpAccountId)) { 
				String pocketAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + topUpAccountId + " AND ssn = " + ssn;
				String fromAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + fromAccountId + " AND ssn = " + ssn;
	
				try {
					ResultSet paccount = Application.stmt.executeQuery(pocketAccount);
					if(paccount.next()) {
						System.out.println("You own paccount");
					} else {
						JOptionPane.showMessageDialog(frame, "You don't own this account.");
					}
					ResultSet faccount = Application.stmt.executeQuery(fromAccount);
					if(faccount.next()) {
						System.out.println("You own fAccount");
						if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountTopUp)) {
							ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, amountTopUp);
							ATMOptionUtility.addMoneyToAccountId(topUpAccountId, amountTopUp);
							ATMOptionUtility.addToTransactionsTable("Top-up", ssn, topUpAccountId, fromAccountId, amountTopUp);
				    		JOptionPane.showMessageDialog(frame, "Top-up succeeded.");
						} else {
							JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "You don't own this account for money transfer.(to)");
					}
	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else 
				JOptionPane.showMessageDialog(frame, "This account isn't a pocket");
			
		}
	}

	

}

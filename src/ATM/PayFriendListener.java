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

public class PayFriendListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField fromAccount;
	JTextField toAccount;
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
		fromAccount = new JTextField(20);
		
		JLabel toAccountNumberLabel = new JLabel("Enter To Pocket Account Number: ");
		toAccount = new JTextField(20);
		
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
			int fromAccountId = Integer.parseInt(fromAccount.getText());
			int toAccountId = Integer.parseInt(toAccount.getText());
			float amountPayFriend = Float.parseFloat(payFriendAmount.getText());
			
			//check if fromAccount  owned by ssn
			if(ATMOptionUtility.checkIfAccountIsPocket(fromAccountId) && ATMOptionUtility.checkIfAccountIsPocket(toAccountId)) {
				String findAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + fromAccountId + " AND ssn = " + ssn;
				try {
					ResultSet paccount = Application.stmt.executeQuery(findAccount);
					if(paccount.next()) {
						System.out.println("You own paccount");
						if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountPayFriend)) {
							ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, amountPayFriend);
							ATMOptionUtility.addMoneyToAccountId(toAccountId, amountPayFriend);
							ATMOptionUtility.addToTransactionsTable("Pay-Friend", ssn, fromAccountId, toAccountId, amountPayFriend);
				    		JOptionPane.showMessageDialog(frame, "Pay-Friend succeeded.");
						} else {
							JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
						}
					} else {
						JOptionPane.showMessageDialog(frame, "You don't own this account.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else {
				JOptionPane.showMessageDialog(frame, "One or both of these accounts aren't a pocket");
			}
		}

	}

}

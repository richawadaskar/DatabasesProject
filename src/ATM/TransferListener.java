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

public class TransferListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField fromAccount;
	JTextField toAccount;
	JTextField transferAmount;
	
	TransferListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
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
		
		JLabel fromAccountNumberLabel = new JLabel("Enter From Account Number: ");
		fromAccount = new JTextField(20);
		
		JLabel toAccountNumberLabel = new JLabel("Enter To Account Number: ");
		toAccount = new JTextField(20);
		
		JLabel transferAmountLabel = new JLabel("Enter Amount to Transfer: ");
		transferAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(fromAccountNumberLabel);
		panel.add(fromAccount);
		panel.add(toAccountNumberLabel);
		panel.add(toAccount);
		panel.add(transferAmountLabel);
		panel.add(transferAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int fromAccountId = Integer.parseInt(fromAccount.getText());
			int toAccountId = Integer.parseInt(toAccount.getText());
			float amountTransfer = Float.parseFloat(transferAmount.getText());
			
			//check if fromAccount  owned by ssn
			String findAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + fromAccountId + " AND ssn = " + ssn;
			String toAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + toAccountId + " AND ssn = " + ssn;

			try {
				ResultSet faccount = Application.stmt.executeQuery(findAccount);
				if(faccount.next()) {
					System.out.println("You own fromAccount");
				} else {
					JOptionPane.showMessageDialog(frame, "You don't own this account for money transfer.(from)");
				}
				ResultSet taccount = Application.stmt.executeQuery(toAccount);
				if(taccount.next()) {
					System.out.println("You own toAccount");
					if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountTransfer)) {
						ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, amountTransfer);
						ATMOptionUtility.addMoneyToAccountId(toAccountId, amountTransfer);
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
			
			//check if fromAccount and toAccount have at least one owner in common
			
			//if yes, proceed with transaction
		}
		
		
	}
}

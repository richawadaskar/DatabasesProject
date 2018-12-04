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


public class PurchaseListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField accountNumber;
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
		accountNumber = new JTextField(20);
		
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
			int accountId = Integer.parseInt(accountNumber.getText());
			float amountPurchase = Float.parseFloat(purchaseAmount.getText());
			
			// check if account exists and that its type a pocket
			if(ATMOptionUtility.checkIfAccountIsPocket(accountId)) {
				String pocketAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + accountId + " AND ssn = " + ssn;
				try {
					ResultSet paccount = Application.stmt.executeQuery(pocketAccount);
					if(paccount.next()) {
						System.out.println("You own paccount");
						if(ATMOptionUtility.checkEnoughBalance(accountId, amountPurchase)) {
		    				ATMOptionUtility.subtractMoneyToAccountId(accountId, amountPurchase);
		    				ATMOptionUtility.addToTransactionsTable("Purchase", ssn, accountId, amountPurchase);
				    		JOptionPane.showMessageDialog(frame, "Purchase succeeded.");
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
				JOptionPane.showMessageDialog(frame, "This account isn't a pocket");
			}
		}
		
	}
}

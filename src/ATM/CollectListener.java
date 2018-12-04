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


public class CollectListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField fromPocketAccount;
	JTextField toParentAccount;
	JTextField collectAmount;
	
	CollectListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Collect clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel fromPocketAccountNumberLabel = new JLabel("Enter From Pocket Account Number: ");
		fromPocketAccount = new JTextField(20);
		
		JLabel toParentAccountNumberLabel = new JLabel("Enter To Parent Account Number: ");
		toParentAccount = new JTextField(20);
		
		JLabel collectAmountLabel = new JLabel("Enter Amount to Collect: ");
		collectAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(fromPocketAccountNumberLabel);
		panel.add(fromPocketAccount);
		panel.add(toParentAccountNumberLabel);
		panel.add(toParentAccount);
		panel.add(collectAmountLabel);
		panel.add(collectAmount);
		panel.add(enter);
		
		panel.updateUI();
		
	}
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int pocketAccountId = Integer.parseInt(fromPocketAccount.getText());
			int parentAccountId = Integer.parseInt(toParentAccount.getText());
			float amountCollect = Float.parseFloat(collectAmount.getText());
			
			// check if account exists and that its type a pocket
			if(ATMOptionUtility.checkIfAccountIsPocket(pocketAccountId)) {
				String pocketAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + pocketAccountId + " AND ssn = " + ssn;
				String fromAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + parentAccountId + " AND ssn = " + ssn;
				try {
					ResultSet paccount = Application.stmt.executeQuery(pocketAccount);
					if(paccount.next()) {
						System.out.println("You own pocket account");
					} else {
						JOptionPane.showMessageDialog(frame, "You don't own this account.");
					}
					ResultSet faccount = Application.stmt.executeQuery(fromAccount);
					if(faccount.next()) {
						System.out.println("You own parent Account");
						if(ATMOptionUtility.checkEnoughBalance(pocketAccountId, amountCollect)) {
							ATMOptionUtility.subtractMoneyToAccountId(pocketAccountId, amountCollect);
							ATMOptionUtility.addMoneyToAccountId(parentAccountId, (float)(amountCollect*0.97));
							ATMOptionUtility.addToTransactionsTable("Top-up", ssn, pocketAccountId, parentAccountId, amountCollect);
				    		JOptionPane.showMessageDialog(frame, "Collect succeeded.");
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
			} else {
				JOptionPane.showMessageDialog(frame, "This account isn't a pocket");
			}
		}
		
	}

}

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

public class WireListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JTextField fromAccount;
	JTextField toAccount;
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
		fromAccount = new JTextField(20);		
		
		JLabel toAccountIdLabel = new JLabel("Enter to account ID");
		toAccount = new JTextField(20);
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
			int fromAccountId = Integer.parseInt(fromAccount.getText());
			int toAccountId = Integer.parseInt(toAccount.getText());
			float amountWire = Float.parseFloat(wireAmount.getText());
			
			//check if fromAccount  owned by ssn
			String findAccount = "SELECT accountId FROM CR_ACCOUNTSOWNEDBY WHERE accountId = " + fromAccountId + " AND ssn = " + ssn;

			if(!ATMOptionUtility.checkIfAccountIsPocket(fromAccountId) && !ATMOptionUtility.checkIfAccountIsPocket(toAccountId)) {
				try {
					ResultSet faccount = Application.stmt.executeQuery(findAccount);
					if(faccount.next()) {
						System.out.println("You own fromAccount");
						if(ATMOptionUtility.checkEnoughBalance(fromAccountId, amountWire)) {
							ATMOptionUtility.subtractMoneyToAccountId(fromAccountId, (float)(amountWire*1.02));
							ATMOptionUtility.addMoneyToAccountId(toAccountId, amountWire);
							ATMOptionUtility.addToTransactionsTable("Wire", ssn, fromAccountId, toAccountId, amountWire);
				    		JOptionPane.showMessageDialog(frame, "Wire succeeded.");
						} else 
							JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
					} else 
						JOptionPane.showMessageDialog(frame, "You don't own this account for money wire.(from)");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else 
				JOptionPane.showMessageDialog(frame, "Account entered is a pocket. Can't perform transfer.");
		
		}
		
	}

}

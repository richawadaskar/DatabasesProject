package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import DebtsRus.*;

public class DepositListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JComboBox accountNumber;
	JTextField depositAmount;

	DepositListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Deposit clicked");
		System.out.println(Application.getDate());


		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JComboBox(ATMOptionUtility.findAllCheckingSavingAccountNumbers(ssn).toArray());
		
		JLabel depositAmountLabel = new JLabel("Enter Amount for Deposit: ");
		depositAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(3,3));
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(depositAmountLabel);
		panel.add(depositAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int accountId = Integer.parseInt(accountNumber.getSelectedItem().toString());
			float amountDeposit = Float.parseFloat(depositAmount.getText());
			
			// check if account exists and that its type is either checking or saving
			String getBalance = "SELECT balance FROM CR_ACCOUNTS WHERE accountId =" + accountId;
			try {
				ResultSet balanceTable = Application.stmt.executeQuery(getBalance);
				while (balanceTable.next()) {
					Float balance = balanceTable.getFloat("balance");
					System.out.println("Initial Money:" + balance);
					ATMOptionUtility.addMoneyToAccountId(accountId, amountDeposit);
					ATMOptionUtility.addToTransactionsTable("Deposit", ssn, accountId, amountDeposit);
					JOptionPane.showMessageDialog(frame, "Deposit succeeded.");
				}

				ResultSet balanceTable2 = Application.stmt.executeQuery(getBalance);
				while (balanceTable2.next()) {
					Float balance = balanceTable2.getFloat("balance");
					System.out.println("After Money:" + balance);
				}
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}

}

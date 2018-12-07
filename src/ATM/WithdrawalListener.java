package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import BankTellerFunctions.BankTellerUtility;
import DebtsRus.Application;

public class WithdrawalListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;

	JComboBox accountNumber;
	JTextField withdrawalAmount;
	
	WithdrawalListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Withdrawal clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JComboBox(ATMOptionUtility.findAllCheckingSavingAccountNumbers(ssn).toArray());
		
		JLabel withdrawalAmountLabel = new JLabel("Enter Amount for Withdrawal: ");
		withdrawalAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(3,3));
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(withdrawalAmountLabel);
		panel.add(withdrawalAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int accountId = Integer.parseInt(accountNumber.getSelectedItem().toString());
			float amountWithdrawal = Float.parseFloat(withdrawalAmount.getText());
			
			// check if account exists and that its type is either checking or saving
			try {

				Float balance = ATMOptionUtility.getBalanceFromAccountId(accountId);
				System.out.println("Initial Money:" + balance);

				if(ATMOptionUtility.checkEnoughBalance(accountId, amountWithdrawal)) {
					ATMOptionUtility.subtractMoneyToAccountId(accountId, amountWithdrawal);
					ATMOptionUtility.addToTransactionsTable("Withdrawal", ssn, accountId, amountWithdrawal);
					JOptionPane.showMessageDialog(frame, "Withdrawal succeeded.");
					if(balance - amountWithdrawal <= 0.01) {
						String closeAccount = "UPDATE CR_ACCOUNTS SET ISCLOSED = 1 WHERE ACCOUNTID = " + accountId;
						int numRowsUpdated = Application.stmt.executeUpdate(closeAccount);
						assert(numRowsUpdated == 1);
						
						BankTellerUtility.showPopUpMessage("Since your account: " + accountId + " balance was less than or "
								+ "equal to $0.01, your account was closed.");
					}

				} else {
					JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
				}

				balance = ATMOptionUtility.getBalanceFromAccountId(accountId);
				System.out.println("After Money:" + balance);

			} catch (SQLException error) {
				error.printStackTrace();
			}

		}
		
	}

}

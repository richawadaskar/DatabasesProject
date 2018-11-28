import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CheckTransactionListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	//JLabel accountNumberLabel;
	JTextField accountNumber;
	JTextField checkAmount;
	
	CheckTransactionListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("transaction clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		// create panel that asks for account number, and check amount and edit transaction table
		JLabel accountNumberLabel = new JLabel("Enter Account Number: ");
		accountNumber = new JTextField(20);
		
		JLabel checkAmountLabel = new JLabel("Enter Amount for Check: ");
		checkAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		// add action listener for enter and then do background shit. Show pop ups if info is wrong.
		
		panel.add(accountNumberLabel);
		panel.add(accountNumber);
		panel.add(checkAmountLabel);
		panel.add(checkAmount);
		panel.add(enter);
		
		panel.updateUI();
	}
	
	private class EnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Enter was pressed.");
			
			System.out.println("Account number entered was: " + accountNumber.getText());
			System.out.println("Amount for check is: " + checkAmount.getText());

		}
		
	}
	
}
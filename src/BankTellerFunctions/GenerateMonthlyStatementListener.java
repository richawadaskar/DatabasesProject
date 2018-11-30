package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GenerateMonthlyStatementListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;

	GenerateMonthlyStatementListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("monthly statements clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		int enterSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter Customer's ssn: "));

		// query for all 
		
		// query the transactions table for all transactions with this customer account.
		// Generate a pop up text view with all the information needed for this report.
		
		panel.updateUI();

	}
	
	// add action listener for text field here.
	
	// 
	
}
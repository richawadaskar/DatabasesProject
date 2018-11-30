import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
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
		
		JLabel customerIdLabel = new JLabel("Enter Customer Id: ");
		JTextField customerId = new JTextField();
		
		// query the transactions table for all transactions with this customer account.
		// Generate a pop up text view with all the information needed for this report.
		
		panel.add(customerIdLabel);
		panel.add(customerId);
		panel.updateUI();

	}
	
	// add action listener for text field here.
	
	// 
	
}
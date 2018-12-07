package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import DebtsRus.Application;

public class DeleteClosedAccountsCustomersListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	DeleteClosedAccountsCustomersListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("deleting accounts customers clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		String query = "DELETE FROM CR_ACCOUNTS WHERE ISCLOSED = 1";
		
		try {
			int numUpdated = Application.stmt.executeUpdate(query);
			
			String query2 = "DELETE FROM CR_CUSTOMER "
					+ "WHERE CR_CUSTOMER.SSN NOT IN ( SELECT SSN"
													+ " FROM CR_ACCOUNTSOWNEDBY )";
			
			int numUpdated2 = Application.stmt.executeUpdate(query2);
			
			BankTellerUtility.showPopUpMessage("Deleted " + numUpdated + " Closed Accounts and " 
					+ numUpdated2 + " Closed Customers.");
			
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		panel.updateUI();
	}
	
}
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
		//TODO: if pocket's parent is deleted, delete pocket too. Also in DELETE TRANSACTIONS.
		System.out.println("deleting accounts customers clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		String query = "DELETE FROM CR_ACCOUNTS WHERE ISCLOSED = 1";
		
		try {
			int numUpdated = Application.stmt.executeUpdate(query);
						
			String query2 = "DELETE FROM CR_CUSTOMERS "
					+ "WHERE CR_CUSTOMERS.SSN NOT IN ( SELECT SSN"
													+ " FROM CR_ACCOUNTSOWNEDBY )";
			
			int numUpdated2 = Application.stmt.executeUpdate(query2);
			
			BankTellerUtility.showPopUpMessage("Deleted " + (numUpdated + numUpdated2) + " rows.");
			
			
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		panel.updateUI();
	}
	
}
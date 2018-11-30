package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DebtsRus.Application;

public class AddInterestListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	AddInterestListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("add interest clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		// TODO: CHECK FOR DUPLICATES OF ADDING INTEREST, AND REPORT A WARNING IF THAT'S THE CASE.
		
		String updateQuery = "UPDATE CR_ACCOUNTS SET balance = balance * interestRate WHERE isClosed = 0";
		try {
			int numUpdated = Application.stmt.executeUpdate(updateQuery);
			
			// display successful text. :)
			BankTellerUtility.showPopUpMessage("Successful brother. Updated " + numUpdated + " rows.");
				
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		panel.updateUI();
	}
	
}
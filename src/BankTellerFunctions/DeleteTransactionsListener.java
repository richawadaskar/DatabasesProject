package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import DebtsRus.Application;

public class DeleteTransactionsListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	DeleteTransactionsListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("deleting transactions clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		String query = "DELETE FROM CR_TRANSACTIONS";
		
		try {
			int numUpdated = Application.stmt.executeUpdate(query);
			
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
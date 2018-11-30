package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DebtsRus.Application;

public class CustomerReportListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;

	CustomerReportListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("customer report clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		int enterSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter Customer's ssn: "));
		
		String query = "SELECT AO.ACCOUNTID, ACC.ISCLOSED "
				+ "FROM CR_ACCOUNTSOWNEDBY AO, CR_ACCOUNTS ACC "
				+ "WHERE AO.SSN = " + enterSSN + " AND AO.ACCOUNTID = ACC.ACCOUNTID";
		try {
			ResultSet set = Application.stmt.executeQuery(query);
			
			String output = "";
			while(set.next()) {
				int accountId = Integer.parseInt(set.getString("accountId"));
				int isClosed = set.getInt("isClosed");
				
				System.out.print("AccountId: " + accountId + ", ");
				if(isClosed == 1) System.out.println("Closed: true");
				else System.out.print("Closed: false");
				System.out.println("");
			}

			panel.removeAll();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		panel.updateUI();

	}
	
}
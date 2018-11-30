package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DebtsRus.Application;

public class GenerateMonthlyStatementListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	ArrayList<Integer> accountList;

	GenerateMonthlyStatementListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		accountList = new ArrayList<Integer>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("monthly statements clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		int enterSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter Customer's ssn: "));

		// query for all accounts that customer has.
		String query = "SELECT ACCOUNTID "
				+ " FROM ACCOUNTSOWNEDBY AO"
				+ " WHERE AO.SSN = " + enterSSN;
		
		try {
			ResultSet accounts = Application.stmt.executeQuery(query);
			
			while(accounts.next()) {
				int id = Integer.parseInt(accounts.getString("accoundId"));
				
			}
			
			String query2 = "DELETE FROM CR_CUSTOMERS "
					+ "WHERE CR_CUSTOMERS.SSN NOT IN ( SELECT SSN"
													+ " FROM CR_ACCOUNTSOWNEDBY )";
			
			int numUpdated2 = Application.stmt.executeUpdate(query2);
			
			//BankTellerUtility.showPopUpMessage("Deleted " + (numUpdated + numUpdated2) + " rows.");
			
			
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		// query the transactions table for all transactions with this customer account.
		// Generate a pop up text view with all the information needed for this report.
		
		panel.updateUI();

	}
	
	// add action listener for text field here.
	
	// 
	
}
package BankTellerFunctions;

import java.awt.GridLayout;
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
	JTextField ssn;
	ArrayList<Integer> accountList;

	GenerateMonthlyStatementListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		accountList = new ArrayList<Integer>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("monthly statements clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel customerSSN = new JLabel("Enter Customer SSN");
		ssn = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(1,3));
		panel.add(customerSSN);
		panel.add(ssn);
		panel.add(enter);
		panel.updateUI();

	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try{
				int taxId = Integer.parseInt(ssn.getText());
				
				String query = "SELECT ACCOUNTID "
						+ " FROM ACCOUNTSOWNEDBY AO"
						+ " WHERE AO.SSN = " + taxId;
				
				ResultSet accounts = Application.stmt.executeQuery(query);
				
				while(accounts.next()) {
					int id = Integer.parseInt(accounts.getString("accoundId"));
					
				}
				
				String query2 = "DELETE FROM CR_CUSTOMERS "
						+ "WHERE CR_CUSTOMERS.SSN NOT IN ( SELECT SSN"
														+ " FROM CR_ACCOUNTSOWNEDBY )";
				
				int numUpdated2 = Application.stmt.executeUpdate(query2);
				
				panel.removeAll();
				BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);

				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch(Exception e) {
				BankTellerUtility.showPopUpMessage("Invalid ssn was entered. SSN must contain only numerical digits");
			}
		}
		
	}
	
}
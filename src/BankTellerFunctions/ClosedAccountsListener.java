package BankTellerFunctions;

import java.awt.event.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import DebtsRus.Application;

public class ClosedAccountsListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	ClosedAccountsListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("close accounts clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		String query = "SELECT accountId FROM CR_ACCOUNTS WHERE isClosed = 1";
		try {
			ResultSet set = Application.stmt.executeQuery(query);
			
			String output = "";
			while(set.next()) {
				String a = set.getString(1);
				output += a += "\n";
			}
			
			JLabel display = new JLabel(output);
			
			panel.removeAll();
			panel.add(display);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		panel.updateUI();

	}
	
}
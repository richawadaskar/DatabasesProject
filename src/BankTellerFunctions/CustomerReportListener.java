package BankTellerFunctions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DebtsRus.Application;

public class CustomerReportListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JTextField customerSSN;
	JLabel customerId;
	JButton enter;

	CustomerReportListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("customer report clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		panel.setLayout(new GridLayout(2,2));

		customerId = new JLabel("Enter Customer SSN: ");
		customerSSN = new JTextField(20);

		enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());

		setUpUI();
		
		panel.updateUI();
	}
	
	public void setUpUI() {
		panel.add(customerId);
		panel.add(customerSSN);
		panel.add(enter);
	}
	
	private class EnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Enter was clicked.");
			
			try {
				
				int enterSSN = Integer.parseInt(customerSSN.getText());
				
				String query = "SELECT AO.ACCOUNTID, ACC.ISCLOSED "
						+ "FROM CR_ACCOUNTSOWNEDBY AO, CR_ACCOUNTS ACC "
						+ "WHERE AO.SSN = " + enterSSN + " AND AO.ACCOUNTID = ACC.ACCOUNTID";

				ResultSet set = Application.stmt.executeQuery(query);
				
				String output = "\n";
				while(set.next()) {
					int accountId = set.getInt("accountId");
					int isClosed = set.getInt("isClosed");
					
					output += ("AccountId: " + accountId + ", ");
					if(isClosed == 1) output += ("Closed: true");
					else output += ("Closed: false");
					output += "\n";
				}
				
				System.out.println(output);
				
				// TODO: Display customer report somewhere nicely :D
				
				BankTellerUtility.showPopUpMessage("Here is the customer report: " + output);
				
				panel.removeAll();
				setUpUI();
				panel.updateUI();
				
			} catch (SQLException e1) {
				BankTellerUtility.showPopUpMessage(e1.toString());
			} catch (Exception e) {
				BankTellerUtility.showPopUpMessage("Invalid inputs were entered.");
			}

		}
	}
	
}
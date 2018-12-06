package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DebtsRus.Application;

public class DTERListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	DTERListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("GTDR clicked");
		
		String query = "SELECT * "
				+ "FROM CR_CUSTOMER C "
				+ "WHERE C.ssn IN "
								+ "	(SELECT customerID "
								+ "	FROM CR_TRANSACTIONS "
								+ " WHERE TRANSACTIONTYPE = 'Deposit' OR TRANSACTIONTYPE = 'transfer' OR TRANSACTIONTYPE = 'wire' "
								+ " GROUP BY customerId "
								+ " HAVING SUM(AMOUNT) > 10000)";
		
		String output = "";
		try {
			ResultSet set = Application.stmt.executeQuery(query);
			
			while(set.next()) {
				String ssn = set.getString("ssn");
				String name = set.getString("name");
				output += "SSN: " + ssn + ", ";
				output += "NAME: " + name + "\n";
			}
			
			// TODO: Display this output somewhere on a panel or something.
			BankTellerUtility.showPopUpMessage(output);
				
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		panel.updateUI();
	}
	
}
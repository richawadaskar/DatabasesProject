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
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
				
		String query = "SELECT * "
				+ "FROM CR_CUSTOMER C "
				+ "WHERE C.ssn IN "
								+ "	(SELECT customerID "
								+ "	FROM CR_TRANSACTIONS "
								+ " WHERE TRANSACTIONTYPE = 'Deposit' OR TRANSACTIONTYPE = 'transfer' OR TRANSACTIONTYPE = 'wire' "
								+ " GROUP BY customerId "
								+ " HAVING SUM(AMOUNT) > 10000)";
		
		try {
			ResultSet set = Application.stmt.executeQuery(query);
			
			while(set.next()) {
				String ssn = set.getString("ssn");
				String name = set.getString("name");
				System.out.print("SSN: " + ssn + ", ");
				System.out.println("NAME: " + name + "\n");
			}
			
			// TODO: Display this output somewhere on a panel or something.
			
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		panel.updateUI();
	}
	
}
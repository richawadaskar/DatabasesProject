package BankTellerFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DebtsRus.Application;

public class BankTellerUtility {
	
	public static void setUpBackPanelToBankTeller(JPanel backPanel, JButton backButton){
		backPanel.removeAll();
		backPanel.add(backButton);
		backPanel.updateUI();
	}
	
	public static void showPopUpMessage(String str) {
		JOptionPane.showMessageDialog(BankTeller.frame, str);	
	}
	
	public static boolean existsCustomer(int customerSSN) throws SQLException {
		String customerExists = "SELECT * FROM CR_CUSTOMER WHERE ssn =" + customerSSN;
		
		ResultSet exists = Application.stmt.executeQuery(customerExists);
		return exists.next();
	}
}
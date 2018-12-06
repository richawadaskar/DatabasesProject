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

	public static boolean existsAccount(int accountId) throws SQLException {
		String customerExists = "SELECT * FROM CR_ACCOUNTS WHERE accountId =" + accountId;

		ResultSet exists = Application.stmt.executeQuery(customerExists);
		return exists.next();
	}
	public static boolean existsPocketAccount(int accountId) throws SQLException {
		String customerExists = "SELECT * FROM CR_POCKET WHERE accountId =" + accountId;

		ResultSet exists = Application.stmt.executeQuery(customerExists);
		return exists.next();
	}

	public static boolean existsOwnedBy(int accountId, int ssn) throws SQLException {
		String customerExists = "SELECT * FROM CR_ACCOUNTSOWNEDBY WHERE accountId =" + accountId + " AND ssn = " + ssn;

		ResultSet exists = Application.stmt.executeQuery(customerExists);
		return exists.next();
	}

	public static int getNumberTransactions() throws SQLException {
		String numTransactions = "SELECT COUNT(TRANSACTIONID) FROM CR_TRANSACTIONS" ;
		
		ResultSet exists = Application.stmt.executeQuery(numTransactions);
		
		if(exists.next()) return exists.getInt(1);
		else throw new SQLException();
	}

	public static void setInterestRate(String accountType, float interestRate) {
		String updateQuery = "UPDATE CR_ACCOUNTS SET interestRate = " + interestRate
				+ " WHERE isClosed = 0 AND accountType = '" + accountType +"'";

		try {
			int updateInterest = Application.stmt.executeUpdate(updateQuery);

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
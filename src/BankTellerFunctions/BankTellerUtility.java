package BankTellerFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static int generateAccountID() throws SQLException {
		String maxAccounts = "SELECT MAX(ACCOUNTID) FROM CR_ACCOUNTS" ;
		
		ResultSet exists = Application.stmt.executeQuery(maxAccounts);
		
		if(exists.next()) return exists.getInt(1);
		else throw new SQLException();
	}
	
	public static void addOwnersIntoOwnedByTable(ArrayList<Integer> owners, int accountId) throws SQLException {
		boolean first = true;
		int isPrimaryOwner = 1;
		for(int ownerID: owners) {
			String toTransactions = "";
			int transactionId = BankTellerUtility.getNumberTransactions() + 1;
			toTransactions = "INSERT INTO CR_ACCOUNTSOWNEDBY VALUES "
					+ "(" + accountId + ", " + ownerID + ", " + isPrimaryOwner + ")";
			if(first) {
				isPrimaryOwner = 0;
				first = false;
			}
		}
	}
	
	public static void addToCustomersTable(int ssn, String name, String address, String pin) throws SQLException {
		String query = "INSERT INTO CR_CUSTOMER VALUES(" + ssn + ", '" + name + "', '" + address + "', '" + pin + "')";
		System.out.println(query);
		
		int numRowsUpdated = Application.stmt.executeUpdate(query);
		System.out.println("rows updated: " + numRowsUpdated);
		assert(numRowsUpdated == 1);
	}
	
	public static double getInterestRate(String typeAccount) {
		if(typeAccount == "Pocket") return Application.pocketInterestRate;
		if(typeAccount == "Student-Checkings") return Application.checkingInterestRate;
		if(typeAccount == "Interest-Checkings") return Application.checkingInterestRate;
		if(typeAccount == "Savings") return Application.savingsInterestRate;
		return 0;
	}
}
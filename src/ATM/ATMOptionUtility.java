package ATM;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DebtsRus.Application;

public class ATMOptionUtility {

	public static void setUpBackPanelToBankTeller(JPanel backPanel, JButton backButton){
		backPanel.removeAll();
		backPanel.add(backButton);
		backPanel.updateUI();
	}
	//transactionId, transactionType, date, customerId, account1Id, amount, otherInformation

	public static void addToTransactionsTable(String transactionType, int customerId, int account1Id, int account2Id, float amount) throws SQLException {
		
		String addTransaction = "INSERT into CR_TRANSACTIONS values( "
				+ "1, "
				+ transactionType + ", "
				+ "3-2-2011, "
				+ customerId + ", "
				+ account1Id + ", "
				+ account2Id + ", "
				+ "float";
		Application.stmt.executeUpdate(addTransaction);
		
	}
	
	public static boolean checkEnoughBalance(int accountId, float amount) {
		
		if(getBalanceFromAccountId(accountId) - amount > 0) 
			return true;
		
			return false;
		
	}
	
	public static float getBalanceFromAccountId(int accountId) {
		float balance = 0;
		String getBalance = "SELECT balance FROM CR_ACCOUNTS WHERE accountId = " + accountId;
		try {
			ResultSet bal = Application.stmt.executeQuery(getBalance);
			while(bal.next()) {
				balance = bal.getFloat("balance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return balance;
		
	}
	
	public static boolean checkIfAccountIsPocket(int accountId) {
		String checkIfPocket = "SELECT accountType FROM CR_ACCOUNTS WHERE accountId = " + accountId;
		try {
			ResultSet checking = Application.stmt.executeQuery(checkIfPocket);
			while(checking.next()) {
				String accountType = checking.getString("accountType");
				if(accountType.toLowerCase().equals("pocket")) 
					return true;			
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return false;
	}

	public static void subtractMoneyToAccountId(int fromAccountId, float amountTransfer) {
		// TODO Auto-generated method stub
		float currBalance = getBalanceFromAccountId(fromAccountId);
		float newBalance = currBalance - amountTransfer;
		String depositMoney = "Update CR_ACCOUNTS SET balance = " + newBalance + "WHERE accountId = " + fromAccountId;
		try {
			Application.stmt.executeUpdate(depositMoney);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void addMoneyToAccountId(int toAccountId, float amountTransfer) {
		// TODO Auto-generated method stub
		float currBalance = getBalanceFromAccountId(toAccountId);
		float newBalance = currBalance + amountTransfer;
		String depositMoney = "Update CR_ACCOUNTS SET balance = " + newBalance + "WHERE accountId = " + toAccountId;
		try {
			Application.stmt.executeUpdate(depositMoney);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

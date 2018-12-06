package ATM;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import BankTellerFunctions.BankTellerUtility;
import DebtsRus.Application;

public class ATMOptionUtility {
	
	static String date = "to_date('" + Application.getDate() + "', 'mm-dd-yyyy')";

	public static boolean checkCredentials(String pinn){
		String sql3 = "SELECT * FROM CR_CUSTOMER";

		String sql2 = ("SELECT * FROM CR_CUSTOMER WHERE PIN = " + pinn);
		try {
			ResultSet tables1 = Application.stmt.executeQuery(sql2);
			if(tables1.next()){
				System.out.println(tables1.getString("pin"));
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void setPin(JFrame frame, int oldPin, int newPin) {
		String pinExists = "SELECT pin FROM CR_CUSTOMER WHERE pin =" + newPin;

		try {
			ResultSet exists = Application.stmt.executeQuery(pinExists);
			if(exists.next()) {
				JOptionPane.showMessageDialog(frame, "PIN is taken. Try again.");
			} else {
				String set = "Update CR_CUSTOMER SET PIN = " + newPin + "WHERE PIN = " + oldPin;
				try {
					Application.stmt.executeUpdate(set);
					JOptionPane.showMessageDialog(frame, "Nice brother! PIN is reset.");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<Integer> findAllAccountNumbers(int ssn) {
		List<Integer> accountNumbers = new ArrayList<>();
		String customerExists = "SELECT AO.accountId FROM CR_ACCOUNTSOWNEDBY AO WHERE AO.ssn = " + ssn +
				"UNOIN SELECT A.accountId FROM CR_ACCOUNTS A WHERE A.isCLosed = 0";

		try {
			ResultSet exists = Application.stmt.executeQuery(customerExists);
			while (exists.next()) {
				int accountId = exists.getInt("accountId");
				accountNumbers.add(accountId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accountNumbers;

	}

	public static List<Integer> findAllCheckingSavingAccountNumbers(int ssn) {
		List<Integer> accountNumbers = new ArrayList<>();
		String customerExists = "SELECT AO.accountId FROM CR_ACCOUNTSOWNEDBY AO WHERE AO.ssn = " + ssn +
				"AND AO.accountId IN (SELECT A.accountId FROM CR_ACCOUNTS A WHERE A.isCLosed = 0 " +
				"AND A.accountType = 'Student-Checking' OR A.accountType = 'Interest-Checking' OR A.accountType = 'Savings')";
		//String customerExists1 = "SELECT AO.accountId FROM CR_ACCOUNTSOWNEDBY AO WHERE AO.ssn = " + ssn;

		try {
			ResultSet exists = Application.stmt.executeQuery(customerExists);
			while (exists.next()) {
				int accountId = exists.getInt("accountId");
				accountNumbers.add(accountId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accountNumbers;

	}

	public static List<Integer> findAllCheckingSavingAccountNumbers() {
		List<Integer> accountNumbers = new ArrayList<>();
		String csExists = "SELECT A.accountId FROM CR_ACCOUNTS A WHERE A.isCLosed = 0 " +
				"A.accountType = 'Student-Checking' OR A.accountType = 'Interest-Checking' OR A.accountType = 'Savings'";
		//String customerExists1 = "SELECT AO.accountId FROM CR_ACCOUNTSOWNEDBY AO WHERE AO.ssn = " + ssn;

		try {
			ResultSet exists1 = Application.stmt.executeQuery(csExists);
			while (exists1.next()) {
				int accountId = exists1.getInt("accountId");
				accountNumbers.add(accountId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accountNumbers;

	}

	public static List<Integer> findAllPocketAccountNumbers(int ssn) {
		List<Integer> accountNumbers = new ArrayList<>();
		String pocketExists = "SELECT O.accountId FROM CR_ACCOUNTSOWNEDBY O WHERE O.ssn = " + ssn +
				"INTERSECT SELECT A.accountId FROM CR_ACCOUNTS A WHERE A.isCLosed = 0 AND A.accountType = 'Pocket'";
		try {
			ResultSet exists = Application.stmt.executeQuery(pocketExists);
			while (exists.next()) {
				int accountId = exists.getInt("accountId");
				accountNumbers.add(accountId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accountNumbers;

	}
	public static List<Integer> findAllPocketAccountNumbers() {
		List<Integer> accountNumbers = new ArrayList<>();
		String allPocket = "SELECT accountId FROM CR_POCKET WHERE " +
				"accountId IN (SELECT A.accountId FROM CR_ACCOUNTS A WHERE A.isClosed = 0)";
		try {
			ResultSet exists1 = Application.stmt.executeQuery(allPocket);
			while (exists1.next()) {
				int accountId = exists1.getInt("accountId");
				accountNumbers.add(accountId);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return accountNumbers;

	}



	public static void setUpBackPanelToBankTeller(JPanel backPanel, JButton backButton){
		backPanel.removeAll();
		backPanel.add(backButton);
		backPanel.updateUI();
	}
	//transactionId, transactionType, date, customerId, account1Id, amount, otherInformation

	public static void addToTransactionsTable(String transactionType, int ssn, int account1Id, float amount) throws SQLException {
		System.out.println(Application.getDate());

		int transactionId = BankTellerUtility.getNumberTransactions()+1;
		//int customerId = getCustomerId(name);
		
		
		
		String addTransaction = "INSERT into CR_TRANSACTIONS values( "
				+ transactionId + ", '" + transactionType + "', " + date + ", " + ssn + ", " + account1Id + ", null, " + amount + ", null" + ")";
		Application.stmt.executeUpdate(addTransaction);
		
	}
	
	public static void addToTransactionsTable(String transactionType, int ssn, int account1Id, int account2Id, float amount) throws SQLException {
		int transactionId = BankTellerUtility.getNumberTransactions()+1;
		//int customerId = getCustomerId(name);
		
		String addTransaction = "INSERT into CR_TRANSACTIONS values( "
				+ transactionId + ", '" + transactionType + "', " + date + ", " + ssn + ", " + account1Id + ", " + account2Id + ", " + amount + ", " + null + ")";
		Application.stmt.executeUpdate(addTransaction);
		
	}
	
	public static void addToCustomersTable(int ssn, String name, String address, int pin) throws SQLException {
		String addCustomer = "INSERT into CR_CUSTOMER values( " 
				+ ssn + ", '"
				+ name + "', '"
				+ address + "', "
				+ pin + ")";
		//String addCustomer1 = "INSERT INTO CR_CUSTOMER VALUES (400651982, 'Pit Wilson', '911 State St', 1821)";
		Application.stmt.executeUpdate(addCustomer);
	}

	public static void addToAccountsTable(int accountId, int primaryOwnerSSN, String branchName, float interestRate,  float balance,
										  float initialMonthlyBalance, int isClosed, String accountType) throws SQLException {
		String addAccount = "INSERT into CR_ACCOUNTS values( "
				+ accountId + ", "+ primaryOwnerSSN + ", '" + branchName + "', " + interestRate + ", "
				+ balance + ", " + initialMonthlyBalance + ", " + isClosed + ", '" + accountType + "')";

		//String addCustomer1 = "INSERT INTO CR_CUSTOMER VALUES (400651982, 'Pit Wilson', '911 State St', 1821)";
		Application.stmt.executeUpdate(addAccount);

		addToTransactionsTable("Deposit", primaryOwnerSSN, accountId, initialMonthlyBalance);
	}

	public static void addToPocketAccountTable(int pocketAccountId, int linkedAccount) throws SQLException {

		String addPocket = "INSERT into CR_POCKET values( "
				+ pocketAccountId + ", "+ linkedAccount + ")";

		Application.stmt.executeUpdate(addPocket);
	}

	public static void addToOwnedByTable(int accountId, int ownerId, int isPrimaryOwner) throws SQLException {
		String addOwned = "INSERT into CR_ACCOUNTSOWNEDBY values( "
				+ accountId + ", "+ ownerId + ", " + isPrimaryOwner + ")";

		Application.stmt.executeUpdate(addOwned);
	}

	public static int getLinkedAccount(int pocketAccount) {
		int linkedAccount = 0;
		String customerExists = "SELECT linkedAccountId FROM CR_POCKET WHERE accountId =" + pocketAccount;

		try {
			ResultSet exists = Application.stmt.executeQuery(customerExists);
			while (exists.next()) {
				linkedAccount = exists.getInt("linkedAccountId");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return linkedAccount;
	}

	public static int getCustomerId(String name) throws SQLException {
		int ssn = 0;
		String customerExists = "SELECT ssn FROM CR_CUSTOMER WHERE name =" + name;
		
		ResultSet exists = Application.stmt.executeQuery(customerExists);
		while(exists.next()) {
			ssn = exists.getInt("ssn");
		}
		return ssn;
	}

	
	public static boolean checkEnoughBalance(int accountId, float amount) {
		
		if(getBalanceFromAccountId(accountId) - amount >= 0)
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
	
	public static void insertIntoCustomerTable() throws SQLException, IOException {
		BufferedReader customers = new BufferedReader(new FileReader("/cs/student/richa_wadaskar/cs174A/project/DatabasesProject/users.csv"));
            
		String line = customers.readLine();
            
        while(line != null) {
            String[] lineParts = line.split(",");
            for(int i = 0; i < lineParts.length; i++) {
            	System.out.print(lineParts[i] + ",");
            }
           	System.out.println();
           	int ssn = Integer.parseInt(lineParts[0]);
           	if(!BankTellerUtility.existsCustomer(ssn)) {
           		int pin = Integer.parseInt(lineParts[3]);
           		ATMOptionUtility.addToCustomersTable(ssn,  lineParts[1], lineParts[2], pin);
           	}
           	line = customers.readLine();
        }
            
        customers.close();
	}

	public static void insertIntoAccountsTable() throws SQLException, IOException {
		BufferedReader accounts = new BufferedReader(new FileReader("/cs/student/richa_wadaskar/cs174A/project/DatabasesProject/accounts.csv"));

		String line = accounts.readLine();

		while(line != null) {
			String[] lineParts = line.split(",");

			int accountId = Integer.parseInt(lineParts[0]);
			if(!BankTellerUtility.existsAccount(accountId)) {
				int primaryOwner = Integer.parseInt(lineParts[1]);
				float interestRate = Float.parseFloat(lineParts[3]);
				float balance = Float.parseFloat(lineParts[4]);
				float initialBalance = Float.parseFloat(lineParts[5]);
				int isClosed = Integer.parseInt(lineParts[6]);
				ATMOptionUtility.addToAccountsTable(accountId, primaryOwner, lineParts[2], interestRate,
						balance, initialBalance, isClosed, lineParts[7]);
				System.out.println("ADDDEDEDEDEDEDEDEDEE");
			}
			for(int i = 0; i < lineParts.length; i++) {
				System.out.print(lineParts[i] + ",");
			}
			System.out.println();
			line = accounts.readLine();
		}

		accounts.close();
	}

	public static void insertIntoPocketAccountsTable() throws SQLException, IOException {
		BufferedReader accounts = new BufferedReader(new FileReader("/cs/student/richa_wadaskar/cs174A/project/DatabasesProject/pockets.csv"));

		String line = accounts.readLine();

		while(line != null) {
			String[] lineParts = line.split(",");
			for(int i = 0; i < lineParts.length; i++) {
				System.out.print(lineParts[i] + ",");
			}
			System.out.println();
			int pocketAccountId = Integer.parseInt(lineParts[0]);
			if(!BankTellerUtility.existsPocketAccount(pocketAccountId)) {
				int linkedAccount = Integer.parseInt(lineParts[1]);

				ATMOptionUtility.addToPocketAccountTable(pocketAccountId, linkedAccount);

			}
			line = accounts.readLine();
		}
		accounts.close();
	}

	public static void insertIntoOwnedTable() throws SQLException, IOException {
		BufferedReader owned = new BufferedReader(new FileReader("/cs/student/richa_wadaskar/cs174A/project/DatabasesProject/accountsOwnedBy.csv"));

		String line = owned.readLine();

		while(line != null) {
			String[] lineParts = line.split(",");
			for(int i = 0; i < lineParts.length; i++) {
				System.out.print(lineParts[i] + ",");
			}
			System.out.println();
			int accountId = Integer.parseInt(lineParts[0]);
			int ownedBy = Integer.parseInt(lineParts[1]);
			if(!BankTellerUtility.existsOwnedBy(accountId, ownedBy)) {
				int isPrimaryOwner = Integer.parseInt(lineParts[2]);
				ATMOptionUtility.addToOwnedByTable(accountId, ownedBy, isPrimaryOwner);

			}
			line = owned.readLine();
		}

		owned.close();
	}


	
	
	
}

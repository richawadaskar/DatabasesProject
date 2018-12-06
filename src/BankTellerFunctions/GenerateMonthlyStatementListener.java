package BankTellerFunctions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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
	ArrayList<Integer> primaryOwnerAccounts;
	
	static int balance = 0;


	GenerateMonthlyStatementListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		accountList = new ArrayList<Integer>();
		primaryOwnerAccounts = new ArrayList<Integer>();
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
			balance = 0;
			
			try{				
				int taxId = Integer.parseInt(ssn.getText());
				
				findAccountsForCustomer(taxId);
								
				String transactionInformation = "";
				for(int id: accountList) {
					transactionInformation += "AccountId: " + id + "\n";
					transactionInformation += getOwnerInformation(id);
					transactionInformation += getInitialAndFinalBalance(id);
					transactionInformation += getTransactionInfo(id);
				}
				
				if(balance > 100000) {
					transactionInformation += "The limit of your insurance has been reached, since you have over $100000 "
							+ "combined in all your primary accounts.";
				}
				
				System.out.println(transactionInformation);

				// provide customer monthly statement, maybe in another panel??? For now, we're just printing to console.
				
				panel.repaint();
				BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
				
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch(Exception e) {
				BankTellerUtility.showPopUpMessage("Invalid ssn was entered. SSN must contain only numerical digits");
			}
		}
		
		public String getOwnerInformation(int accountId) throws SQLException {
			System.out.println("AccountId:asdfsda " + accountId);
			
			// TODO: WHAT THE FUCKKKK IT SHOULD RETURN MULTIPLE OWNERS.......
			String ownersQuery = "SELECT * FROM CR_ACCOUNTSOWNEDBY WHERE ACCOUNTID = " + accountId;
			ResultSet res = Application.stmt.executeQuery(ownersQuery);
			
			String ownersInfo = "";
			while(res.next()) {
				int ssn = res.getInt(2);
				System.out.println("Hi: " + ssn + ", ");
				String getOwnerInfo = "SELECT * FROM CR_CUSTOMER WHERE SSN = " + ssn;
				
				ResultSet info = Application.stmt.executeQuery(getOwnerInfo);
				
				while(info.next()) {
					String ownerName = info.getString("name");
					String ownerAddress = info.getString("address");
					ownersInfo += "owner: " + ownerName + ", address: " + ownerAddress + "\n";
				}
			}
			
			System.out.println(ownersInfo);
			return ownersInfo;
		}
		
		public String getInitialAndFinalBalance(int accountId) throws SQLException{
			String balanceInfo = "";
			String queryAccountInfo = "SELECT Balance, InitialMonthlyBalance FROM CR_ACCOUNTS WHERE ACCOUNTID = " + accountId;
			ResultSet res = Application.stmt.executeQuery(queryAccountInfo);
			
			while(res.next()) {
				String balance = res.getString("balance");
				String initialMonthly = res.getString("initialmonthlybalance");
				balanceInfo += "Initial balance: " + initialMonthly + ", finalBalance: " + balance + "\n";
				
				if(primaryOwnerAccounts.contains(accountId)) {
					balance += Integer.parseInt(balance);
				}
			}
			
			return balanceInfo;
		}
		
		public String getTransactionInfo(int accountId) throws SQLException {
			String accum = "";
			String queryForTransactions = "SELECT * FROM CR_TRANSACTIONS WHERE ACCOUNT1ID = " + accountId + " OR ACCOUNT2ID = " + accountId;
			ResultSet accountTransactions = Application.stmt.executeQuery(queryForTransactions);
			
			while(accountTransactions.next()) {
	            int transId = accountTransactions.getInt("transactionid");
	            String transType = accountTransactions.getString("transactiontype");
	            int customerId = accountTransactions.getInt("customerid");
	            int account1Id = accountTransactions.getInt("account1id");
	            int account2Id = accountTransactions.getInt("account2id");
	            int amount = accountTransactions.getInt("amount");
	            String otherInfo = accountTransactions.getString("otherinformation");
	            Date transDate = accountTransactions.getDate("transactiondate");
	            
	            accum += "transactionId: " + transId + ", " + "transactionType: " + transType + 
	            		", account1Id: " + account1Id + ", account2Id: " + account2Id + ", amount: " + amount +
	            		", otherInfo: " + otherInfo + ", dateTransaction: " + transDate + "\n";
			}
			
			return accum;

		}
		
		public void findAccountsForCustomer(int taxId) throws SQLException {

			String query = "SELECT ACCOUNTID, ISPRIMARYOWNER "
					+ " FROM CR_ACCOUNTSOWNEDBY AO"
					+ " WHERE AO.SSN = " + taxId;
			
			ResultSet accounts = Application.stmt.executeQuery(query);

			while(accounts.next()) {
				int id = Integer.parseInt(accounts.getString("accountId"));
				int primaryOwner = Integer.parseInt(accounts.getString(2));
				if(primaryOwner == 1) primaryOwnerAccounts.add(id);
				accountList.add(id);
			}
		}
	}
	
}
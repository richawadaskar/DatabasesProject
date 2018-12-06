package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DebtsRus.Application;

public class AddInterestListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	AddInterestListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("add interest clicked");
								
		try {
			if(interestAlreadyAdded()) {
				BankTellerUtility.showPopUpMessage("Warning: Interest has already been added this month.");
			} else {
				addInterest();
			}
		} catch (SQLException e1) {
			BankTellerUtility.showPopUpMessage("An error has occurred. Please try again later.");
			e1.printStackTrace();
		}
		
		panel.updateUI();
	}
	
	public boolean interestAlreadyAdded() throws SQLException {
		String checkInterestQuery = "SELECT TransactionDate FROM CR_TRANSACTIONS WHERE TransactionType = 'addInterest'";
		
		Date date;
		Calendar cal = Calendar.getInstance();
		ResultSet res = Application.stmt.executeQuery(checkInterestQuery);
		
		while(res.next()) {
			date = res.getDate(1);
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);

			cal.setTime(Application.date);
			int month2 = cal.get(Calendar.MONTH);
			
			System.out.println("Month1: " + month + ", Month2: " + month2);
			
			if(month == month2) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addInterest() {
		String updateQuery = "UPDATE CR_ACCOUNTS SET balance = balance * interestRate WHERE isClosed = 0";
		try {
			int numUpdated = Application.stmt.executeUpdate(updateQuery);
			
			BankTellerUtility.showPopUpMessage("Successful brother. Updated " + numUpdated + " rows.");
				
			panel.removeAll();
			BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
			
			String updateTransactionsTable = "INSERT INTO CR_TRANSACTIONS VALUES("
					+ (BankTellerUtility.getNumberTransactions() + 1) + ", "
					+ "'addInterest', to_date('"
					+ Application.getDate()
					+ "', 'mm-dd-yyyy'), "
					+ "null, "
					+ "null, "
					+ "null, "
					+ "0, "
					+ "'interestAddedForMonth')";
			
			System.out.println(updateTransactionsTable);
			
			int rowsUpdated = Application.stmt.executeUpdate(updateTransactionsTable);
			assert(rowsUpdated == 1);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}	
}
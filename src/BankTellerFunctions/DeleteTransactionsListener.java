package BankTellerFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

import DebtsRus.Application;

public class DeleteTransactionsListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	DeleteTransactionsListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("deleting transactions clicked");
						
		Calendar cal = Calendar.getInstance();
		cal.setTime(Application.date);
		int month = cal.get(Calendar.MONTH);
		
		String query = "DELETE FROM CR_TRANSACTIONS "
			+ "WHERE EXTRACT(MONTH FROM TRANSACTIONDATE) != " + month;
		
		try {
			int numUpdated = Application.stmt.executeUpdate(query);
			BankTellerUtility.showPopUpMessage("Successful brother. Updated " + numUpdated + " rows.");			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		panel.updateUI();
	}
}
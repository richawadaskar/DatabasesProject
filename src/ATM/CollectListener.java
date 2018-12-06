package ATM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import DebtsRus.Application;


public class CollectListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	JFrame frame;
	int ssn;
	
	JComboBox fromPocketAccount;
	JTextField collectAmount;
	
	CollectListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton, JFrame incomingFrame, int customerId) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
		frame = incomingFrame;
		ssn = customerId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Collect clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		
		JLabel fromPocketAccountNumberLabel = new JLabel("Enter From Pocket Account Number: ");
		fromPocketAccount = new JComboBox(ATMOptionUtility.findAllPocketAccountNumbers(ssn).toArray());
		
		JLabel collectAmountLabel = new JLabel("Enter Amount to Collect: ");
		collectAmount = new JTextField(20);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.setLayout(new GridLayout(4,3));
		panel.add(fromPocketAccountNumberLabel);
		panel.add(fromPocketAccount);
		panel.add(collectAmountLabel);
		panel.add(collectAmount);
		panel.add(enter);
		
		panel.updateUI();
		
	}
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int pocketAccountId = Integer.parseInt(fromPocketAccount.getSelectedItem().toString());
			int parentAccountId = ATMOptionUtility.getLinkedAccount(pocketAccountId);
			float amountCollect = Float.parseFloat(collectAmount.getText());

			try {
				if(ATMOptionUtility.checkEnoughBalance(pocketAccountId, amountCollect)) {
					ATMOptionUtility.subtractMoneyToAccountId(pocketAccountId, amountCollect);
					ATMOptionUtility.addMoneyToAccountId(parentAccountId, (float)(amountCollect*0.97));
					ATMOptionUtility.addToTransactionsTable("Collect", ssn, pocketAccountId, parentAccountId, amountCollect);
					JOptionPane.showMessageDialog(frame, "Collect succeeded.");
				} else {
					JOptionPane.showMessageDialog(frame, "You don't have enough to make this transaction.");
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}

}

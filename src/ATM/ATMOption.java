package ATM;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import DebtsRus.*;

public class ATMOption {

	Application app;
	JFrame frame;
	JButton deposit;
	JButton topUp;
	JButton withdrawal;
	JButton purchase;
	JButton transfer;
	JButton collect;
	JButton wire;
	JButton payFriend;
	JButton setPIN;
	JButton backButtonToATMOption;
	JButton backButton;
	JPanel panel;
	JPanel backPanel;
	int customerId;
	
	ATMOption(Application app,JFrame frame, JPanel panel, JPanel backPanel, int pin){
		this.app = app;
		this.frame = frame;
		this.panel = panel;
		this.backPanel = backPanel;
		this.frame.setTitle("ATM Options");
		setCustomerId(pin);
		
		this.backPanel.removeAll();
		this.panel.removeAll();
		
		setUpATMOptions();
		
		this.panel.updateUI();
		this.backPanel.updateUI();

		this.frame.getContentPane().add(BorderLayout.NORTH, this.backPanel);
		this.frame.getContentPane().add(BorderLayout.CENTER, this.panel); 
		this.frame.setVisible(true);
	}

	public void setUpATMOptions() {
		panel.setLayout(new GridLayout(3,3));
		
		//backPanel = new JPanel();
		
		// create all buttons needed
		backButton = new JButton("Back to PIN page");
		backButton.addActionListener(new BackButtonListener());
		backButtonToATMOption = new JButton("Back to ATM Option");
		backButtonToATMOption.addActionListener(new BackButtonToATMOptionListener());
		
		deposit = new JButton("Deposit");
		topUp = new JButton("Top-Up");
		withdrawal = new JButton("Withdrawal");
		purchase = new JButton("Purchase");
		transfer = new JButton("Transfer");
		collect = new JButton("Collect");
		wire = new JButton("Wire");
		payFriend = new JButton("Pay-Friend");
		setPIN = new JButton("Set Pin");
		
		// add action listeners for buttons
		deposit.addActionListener(new DepositListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		topUp.addActionListener(new TopUpListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		withdrawal.addActionListener(new WithdrawalListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		purchase.addActionListener(new PurchaseListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		transfer.addActionListener(new TransferListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		collect.addActionListener(new CollectListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		wire.addActionListener(new WireListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		payFriend.addActionListener(new PayFriendListener(panel, backPanel, backButtonToATMOption, frame, customerId));
		setPIN.addActionListener(new SetPinActionListener());
		
		ATMOptionScreen();
	}
	
	public void ATMOptionScreen() {
		panel.add(deposit);
		panel.add(topUp);
		panel.add(withdrawal);
		panel.add(purchase);
		panel.add(transfer);
		panel.add(collect);
		panel.add(wire);
		panel.add(payFriend);
		panel.add(setPIN);
		
		backPanel.add(backButton);
	}
	
	public void setCustomerId(int pin) {
		String getCustomerId = "SELECT ssn FROM CR_CUSTOMER WHERE pin =" + pin;
		try {
			ResultSet table = Application.stmt.executeQuery(getCustomerId);
			while(table.next()) {
				customerId = table.getInt("ssn");
				System.out.println(customerId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class BackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Back to PIN page");
			
			panel.removeAll();
			backPanel.removeAll();
			panel.updateUI();
			backPanel.updateUI();
			app.getATM().setUpATMScreen();

		}
	}
	
	private class BackButtonToATMOptionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the nested back button :D");
						
			panel.removeAll();
			backPanel.removeAll();
			panel.updateUI();
			backPanel.updateUI();
			setUpATMOptions();

		}
	}

	private class SetPinActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			String oldPin = JOptionPane.showInputDialog(frame, "What's your old PIN?");
			if(ATMOptionUtility.checkCredentials(oldPin)) {
				String newPin = JOptionPane.showInputDialog(frame, "Enter new PIN:");
				ATMOptionUtility.setPin(frame, Integer.parseInt(oldPin), Integer.parseInt(newPin));
			} else {
				JOptionPane.showMessageDialog(frame, "PIN does not exisit.");
			}

		}
	}
	    
}

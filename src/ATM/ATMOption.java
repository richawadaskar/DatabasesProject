package ATM;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	JButton backButtonToATMOption;
	JButton backButton;
	JPanel panel;
	JPanel backPanel;
	
	ATMOption(Application app,JFrame frame, JPanel panel, JPanel backPanel){
		this.app = app;
		this.frame = frame;
		this.panel = panel;
		this.backPanel = backPanel;
		this.frame.setTitle("ATM Options");
		
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
		panel.setLayout(new GridLayout(2,4));
		
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
		
		// add action listeners for buttons
		deposit.addActionListener(new DepositListener(panel, backPanel, backButtonToATMOption, frame));
		topUp.addActionListener(new TopUpListener(panel, backPanel, backButtonToATMOption));
		withdrawal.addActionListener(new WithdrawalListener(panel, backPanel, backButtonToATMOption));
		purchase.addActionListener(new PurchaseListener(panel, backPanel, backButtonToATMOption));
		transfer.addActionListener(new TransferListener(panel, backPanel, backButtonToATMOption));
		collect.addActionListener(new CollectListener(panel, backPanel, backButtonToATMOption));
		wire.addActionListener(new WireListener(panel, backPanel, backButtonToATMOption));
		payFriend.addActionListener(new PayFriendListener(panel, backPanel, backButtonToATMOption));
		
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
		
		backPanel.add(backButton);
	}
	
	private class BackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Back to PIN page");
			
			panel.removeAll();
			backPanel.removeAll();
			app.getATM().setUpATMScreen();
			panel.updateUI();
			backPanel.updateUI();
		}
	}
	
	private class BackButtonToATMOptionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the nested back button :D");
						
			panel.removeAll();
			backPanel.removeAll();
			setUpATMOptions();
			panel.updateUI();
			backPanel.updateUI();
		}
	}
	    
}

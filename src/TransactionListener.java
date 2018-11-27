import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TransactionListener implements ActionListener {

	JPanel panel;
	JButton backButton;
	
	TransactionListener(JPanel incomingPanel, JButton incomingButton) {
		panel = incomingPanel;
		backButton = incomingButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("transaction clicked");
		
		// what to do about this...
		BankTellerUtility.setUpBackPanelToBankTeller(panel, backButton);
		
		panel.removeAll();
		
		// create panel that asks for account number, and check amount

		
		JButton trans = new JButton("YAY MADE IT");
		panel.add(trans);
		
		
		panel.updateUI();
	}
	
}
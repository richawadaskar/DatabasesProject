package ATM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PurchaseListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	PurchaseListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Deposit clicked");
		
		ATMOptionUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
	}
}

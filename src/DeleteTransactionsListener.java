import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
		// TODO Auto-generated method stub
		System.out.println("deleting transactions clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		panel.removeAll();
		JButton trans = new JButton("YAY MADE IT");
		panel.add(trans);
		panel.updateUI();

	}
	
}
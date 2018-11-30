package ATM;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ATMOptionUtility {

	public static void setUpBackPanelToBankTeller(JPanel backPanel, JButton backButton){
		backPanel.removeAll();
		backPanel.add(backButton);
		backPanel.updateUI();
	}
	
}

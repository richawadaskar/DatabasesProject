package ATM;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ATMOption {

	JButton deposit;
	JButton topUp;
	JButton withdrawal;
	JButton purchase;
	JButton transfer;
	JButton collect;
	JButton wire;
	JButton payFriend;
	JPanel panel;
	ATM atm;
	
	ATMOption(JFrame frame, JPanel panel){
		frame.setTitle("ATM Options");
		this.panel = panel;
		panel.removeAll();
		
		setUpATMOptions();

		frame.getContentPane().add(BorderLayout.CENTER, panel); 

		frame.setVisible(true);
	}

	public void setUpATMOptions() {
		
	}
	    
}

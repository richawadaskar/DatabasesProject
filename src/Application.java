import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Application {
	
	JButton ATM;
	JButton BankTeller;
	JPanel panel;
	JFrame frame;
	
	Application() {
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.setUpUI();
	}
	
	public void setUpUI() {
		frame = new JFrame("ATM or Bank Teller");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(900,300);
	    frame.setLocationRelativeTo(null);
	    
	    //initialize all buttons 
	    ATM = new JButton("ATM");
	    BankTeller = new JButton("Bank Teller");
	    
	    // dd action listeners for buttons
	    ATM.addActionListener(new ATMBtnClicked());
	    BankTeller.addActionListener(new BankTellerBtnClicked());
	    
	    //add buttons to grid
	    panel = new JPanel(new GridLayout(1,2));
		panel.add(ATM);
		panel.add(BankTeller);
		
		frame.getContentPane().add(panel); // Adds Button to content pane of frame
	    frame.setVisible(true);
	    
	}
	private class ATMBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("ATM clicked");
            goToATM();
        }
        public void goToATM() {
        	ATM atm = new ATM(frame, panel);
        	
        }

    }
	
	private class BankTellerBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Bank Teller clicked");
            goToBankTeller();
        }
        public void goToBankTeller() {
        	BankTeller bt = new BankTeller();
        }

    }
}

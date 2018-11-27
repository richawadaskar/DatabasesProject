import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ATM {

    JLabel pin;
    JPasswordField pinField;
    JButton OKButton;
	JButton backButton;
	JPanel backPanel;
	JPanel panel;
	JPanel appPanel;
	JFrame frame;
	Application app;

    public ATM(JFrame frame, JPanel appPanel) {
    	app = new Application();
    	this.frame = frame;
    	this.appPanel = appPanel;
    	appPanel.removeAll();
    	
    	setUpATMScreen();
        
    	frame.getContentPane().add(BorderLayout.NORTH, backPanel);
        frame.getContentPane().add(BorderLayout.CENTER, panel); 

        frame.setVisible(true);
    }
    
    public void setUpATMScreen() {
    	pin = new JLabel("Enter your PIN:");
    	pinField = new JPasswordField(4);
    	OKButton = new JButton("OK");
        OKButton.addActionListener(new OKBtnClicked());
        backButton = new JButton("Back");
        backButton.addActionListener(new BackBtnClicked());
       
        panel = new JPanel();
 	    backPanel = new JPanel();
 	    
        panel.add(pin);
        panel.add(pinField);
        panel.add(OKButton);
        backPanel.add(backButton);
    	
    }

    private class BackBtnClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the first back button");
			
			panel.removeAll();
			backPanel.removeAll();
			app.setUpUI();
		}
	}
    
    private class OKBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentials(pinField.getPassword().toString());
        }

    }

    public void checkCredentials(String pin) {
        //Check if username exists. If exists, check if PIN matches
    }

    public static void main(String[] args) {
        
    }
}

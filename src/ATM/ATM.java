package ATM;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import DebtsRus.Application;

public class ATM {

    JLabel pin;
    JPasswordField pinField;
    JButton OKButton;
	JButton backButton;
	JPanel backPanel;
	JPanel panel;
	JLabel wrongPIN;
	JFrame frame;
	Application app;
	
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";
    static final String USERNAME = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

    public ATM(JFrame frame, JPanel panel, Application appl) {
    	app = appl;
    	this.frame = frame;
    	this.frame.setTitle("ATM");
    	this.panel = panel;
    	this.panel.removeAll();
    	
    	setUpATMScreen();
    }
    
    public void setUpATMScreen() {
    	backPanel = new JPanel();
 	    panel.setLayout(new FlowLayout());
 	    
    	pin = new JLabel("Enter your PIN:");
    	wrongPIN = new JLabel("WRONG PIN. TRY AGAIN");
    	pinField = new JPasswordField(4);
    	OKButton = new JButton("OK");
        OKButton.addActionListener(new OKBtnClicked());
        backButton = new JButton("Back");
        backButton.addActionListener(new BackBtnClicked());
 	    
        panel.add(pin);
        panel.add(pinField);
        panel.add(OKButton);
        backPanel.add(backButton);

		panel.updateUI();

		frame.getContentPane().add(BorderLayout.NORTH, backPanel);
		frame.getContentPane().add(BorderLayout.CENTER, panel);

		frame.setVisible(true);
    }

    private class BackBtnClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("In the first back button");
			
			panel.removeAll();
			backPanel.removeAll();
			panel.updateUI();
			backPanel.updateUI();
			app.updateUI();

		}
	}
    
    private class OKBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentials(new String(pinField.getPassword()));
        }

    }

    public void checkCredentials(String pinn){
        //Check if PIN exists
    	String sql3 = "SELECT * FROM CR_CUSTOMER";
    	try {
   	    	ResultSet result3 = app.stmt.executeQuery(sql3);
	    	while(result3.next()) {
	    		String ssn = result3.getString("ssn");
	    		String name = result3.getString("name");
	    		String address = result3.getString("address");
	    		String pin = result3.getString("pin");
	    		System.out.print("ssn: " + ssn);
	    		System.out.print(", name: "+ name);
	    		System.out.print(", address: "+ address);
	    		System.out.println(", pin: " + pin );
	    		System.out.println();
	    	}
	    	result3.close();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
            
    	if(ATMOptionUtility.checkCredentials(pinn)) {
			ATMOption atmo = new ATMOption(app, frame, panel, backPanel, Integer.parseInt(pinn));
		} else {
			System.out.println("PIN does not exist.");
			JOptionPane.showMessageDialog(frame, "PIN does not exist.");
			//panel.add(wrongPIN);
			panel.updateUI();
			pinField.cut();
		}
    }

    
    
}

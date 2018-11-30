import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

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
        
    	this.frame.getContentPane().add(BorderLayout.NORTH, backPanel);
        this.frame.getContentPane().add(BorderLayout.CENTER, this.panel); 

        this.frame.setVisible(true);
    }
    
    public void setUpATMScreen() {
    	pin = new JLabel("Enter your PIN:");
    	wrongPIN = new JLabel("WRONG PIN. TRY AGAIN");
    	pinField = new JPasswordField(4);
    	OKButton = new JButton("OK");
        OKButton.addActionListener(new OKBtnClicked());
        backButton = new JButton("Back");
        backButton.addActionListener(new BackBtnClicked());
       
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
			app.updateUI();
			panel.updateUI();
		}
	}
    
    private class OKBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkCredentials(new String(pinField.getPassword()));
        }

    }

    public void checkCredentials(String pinn) {
        //Check if PIN exists
    	Connection conn = null;
    	Statement stmt = null;
    	try {
    		Class.forName(JDBC_DRIVER);
    		conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    	    stmt = conn.createStatement();
    	    
    	    String sql3 = String.format("SELECT * FROM %sCUSTOMERS", USERNAME);
            ResultSet result3 = stmt.executeQuery(sql3);
            while(result3.next()) {
                String taxId = result3.getString("taxId");
                String name = result3.getString("name");
                String address = result3.getString("address");
                String pin = result3.getString("pin");
                System.out.print("taxId: " + taxId);
                System.out.print(", name: "+ name);
                System.out.print(", address: "+ address);
                System.out.println(", pin: " + pin );
                System.out.println();
            }
            result3.close();
            
    	    String sql2 = String.format("SELECT * FROM %sCUSTOMERS WHERE PIN = %s", USERNAME, pinn);
            ResultSet tables1 = stmt.executeQuery(sql2);
            if(tables1.next()){
                System.out.println(tables1.getString("pin"));
                ATMOption atmo = new ATMOption(frame, panel);
                atmo.setUpATMOptions();
            } else {
            	System.out.println("PIN does not exist.");
            	panel.add(wrongPIN);
            	panel.updateUI();
            	pinField.cut();
            }
            
    	}catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    	
    }

    
    
}

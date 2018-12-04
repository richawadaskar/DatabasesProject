package DebtsRus;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import BankTellerFunctions.*;
import ATM.*;

import javax.swing.*;

public class Application {
	
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";
    static final String USERNAME = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

	static JButton ATM;
	static JButton BankTeller;
	static JPanel panel;
	static JFrame frame;
	static Connection conn = null;
	public static Statement stmt = null;
	
	static Application app = null;
	public ATM atm;
	
	public static void main(String[] args) {
		app = new Application();
		
		app.setUpDatabaseConnection();
		
		app.setUpUI();
		
		try {

            BufferedReader file = new BufferedReader(new FileReader("/cs/student/cindylu/cs174A/DatabasesProject/users.csv"));
            
            String line = file.readLine();
            
            while(!line.equals("")) {
            	String[] lineParts = line.split(",");
            	for(int i = 0; i < lineParts.length; i++) {
            		System.out.print(lineParts[i] + ",");
            	}
            	int ssn = Integer.parseInt(lineParts[0]);
            	if(!BankTellerUtility.existsCustomer(ssn)) {
            		int pin = Integer.parseInt(lineParts[3]);
                	ATMOptionUtility.addToCustomersTable(ssn, lineParts[1], lineParts[2], pin);
            	}
            	System.out.println();
            	line = file.readLine();
            }
            
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setUpDatabaseConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(true);
            
            stmt = conn.createStatement();
            
        } catch(Exception e){
            e.printStackTrace();
        }
//        } finally{
//            try{
//                if(stmt!=null)
//                    conn.close();
//            }catch(SQLException se){
//            }
//            try{
//                if(conn!=null)
//                    conn.close();
//            }catch(SQLException se){
//                se.printStackTrace();
//            }
//        }
	}
	
	public void setUpUI() {
		frame = new JFrame("Choose your interface");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(900,300);
	    frame.setLocationRelativeTo(null);
	    
	    //initialize all buttons 
	    ATM = new JButton("ATM");
	    BankTeller = new JButton("Bank Teller");
	    
	    // add action listeners for buttons
	    ATM.addActionListener(new ATMBtnClicked());
	    BankTeller.addActionListener(new BankTellerBtnClicked());
	    
	    panel = new JPanel();
	    updateUI();
	
		frame.getContentPane().add(panel); // Adds Button to content pane of frame
	    frame.setVisible(true);
	    
	}
	
	public void updateUI() {
		// add buttons to grid
		panel.setLayout(new GridLayout(1,2));
		panel.add(ATM);
		panel.add(BankTeller);
	}
	
	public ATM getATM() { return atm; }
	
	private class ATMBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("ATM clicked");
            goToATM();
        }
        public void goToATM() {
        	atm = new ATM(frame, panel, app);
        }

    }
	
	private class BankTellerBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Bank Teller clicked");
            goToBankTeller();
        }
        public void goToBankTeller() {
        	BankTeller bt = new BankTeller(app, panel, frame);
        }

    }
}

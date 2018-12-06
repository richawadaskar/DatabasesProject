package DebtsRus;
import java.awt.*;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import BankTellerFunctions.*;
import ATM.*;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Application {
	
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@cloud-34-133.eci.ucsb.edu:1521:XE";
    static final String USERNAME = System.getenv("USERNAME");
    static final String PASSWORD = System.getenv("PASSWORD");

	static JButton ATM;
	static JButton BankTeller;
	static JPanel panel;
	static JFrame frame;
	static JDatePickerImpl datePicker;
	static UtilDateModel model;
	static Properties p;
	static Connection conn = null;
	public static Statement stmt = null;
	
	static Application app = null;
	public ATM atm;
	
	//static public Date date = new Date();
	static public Date date = new Date(12-04-2018);
	 
	public static void main(String[] args) throws SQLException, IOException {
		app = new Application();
		
		app.setUpDatabaseConnection();
		
		app.setUpUI();
	
		//ATMOptionUtility.insertIntoCustomerTable();
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
	    
	    model = new UtilDateModel();
	    p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	    //datePicker.setBounds(110, 100, 200, 25);
	    model.setSelected(true);
	    datePicker.setVisible(true);
	    datePicker.setName("AHHAHAHHAH");
	    
	    panel = new JPanel();
	    updateUI();
	
	    //frame.getContentPane().add(BorderLayout.NORTH, datePicker);
		frame.getContentPane().add(BorderLayout.CENTER, panel); // Adds Button to content pane of frame
	    frame.setVisible(true);
	    
	}
	
	public void updateUI() {
		// add buttons to grid
		panel.setLayout(new FlowLayout());
		panel.add(datePicker);
		panel.add(ATM);
		panel.add(BankTeller);
		//frame.getContentPane().add(BorderLayout.NORTH, datePicker);
	}
	
	public static void getDateFromPicker() {
	    Date selectedDate = (Date) datePicker.getModel().getValue();
	    setDate(selectedDate);
	}
	
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		return sdf.format(date);
	}
	
	// TODO: Add button to main page of application that lets you change the date
	public static void setDate(Date newDate) {
		Application.date = newDate;
		
		if(lastDayOfMonth(newDate)) {
			// accrue interest.  aka call addinterestlistener     dfvsdfsjd
			
		}
	}
	
	public static boolean lastDayOfMonth(Date dateToCheck) {
		return false;
	}
	
	public ATM getATM() { return atm; }
	
	private class ATMBtnClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("ATM clicked");
            getDateFromPicker();
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
            getDateFromPicker();
            goToBankTeller();
        }
        public void goToBankTeller() {
        	BankTeller bt = new BankTeller(app, panel, frame);
        }

    }
}

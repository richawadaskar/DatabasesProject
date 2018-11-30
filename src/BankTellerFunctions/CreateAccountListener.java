package BankTellerFunctions;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class CreateAccountListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	JTextField accountType;
	String typeAccount;
	int primaryOwner;
	ArrayList<Integer> otherOwners;
	int initialDeposit;
	JComboBox typeList;
	JTextField depositAmount; 
	JButton next;
	JButton addOwner;
	JButton addNewCustomer;
	JLabel ongoing;
	JLabel ownerLabel;
	
	String[] accountTypes = {"Checking: Student", "Checking: Interest", "Savings"};
	
	CreateAccountListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("create accounts clicked");
		
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		
		primaryOwner = -1;
		
		panel.removeAll();
		
		setUpCreateAccountUI();
		
		otherOwners = new ArrayList<Integer>();
		
		panel.updateUI();
	}
	
	public void addOwners() {
		ownerLabel = new JLabel("Owner: ");
		
		addOwner = new JButton("Add Owner");
		addOwner.addActionListener(new AddOwnerListener());
		
		next = new JButton("next");
		next.addActionListener(new NextListener());
						
		addOwnersUI();	
	}
	
	public void addOwnersUI() {
		panel.removeAll();
		
		panel.add(ownerLabel);
		panel.add(addOwner);
		panel.add(next);
		
		String currOwners = "";
		if(primaryOwner >= 0) {
			currOwners += primaryOwner;
			for(int i: otherOwners) {
				currOwners = currOwners + ", " + i;
			}
		}
		
		ongoing = new JLabel(currOwners);

		panel.add(ongoing);
		
		panel.updateUI();
	}
	
	public void setUpCreateAccountUI() {
		panel.setLayout(new FlowLayout());
		
		typeList = new JComboBox(accountTypes);
		JLabel type = new JLabel("Select Account Type: ");
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new EnterListener());
		
		panel.add(type);
		panel.add(typeList);
		panel.add(enter);
	}
	
	public void initialDepositScreen() {
		JLabel depositAmountLabel = new JLabel("Enter amount to deposit");
		depositAmount = new JTextField(20);
	}
	
	private class AddOwnerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object[] options = {"Yes existing Customer",
                    "No, it's a new customer"};
			int n = JOptionPane.showOptionDialog(BankTeller.frame,
			    "Is the owner already a customer in our bank?",
			    "Customer Info",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
			
			System.out.println(n);
			
			if(n == 1) {
				panel.removeAll();
				newBankCustomerUI();

				panel.updateUI();
				
				System.out.println("why is the ui nNIFOASHDFKLot updating...");
				
			} else {
				int inputSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's ssn: "));
				
				try {
					if(BankTellerUtility.existsCustomer(inputSSN)) {
						if(primaryOwner == -1) {
							primaryOwner = inputSSN;
						} else {
							otherOwners.add(inputSSN);
						}
						
						panel.removeAll();
						addOwnersUI();
												
						panel.repaint();

					// don't add any info to database until the final "finish" button is clicked.
					} else {
						// invalid customer... you suck.
						BankTellerUtility.showPopUpMessage("Bish that was an invalid customer....");
					}
				} catch (SQLException ee) {
					BankTellerUtility.showPopUpMessage("Invalid inputs were entered.");
					ee.printStackTrace();
				}
				
			}
		}
		
		public void newBankCustomerUI() {
			panel.setLayout(new GridBagLayout());
			
			int enterSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter new Customer's ssn: "));
			String enterName = JOptionPane.showInputDialog(BankTeller.frame, "Enter Customer Name: ");
			String address = JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's address: ");
			String pin = JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's pin: ");
			
			JButton enter = new JButton("Enter");
			enter.addActionListener(new newCustomerAddedListener());
			
			if(primaryOwner == -1) {
				primaryOwner = enterSSN;
			} else {
				otherOwners.add(enterSSN);
			}
			// store new customers such that you can add to the customers table.
			
			panel.add(enter);
		}
	}
	
	private class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			typeAccount = typeList.getSelectedItem().toString();
			panel.removeAll();
			
			addOwners();
		}
	}
	
	private class newCustomerAddedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addOwners();
		}
	}

	
	private class NextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(primaryOwner == -1) {
				// error message, ask them to add at least 1 owner.
				BankTellerUtility.showPopUpMessage("You must add at least 1 owner for the account");
			} else {
				// ask where to deposit in money from. 
				initialDepositScreen();
			}
		}
	}
}
package BankTellerFunctions;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ATM.ATMOptionUtility;
import DebtsRus.Application;

public class CreateAccountListener implements ActionListener {

	JPanel backPanel;
	JPanel panel;
	JButton backButton;
	
	JTextField accountType;
	String typeAccount;
	ArrayList<Integer> owners;
	ArrayList<Integer> newOwners;
	int initialDeposit;
	JComboBox typeList;
	JTextField depositAmount; 
	JButton next;
	JButton addOwner;
	JButton addNewCustomer;
	JLabel ongoing;
	JLabel ownerLabel;
	JComboBox accountLinked;
	JTextField topUpAmount;
		
	String[] accountTypes = {"Checking: Student", "Checking: Interest", "Savings", "Pocket"};

	CreateAccountListener(JPanel incomingPanel, JPanel incomingBackPanel, JButton incomingButton) {
		backPanel = incomingBackPanel;
		backButton = incomingButton;
		panel = incomingPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("create accounts clicked");
		BankTellerUtility.setUpBackPanelToBankTeller(backPanel, backButton);
		panel.removeAll();
		setUpCreateAccountUI();
		owners = new ArrayList<Integer>();
		newOwners = new ArrayList<Integer>();
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
		boolean first = true;
		for(int i: owners) {
			if(first) {
				currOwners += "\r\nPrimary owner: " + i + "\nOther owners: ";
				first = false;
			} else {
				currOwners += i + ", ";
			}
		}
		if(owners.size() > 1) currOwners = currOwners.substring(0, currOwners.length() - 2);
		
		//TODO: figure out how to display the list of current owners
		ongoing = new JLabel(currOwners);

		panel.add(ongoing);
		
		panel.updateUI();
	}
	
	public void initialDepositScreen() {
		JLabel depositAmountLabel = new JLabel("Enter amount to deposit: ");
		depositAmount = new JTextField(20);
		panel.add(depositAmountLabel);
		panel.add(depositAmount);
		JButton deposit = new JButton("Deposit");
		deposit.addActionListener(new DepositListener());
		panel.add(deposit);
		panel.updateUI();
	}
	
	public void pocketTopUpScreen() {
		List<Integer> accountsToLink = ATMOptionUtility.findAllCheckingSavingAccountNumbers(owners.get(0));
		
		JLabel accountToLink = new JLabel("Select Account to Link to: ");
		accountLinked = new JComboBox(accountsToLink.toArray());

		JLabel topUpAmountLabel = new JLabel("Enter Amount to Top Up: ");
		topUpAmount = new JTextField(20);
		
		panel.add(accountToLink);
		panel.add(accountLinked);
		panel.add(topUpAmountLabel);
		panel.add(topUpAmount);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(new TopUpEnterListener());
		panel.add(enter);
		
		panel.updateUI();
		System.out.println("Performing top up.");
	}
	
	private class TopUpEnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			float amount = Integer.parseInt(topUpAmount.getText());
			int topUpAccountId;
			try {
				topUpAccountId = BankTellerUtility.generateAccountID() + 1;
				if(amount <= 0) {
					BankTellerUtility.showPopUpMessage("Amount being deposited must be a positive number.");
				} else {
					int accountIdSelected = Integer.parseInt(accountLinked.getSelectedItem().toString());
					if (ATMOptionUtility.checkEnoughBalance(accountIdSelected, amount)) {
						ATMOptionUtility.subtractMoneyToAccountId(accountIdSelected, amount);
						ATMOptionUtility.addToAccountsTable(topUpAccountId, owners.get(0), "SB", 0, amount, amount, 0, "Pocket");
						ATMOptionUtility.addToPocketAccountTable(topUpAccountId, accountIdSelected);
						ATMOptionUtility.addToTransactionsTable("Top-up", owners.get(0), topUpAccountId, accountIdSelected, amount);
						BankTellerUtility.showPopUpMessage("Top-up succeeded.");
						BankTellerUtility.addOwnersIntoOwnedByTable(owners, topUpAccountId);
					} else {
						BankTellerUtility.showPopUpMessage("You don't have enough money in your linked account to make this transaction.");
					}
			}
			} catch (SQLException e) {
				BankTellerUtility.showPopUpMessage("Unfortunately an error occurred. Please try again later.");
				e.printStackTrace();
			}
		}
	}
	
	private class DepositListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				float amount = Integer.parseInt(depositAmount.getText());
				int accountId = BankTellerUtility.generateAccountID() + 1;
				System.out.println(accountId);
				System.out.println(amount);
				assert(amount > 0);
				
				float interestRate = (float) BankTellerUtility.getInterestRate(typeAccount);
				int primary = owners.get(0);
				
				ATMOptionUtility.addToAccountsTable(accountId, primary, "Santa Barbara", interestRate, amount, amount, 0, typeAccount);
				ATMOptionUtility.addToTransactionsTable("Deposit", owners.get(0), accountId, amount);
				
				BankTellerUtility.showPopUpMessage("New account was successfully created.");
				BankTellerUtility.addOwnersIntoOwnedByTable(owners, accountId);
			} catch (SQLException e1) {
				BankTellerUtility.showPopUpMessage("Invalid inputs were entered into amount field.");
				e1.printStackTrace();
			} catch (Exception ee) {
				BankTellerUtility.showPopUpMessage("Amount entered must be positive.");
			}
		}
	}
	
	private class AddOwnerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
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
			if(n == 1) {
				panel.removeAll();
				newBankCustomerUI();
				panel.updateUI();
			} else {
				int inputSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's ssn: "));
				try {
					if(BankTellerUtility.existsCustomer(inputSSN)) {
						owners.add(inputSSN);
						panel.removeAll();
						addOwnersUI();		
						panel.updateUI();
					} else {
						BankTellerUtility.showPopUpMessage("The SSN you entered isn't a customer of our bank yet.");
					}
				} catch (SQLException ee) {
					BankTellerUtility.showPopUpMessage("Invalid inputs were entered.");
				}
				panel.removeAll();
				addOwnersUI();
				panel.updateUI();
			}
		}
		
		public void newBankCustomerUI() {
			panel.setLayout(new GridBagLayout());
			
			try {
				int enterSSN = Integer.parseInt(JOptionPane.showInputDialog(BankTeller.frame, "Enter new Customer's ssn: "));
				String enterName = JOptionPane.showInputDialog(BankTeller.frame, "Enter Customer Name: ");
				String address = JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's address: ");
				
				// CHECK THAT PIN IS UNIQUE LATER!!!
				String pin = JOptionPane.showInputDialog(BankTeller.frame, "Enter Owner's pin: ");
				
				owners.add(enterSSN);
				newOwners.add(enterSSN);
				
				BankTellerUtility.addToCustomersTable(enterSSN, enterName, address, pin);
			
				// TODO: delete new customers when exceptions occur or the account isn't created.... FUCK
				
			} catch (Exception e) {
				e.printStackTrace();
				BankTellerUtility.showPopUpMessage("Invalid inputs were entered. Please try again.");
			}
			panel.removeAll();
			addOwnersUI();
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
			if(owners.size() == 0) {
				// error message, ask them to add at least 1 owner.
				BankTellerUtility.showPopUpMessage("You must add at least 1 owner for the account");
			} else {
				if(typeAccount == "Pocket") {
					panel.removeAll();
					pocketTopUpScreen();
				} else {
					panel.removeAll();
					initialDepositScreen();
				}
			}
		}
	}
}
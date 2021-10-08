package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class OrderMenuItemPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private JButton orderButton, viewMenuButton;
	private JLabel errorLabel, menuItemLabel, quantityLabel, seatsLabel;
	private JTextField menuItemField, quantityField, seatsField;

	private String errorMessage = "";

	public OrderMenuItemPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		orderButton = new JButton();
		viewMenuButton = new JButton();
		errorLabel = new JLabel();
		menuItemLabel = new JLabel();
		quantityLabel = new JLabel();
		seatsLabel = new JLabel();
		menuItemField = new JTextField();
		quantityField = new JTextField();
		seatsField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Order Menu Item");

		// Set text
		orderButton.setText("Place order !");
		viewMenuButton.setText("View numbered list of menu items");
		menuItemLabel.setText("Number of the menu item (see numbered menu using the button on the right) :");
		quantityLabel.setText("Order quantity : ");
		seatsLabel.setText(seatsLabelMessage());
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(menuItemLabel)
						.addComponent(quantityLabel).addComponent(seatsLabel))
				.addGroup(layout.createParallelGroup().addComponent(viewMenuButton).addComponent(menuItemField)
						.addComponent(quantityField).addComponent(seatsField).addComponent(orderButton)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(viewMenuButton))
				.addGroup(layout.createParallelGroup().addComponent(menuItemLabel).addComponent(menuItemField))
				.addGroup(layout.createParallelGroup().addComponent(quantityLabel).addComponent(quantityField))
				.addGroup(layout.createParallelGroup().addComponent(seatsLabel).addComponent(seatsField))
				.addComponent(orderButton));

		viewMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewMenuButtonActionPerformed();
			}
		});

		orderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				orderButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		menuItemField.setText("");
		quantityField.setText("");
		seatsField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void orderButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int menuItemNumber = Integer.parseInt(menuItemField.getText());
			String menuItemName = "";
			try {
				menuItemName = getNameFromNumber(menuItemNumber);
			} catch (InvalidInputException e) {
				errorMessage = e.getMessage();
			}
			int quantity = Integer.parseInt(quantityField.getText());
			String seatNumbers = seatsField.getText();
			List<Seat> seats = null;
			try {
				seats = getSeatsFromNumbers(seatNumbers);
			} catch (InvalidInputException e) {
				errorMessage = e.getMessage();
			}
			rac.orderMenuItem(menuItemName, quantity, seats);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	private static String seatsLabelMessage() {
		String returned = "<html>Please enter the table and seats numbers for this order as follows<br>";
		returned += ("1,1,2,3-5,4<br>");
		returned += ("This example means order the item and share it<br> between seats 1, 2, 3 of table 1 and seat 4 of table 5.</html>");
		return returned;
	}

	private String getNameFromNumber(int number) throws InvalidInputException {
		try {
			String name = resto.getMenu().getMenuItems().get(number).getName();
			return name;
		} catch (Exception e) {
			throw new InvalidInputException("Error : invalid menu item number");
		}
	}

	private List<Seat> getSeatsFromNumbers(String seatNumbers) throws InvalidInputException {
		try {
			List<Seat> returnedSeats = new ArrayList<Seat>();
			String[] tableData = seatNumbers.split("-");
			for (String str : tableData) {
				String[] seatData = str.split(",");
				Table table = Table.getWithNumber(Integer.parseInt(seatData[0]));
				for (int i = 1; i < seatData.length; i++) {
					Seat seat = table.getSeat(Integer.parseInt(seatData[i]));
					returnedSeats.add(seat);
				}
			}
			return returnedSeats;
		} catch (Exception e) {
			throw new InvalidInputException("Error : the inputted seats/tables are invalid !");
		}
	}

	private void viewMenuButtonActionPerformed() {
		String listOfMenuItems = "<html>";
		int counter = 0;
		List<MenuItem> menuItems = resto.getMenu().getMenuItems();
		for (MenuItem mI : menuItems) {
			if (mI.hasCurrentPricedMenuItem()) {
				listOfMenuItems += ("" + counter + " : " + mI.getName() + "&emsp;&emsp;");
				counter++;
				if (counter % 2 == 0) {
					listOfMenuItems += "<br>";
				}
			}
		}
		listOfMenuItems += "</html>";
		NumberedMenuPage nmp = new NumberedMenuPage(listOfMenuItems);
		nmp.setSize(425, 850);
		nmp.setVisible(true);
	}

}

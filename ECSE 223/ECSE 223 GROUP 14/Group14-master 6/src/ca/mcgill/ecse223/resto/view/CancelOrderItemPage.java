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
import ca.mcgill.ecse223.resto.model.*;

public class CancelOrderItemPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private JButton cancelButton, viewMenuButton, loadOrderItemsButton;
	private JLabel errorLabel, itemNumberLabel;
	private JTextField itemNumberField;

	private List<OrderItem> orderItems;
	private String orderItemList = "";

	private boolean orderItemsLoaded = false;

	private String errorMessage = "";

	public CancelOrderItemPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		cancelButton = new JButton();
		viewMenuButton = new JButton();
		loadOrderItemsButton = new JButton();
		errorLabel = new JLabel();
		itemNumberLabel = new JLabel();
		itemNumberField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Cancel Order Item");

		// Set text
		cancelButton.setText("Cancel order item!");
		viewMenuButton.setText("View numbered list of menu items");
		loadOrderItemsButton.setText("Load Order Items");
		itemNumberLabel.setText(itemNumberLabelMessage());
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(itemNumberLabel)
						.addComponent(viewMenuButton))
				.addGroup(layout.createParallelGroup().addComponent(itemNumberField).addComponent(loadOrderItemsButton))
				.addGroup(layout.createParallelGroup().addComponent(cancelButton)));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(itemNumberLabel).addComponent(itemNumberField))
				.addGroup(layout.createParallelGroup().addComponent(viewMenuButton).addComponent(loadOrderItemsButton)
						.addComponent(cancelButton)));

		loadOrderItemsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadOrderItemsButtonActionPerformed();
			}
		});

		viewMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				viewMenuButtonActionPerformed();
			}
		});

		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		itemNumberField.setText("");
		itemNumberLabel.setText(itemNumberLabelMessage());
		errorLabel.setText(errorMessage);
	}

	private void cancelButtonActionPerformed() {
		if (orderItemsLoaded) {
			try {
				RestoAppController rac = RestoAppApplication.getControllerInstance();
				int orderItemNumber = Integer.parseInt(itemNumberField.getText());
				OrderItem oI = orderItems.get(orderItemNumber);
				rac.cancelOrderItem(oI);
			} catch (InvalidInputException | NullPointerException e) {
				errorMessage = "Invalid number";
			}
			refreshData();
		} else {
			errorMessage = "Error : Choose an item number first to load the order items.";
			refreshData();
			return;
		}
	}

	private void loadOrderItemsButtonActionPerformed() {
		try {
			int menuItemNumber = Integer.parseInt(itemNumberField.getText());
			String menuItemName = "";
			try {
				menuItemName = getNameFromNumber(menuItemNumber);
			} catch (InvalidInputException e) {
				errorMessage = e.getMessage();
			}
			if (!menuItemName.equals("")) {
				MenuItem mI = null;
				for (MenuItem menuItem : resto.getMenu().getMenuItems()) {
					if (menuItem.getName().equals(menuItemName)) {
						mI = menuItem;
					}
				}
				if (mI != null) {
					PricedMenuItem cpmi = mI.getCurrentPricedMenuItem();
					List<OrderItem> foundOrderItems = cpmi.getOrderItems();
					int counter = 0;
					for (OrderItem orderItem : foundOrderItems) {
						orderItemList += ("" + counter + " : " + seatsToString(orderItem.getSeats()) + "br>");
						counter++;
					}
					orderItems = foundOrderItems;
				}
			}
			orderItemsLoaded = true;
			errorMessage = "";
		} catch (Exception e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	private String getNameFromNumber(int number) throws InvalidInputException {
		try {
			List<MenuItem> searchList = new ArrayList<MenuItem>();
			for (MenuItem mI : resto.getMenu().getMenuItems()) {
				if (mI.hasCurrentPricedMenuItem()) {
					searchList.add(mI);
				}
			}
			String name = searchList.get(number).getName();
			return name;
		} catch (Exception e) {
			throw new InvalidInputException("Error : invalid menu item number");
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

	private String seatsToString(List<Seat> seats) {
		String returned = "";
		try {
			List<Integer> foundTables = new ArrayList<Integer>();
			List<String> tables = new ArrayList<String>();
			for (Seat seat : seats) {
				if (!foundTables.contains(seat.getTable().getNumber())) {
					foundTables.add(seat.getTable().getNumber());
					String newTable = "" + seat.getTable().getNumber() + ",";
					tables.add(newTable);
				} else {
					int index = foundTables.indexOf(seat.getTable().getNumber());
					String addToTable = tables.get(index);
					addToTable += (seat.getTable().getSeats().indexOf(seat) + ",");
					tables.remove(index);
					foundTables.remove(index);
					tables.add(addToTable);
					foundTables.add(seat.getTable().getNumber());
				}
			}
			returned = "<html>";
			for (String table : tables) {
				returned += (table + "<br>");
			}
			returned += "</html>";
		} catch (Exception e) {
			returned = "";
		}
		return returned;

	}

	private String itemNumberLabelMessage() {

		String returned = "<html>";
		if (!orderItemsLoaded) {
			returned += "To cancel an order item, we first need to know the corresponding menu item.<br>";
			returned += "Therefore, please open the menu using the button below and find the number <br>";
			returned += "of the menu item you wish to cancel, enter it, then click the load button.<br>";
		} else {
			returned += "Thank you for choosing a menu item number. The following is the numbered list <br>";
			returned += "of orders containing that order item. Please choose which order corresponds to yours<br>";
			returned += "and click the cancel button to proceed.<br>";
			returned += "Note : if you see no list, then no order was made containing that item.";
			returned += (orderItemList + "<br");

		}
		returned += "</html>";
		return returned;
	}

}

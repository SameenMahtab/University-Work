package ca.mcgill.ecse223.resto.view;

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

public class UpdateMenuItemPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton updateMenuItemButton;
	private JLabel errorLabel, itemNameLabel, itemNewNameLabel, itemCategoryLabel, priceLabel;
	private JTextField itemNameField, itemNewNameField, itemCategoryField, priceField;

	private String errorMessage = "";

	public UpdateMenuItemPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		updateMenuItemButton = new JButton();
		errorLabel = new JLabel();
		itemNameLabel = new JLabel();
		itemNewNameLabel = new JLabel();
		itemCategoryLabel = new JLabel();
		priceLabel = new JLabel();
		itemNameField = new JTextField();
		itemNewNameField = new JTextField();
		itemCategoryField = new JTextField();
		priceField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Update Menu Item");

		// Set text
		updateMenuItemButton.setText("Update Menu Item");
		itemNameLabel.setText("Item name?");
		itemNewNameLabel.setText("New name?");
		itemCategoryLabel.setText("<html>New category? Choices are:<br>" + categories());
		priceLabel.setText("New price?");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(itemNameLabel)
						.addComponent(itemNewNameLabel).addComponent(itemCategoryLabel).addComponent(priceLabel))
				.addGroup(layout.createParallelGroup().addComponent(itemNameField).addComponent(itemNewNameField)
						.addComponent(itemCategoryField).addComponent(priceField).addComponent(updateMenuItemButton)));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(itemNameLabel).addComponent(itemNameField))
				.addGroup(layout.createParallelGroup().addComponent(itemNewNameLabel).addComponent(itemNewNameField))
				.addGroup(layout.createParallelGroup().addComponent(itemCategoryLabel).addComponent(itemCategoryField))
				.addGroup(layout.createParallelGroup().addComponent(priceLabel).addComponent(priceField))
				.addComponent(updateMenuItemButton));

		updateMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateMenuItemButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		itemNameField.setText("");
		itemNewNameField.setText("");
		itemCategoryField.setText("");
		priceField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void updateMenuItemButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			String itemName = itemNameField.getText(), itemNewName = itemNewNameField.getText();
			MenuItem.ItemCategory itemCat = stringToCat(itemCategoryField.getText());
			double price = Double.parseDouble(priceField.getText());
			rac.updateMenuItem(itemName, itemNewName, itemCat, price);
			errorMessage = "";
		} catch (InvalidInputException | NullPointerException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	private String categories() {
		String returned = "";
		for (MenuItem.ItemCategory cat : MenuItem.ItemCategory.values()) {
			returned += ("" + cat.toString() + "<br>");
		}
		returned += "</html>";
		return returned;
	}

	private MenuItem.ItemCategory stringToCat(String givenCat) {
		for (MenuItem.ItemCategory cat : MenuItem.ItemCategory.values()) {
			if (cat.toString().equals(givenCat)) {
				return cat;
			}
		}
		return null;
	}
}
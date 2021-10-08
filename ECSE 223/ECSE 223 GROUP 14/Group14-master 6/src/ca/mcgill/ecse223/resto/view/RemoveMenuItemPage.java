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

public class RemoveMenuItemPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton removeMenuItemButton;
	private JLabel errorLabel, menuItemNameLabel;
	private JTextField menuItemNameField;

	private String errorMessage = "";

	public RemoveMenuItemPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		removeMenuItemButton = new JButton();
		errorLabel = new JLabel();
		menuItemNameLabel = new JLabel();
		menuItemNameField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Remove Menu Item");

		// Set text
		removeMenuItemButton.setText("Remove Menu Item");
		menuItemNameLabel.setText("Menu Item Name");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(errorLabel)
						.addComponent(menuItemNameLabel).addComponent(removeMenuItemButton))
				.addComponent(menuItemNameField));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(menuItemNameLabel).addComponent(menuItemNameField))
				.addComponent(removeMenuItemButton));

		removeMenuItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeMenuItemButtonActionPerformed();
			}
		});

	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		menuItemNameField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void removeMenuItemButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			String menuItemName = menuItemNameField.getText();
			rac.removeMenuItem(menuItemName);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

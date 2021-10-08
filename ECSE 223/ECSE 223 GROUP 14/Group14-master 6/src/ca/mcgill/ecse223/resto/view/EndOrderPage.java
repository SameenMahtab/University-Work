package ca.mcgill.ecse223.resto.view;

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
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class EndOrderPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private JButton endOrderButton;
	private JLabel errorLabel, orderLabel;
	private JTextField orderField;

	private String errorMessage = "";

	public EndOrderPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		endOrderButton = new JButton();
		errorLabel = new JLabel();
		orderLabel = new JLabel();
		orderField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("End order");

		// Set text
		endOrderButton.setText("End order");
		String label = "<html>What orders(s)?<br>";
		label += "Existing orders :<br>";
		label += stringOrders();
		label += "</html>";
		orderLabel.setText(label);
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(orderLabel))
				.addComponent(orderField).addComponent(endOrderButton));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel).addGroup(layout
				.createParallelGroup().addComponent(orderLabel).addComponent(orderField).addComponent(endOrderButton)));

		endOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				endOrderButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		orderField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void endOrderButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int chosenOrderNum = Integer.parseInt(orderField.getText());
			rac.endOrder(resto.getCurrentOrder(chosenOrderNum));
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	private String stringOrders() {
		String orders = "";
		for (Order currOrder : resto.getCurrentOrders()) {
			String order = "";
			order += ("Order " + currOrder.getNumber() + ". Tables " + tablesToNumbers(currOrder.getTables()));
			orders += (order + "<br>");
		}
		return orders;
	}

	private String tablesToNumbers(List<Table> list) {
		String returned = "";
		for (Table t : list) {
			returned += (t.getNumber() + ",");
		}
		return returned;
	}

}

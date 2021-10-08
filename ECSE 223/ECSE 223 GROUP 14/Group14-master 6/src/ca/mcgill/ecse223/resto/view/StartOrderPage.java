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
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class StartOrderPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private JButton startOrderButton;
	private JLabel errorLabel, tablesLabel;
	private JTextField tablesField;

	private String errorMessage = "";

	public StartOrderPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		startOrderButton = new JButton();
		errorLabel = new JLabel();
		tablesLabel = new JLabel();
		tablesField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Start order");

		// Set text
		startOrderButton.setText("Start order");
		tablesLabel.setText("<html>What table(s)?<br>Separate them by a comma (no space)</html>");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(tablesLabel))
				.addComponent(tablesField).addComponent(startOrderButton));

		layout.setVerticalGroup(
				layout.createSequentialGroup().addComponent(errorLabel).addGroup(layout.createParallelGroup()
						.addComponent(tablesLabel).addComponent(tablesField).addComponent(startOrderButton)));

		startOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startOrderButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		tablesField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void startOrderButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			String chosenTables = tablesField.getText();
			String[] chosenList = chosenTables.split(",");
			List<Integer> tableNumbers = new ArrayList<Integer>();
			for (String chosen : chosenList) {
				tableNumbers.add(Integer.parseInt(chosen));
			}
			rac.startOrder(tableNumbers);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	

}

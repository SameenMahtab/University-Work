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

public class CreateTablePage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton addTableButton;
	private JLabel errorLabel, tableSizeLabel, tableLocLabel, numSeatsLabel, tableNumLabel;
	private JTextField widthField, heightField, locXField, locYField, numSeatsField, tableNumField;

	private String errorMessage = "";

	public CreateTablePage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		addTableButton = new JButton();
		errorLabel = new JLabel();
		tableSizeLabel = new JLabel();
		tableLocLabel = new JLabel();
		numSeatsLabel = new JLabel();
		tableNumLabel = new JLabel();
		widthField = new JTextField();
		heightField = new JTextField();
		locXField = new JTextField();
		locYField = new JTextField();
		numSeatsField = new JTextField();
		tableNumField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Add Table");

		// Set text
		addTableButton.setText("Add table");
		tableSizeLabel.setText("Table size (width, height):");
		tableLocLabel.setText("Table location (x, y)");
		numSeatsLabel.setText("Number of seats:");
		tableNumLabel.setText("Table number:");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(tableSizeLabel)
						.addComponent(tableLocLabel).addComponent(numSeatsLabel).addComponent(tableNumLabel))
				.addGroup(layout.createParallelGroup().addComponent(widthField).addComponent(locXField)
						.addComponent(numSeatsField).addComponent(tableNumField).addComponent(addTableButton))
				.addGroup(layout.createParallelGroup().addComponent(heightField).addComponent(locYField)));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(tableSizeLabel).addComponent(widthField)
						.addComponent(heightField))
				.addGroup(layout.createParallelGroup().addComponent(tableLocLabel).addComponent(locXField)
						.addComponent(locYField))
				.addGroup(layout.createParallelGroup().addComponent(numSeatsLabel).addComponent(numSeatsField))
				.addGroup(layout.createParallelGroup().addComponent(tableNumLabel).addComponent(tableNumField))
				.addGroup(layout.createParallelGroup().addComponent(addTableButton)));

		addTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addTableButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		widthField.setText("");
		heightField.setText("");
		locXField.setText("");
		locYField.setText("");
		numSeatsField.setText("");
		tableNumField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void addTableButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int tableNumber = Integer.parseInt(tableNumField.getText());
			int width = Integer.parseInt(widthField.getText());
			int height = Integer.parseInt(heightField.getText());
			int locX = Integer.parseInt(locXField.getText());
			int locY = Integer.parseInt(locYField.getText());
			int numSeats = Integer.parseInt(numSeatsField.getText());
			rac.createTable(tableNumber, locX, locY, width, height, numSeats);
			errorMessage = "Success !";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

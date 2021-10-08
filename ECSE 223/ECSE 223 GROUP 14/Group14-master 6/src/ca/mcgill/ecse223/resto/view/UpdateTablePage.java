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

public class UpdateTablePage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton updateTableButton;
	private JLabel errorLabel, currTableNumberLabel, newTableNumberLabel, numSeatsLabel;
	private JTextField currTableNumberField, newTableNumberField, numSeatsField;

	private String errorMessage = "";

	public UpdateTablePage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		updateTableButton = new JButton();
		errorLabel = new JLabel();
		currTableNumberLabel = new JLabel();
		newTableNumberLabel = new JLabel();
		numSeatsLabel = new JLabel();
		currTableNumberField = new JTextField();
		newTableNumberField = new JTextField();
		numSeatsField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Update Table Number");

		// Set text
		updateTableButton.setText("Update");
		currTableNumberLabel.setText("Current Table number");
		newTableNumberLabel.setText("New Table number");
		numSeatsLabel.setText("Number of seats");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(currTableNumberLabel)
						.addComponent(newTableNumberLabel).addComponent(numSeatsLabel).addComponent(updateTableButton))
				.addGroup(layout.createParallelGroup().addComponent(currTableNumberField)
						.addComponent(newTableNumberField).addComponent(numSeatsField)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(currTableNumberLabel)
						.addComponent(currTableNumberField))
				.addGroup(layout.createParallelGroup().addComponent(newTableNumberLabel)
						.addComponent(newTableNumberField))
				.addGroup(layout.createParallelGroup().addComponent(numSeatsLabel).addComponent(numSeatsField))
				.addComponent(updateTableButton));

		updateTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					updateTableButtonActionPerformed();
				} catch (InvalidInputException e) {
				}
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		currTableNumberField.setText("");
		newTableNumberField.setText("");
		numSeatsField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void updateTableButtonActionPerformed() throws InvalidInputException {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int currTableNumber = Integer.parseInt(currTableNumberField.getText());
			int newTableNumber = Integer.parseInt(newTableNumberField.getText());
			int numberOfSeats = Integer.parseInt(numSeatsField.getText());
			rac.updateTable(currTableNumber, newTableNumber, numberOfSeats);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

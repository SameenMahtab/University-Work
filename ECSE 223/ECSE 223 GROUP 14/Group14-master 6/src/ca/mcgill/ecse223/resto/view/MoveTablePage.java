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

public class MoveTablePage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;
	
	private JButton moveTableButton;
	private JLabel errorLabel, tableNumberLabel, coordsLabel;
	private JTextField tableNumberField, xField, yField;

	private String errorMessage = "";

	public MoveTablePage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		moveTableButton = new JButton();
		errorLabel = new JLabel();
		tableNumberLabel = new JLabel();
		coordsLabel = new JLabel();
		tableNumberField = new JTextField();
		xField = new JTextField();
		yField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Move Table");

		// Set text
		moveTableButton.setText("Change");
		tableNumberLabel.setText("Table number");
		coordsLabel.setText("New coordinates? (x, y)");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(tableNumberLabel)
						.addComponent(coordsLabel).addComponent(moveTableButton))
				.addGroup(layout.createParallelGroup().addComponent(tableNumberField)
						.addGroup(layout.createSequentialGroup().addComponent(xField).addComponent(yField))));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(tableNumberLabel).addComponent(tableNumberField))
				.addGroup(layout.createParallelGroup().addComponent(coordsLabel)
						.addGroup(layout.createParallelGroup().addComponent(xField).addComponent(yField)))
				.addComponent(moveTableButton));

		moveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					moveTableButtonActionPerformed();
				} catch (InvalidInputException e) {
				}
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		tableNumberField.setText("");
		xField.setText("");
		yField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void moveTableButtonActionPerformed() throws InvalidInputException {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int tableNumber = Integer.parseInt(tableNumberField.getText());
			int xVal = Integer.parseInt(xField.getText());
			int yVal = Integer.parseInt(yField.getText());
			rac.moveTable(tableNumber, xVal, yVal);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}
}
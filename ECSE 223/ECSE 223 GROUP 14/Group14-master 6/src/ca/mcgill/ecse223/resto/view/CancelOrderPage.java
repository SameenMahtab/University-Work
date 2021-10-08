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

public class CancelOrderPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton cancelOrderButton;
	private JLabel errorLabel, tableNumberLabel;
	private JTextField tableNumberField;

	private String errorMessage = "";

	public CancelOrderPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		cancelOrderButton = new JButton();
		errorLabel = new JLabel();
		tableNumberLabel = new JLabel();
		tableNumberField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Cancel order");

		// Set text
		cancelOrderButton.setText("Cancel order");
		tableNumberLabel.setText("Table number");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(errorLabel)
						.addComponent(tableNumberLabel).addComponent(cancelOrderButton))
				.addComponent(tableNumberField));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(tableNumberLabel).addComponent(tableNumberField))
				.addComponent(cancelOrderButton));

		cancelOrderButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelOrderButtonActionPerformed();
			}
		});

	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		tableNumberField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void cancelOrderButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int tableNumber = Integer.parseInt(tableNumberField.getText());
			rac.cancelOrder(tableNumber);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

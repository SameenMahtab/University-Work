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
import ca.mcgill.ecse223.resto.model.Table;

public class RemoveTablePage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton removeTableButton;
	private JLabel errorLabel, tableNumberLabel;
	private JTextField tableNumberField;

	private String errorMessage = "";

	public RemoveTablePage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		removeTableButton = new JButton();
		errorLabel = new JLabel();
		tableNumberLabel = new JLabel();
		tableNumberField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Remove Table");

		// Set text
		removeTableButton.setText("Remove table");
		tableNumberLabel.setText("Table number");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(errorLabel)
						.addComponent(tableNumberLabel).addComponent(removeTableButton))
				.addComponent(tableNumberField));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(tableNumberLabel).addComponent(tableNumberField))
				.addComponent(removeTableButton));

		removeTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeTableButtonActionPerformed();
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

	private void removeTableButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			int tableNumber = Integer.parseInt(tableNumberField.getText());
			System.out.println(tableNumber);
			boolean tableWasFound = false;
			for (Table t : RestoAppApplication.getRestaurantInstance().getCurrentTables()) {
				if (t.getNumber() == tableNumber) {
					tableWasFound = true;
					System.out.println("Winner number" + t.getNumber());
					rac.removeTable(t.getNumber());
				}
			}
			if (!tableWasFound) {
				errorMessage = "Error : table wasn't found";
				refreshData();
				return;
			}
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

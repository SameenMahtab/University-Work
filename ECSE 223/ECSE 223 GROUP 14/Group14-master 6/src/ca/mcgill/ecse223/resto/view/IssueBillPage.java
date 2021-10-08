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
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class IssueBillPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton issueBillButton;
	private JLabel errorLabel, seatsNumbersLabel;
	private JTextField seatNumbersField;

	private String errorMessage = "";

	public IssueBillPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		issueBillButton = new JButton();
		errorLabel = new JLabel();
		seatsNumbersLabel = new JLabel();
		seatNumbersField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Issue Bill");

		// Set text
		issueBillButton.setText("Issue Bill");
		seatsNumbersLabel.setText("<html>Enter the table and seats along the following template<br>"
				+ "1,1,2,3-4,5<br>means the seats 1,2,3 of table 1 and seat 5 of table 4.<br></html>");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(errorLabel)
						.addComponent(seatsNumbersLabel).addComponent(issueBillButton))
				.addComponent(seatNumbersField));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(seatsNumbersLabel).addComponent(seatNumbersField))
				.addComponent(issueBillButton));

		issueBillButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				issueBillButtonActionPerformed();
			}
		});

	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		seatNumbersField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void issueBillButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			String[] tablesAndSeats = seatNumbersField.getText().split("-");
			List<Seat> foundSeats = new ArrayList<Seat>();
			for (String tableAndSeats : tablesAndSeats) {
				int tableNumber = Integer.parseInt("" + tableAndSeats.charAt(0));
				List<Integer> seatNumbers = new ArrayList<Integer>();
				for (int i = 1; i < tableAndSeats.length(); i++) {
					if (tableAndSeats.charAt(i) != ',') {
						seatNumbers.add(Integer.parseInt("" + tableAndSeats.charAt(i)));
					}
				}
				for (int seatNum : seatNumbers) {
					foundSeats.add(Table.getWithNumber(tableNumber).getSeat(seatNum));
				}
			}
			rac.issueBill(foundSeats);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

}

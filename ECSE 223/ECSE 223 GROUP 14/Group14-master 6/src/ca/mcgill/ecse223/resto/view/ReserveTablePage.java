package ca.mcgill.ecse223.resto.view;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Table;

public class ReserveTablePage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto  = RestoAppApplication.getRestaurantInstance();

	private JButton reserveTableButton;
	private JLabel errorLabel, dateLabel, timeLabel, nameLabel, emailLabel, phoneLabel, partySizeLabel,
			availableTablesLabel, chooseTableLabel;
	private JTextField timeHourField, timeMinuteField, nameField, emailField, phoneField, partySizeField,
			availableTablesField;
	private JDatePicker datePicker;
	private JDatePanelImpl datePanel;

	private String errorMessage = "";

	public ReserveTablePage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		reserveTableButton = new JButton();

		errorLabel = new JLabel();
		dateLabel = new JLabel();
		timeLabel = new JLabel();
		nameLabel = new JLabel();
		emailLabel = new JLabel();
		phoneLabel = new JLabel();
		partySizeLabel = new JLabel();
		availableTablesLabel = new JLabel();
		chooseTableLabel = new JLabel();

		timeHourField = new JTextField();
		timeMinuteField = new JTextField();
		nameField = new JTextField();
		emailField = new JTextField();
		phoneField = new JTextField();
		partySizeField = new JTextField();
		availableTablesField = new JTextField();

		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Reserve Table");

		// Set text
		reserveTableButton.setText("Reserve table");
		dateLabel.setText("Choose a date");
		timeLabel.setText("Enter a time (hours, minutes)");
		nameLabel.setText("Name");
		emailLabel.setText("Email");
		phoneLabel.setText("Phone");
		partySizeLabel.setText("How many people");
		chooseTableLabel.setText(
				"<html>Choose the table(s) for this reservation.<br>Enter them separated by a comma (no space !)</html>");

		String availableTables = "<html>Available Tables:<br>" + stringTables(resto) + "</html>";
		availableTablesLabel.setText(availableTables); // TODO

		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(dateLabel)
						.addComponent(nameLabel).addComponent(phoneLabel))
				.addGroup(layout.createParallelGroup().addComponent(datePanel).addComponent(nameField)
						.addComponent(phoneField).addComponent(availableTablesLabel))
				.addGroup(layout.createParallelGroup().addComponent(timeLabel).addComponent(emailLabel)
						.addComponent(partySizeLabel).addComponent(chooseTableLabel))
				.addGroup(layout.createParallelGroup().addComponent(timeHourField).addComponent(emailField)
						.addComponent(partySizeField).addComponent(availableTablesField))
				.addGroup(layout.createParallelGroup().addComponent(timeMinuteField).addComponent(reserveTableButton)));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup().addComponent(dateLabel).addComponent(datePanel)
						.addComponent(timeLabel).addComponent(timeHourField).addComponent(timeMinuteField))
				.addGroup(layout.createParallelGroup().addComponent(nameLabel).addComponent(nameField)
						.addComponent(emailLabel).addComponent(emailField))
				.addGroup(layout.createParallelGroup().addComponent(phoneLabel).addComponent(phoneField)
						.addComponent(partySizeLabel).addComponent(partySizeField))
				.addGroup(layout.createParallelGroup().addComponent(availableTablesLabel).addComponent(chooseTableLabel)
						.addComponent(availableTablesField).addComponent(reserveTableButton)));

		reserveTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reserveTableButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		clearFields();
	}

	private void clearFields() {
		timeHourField.setText("");
		timeMinuteField.setText("");
		nameField.setText("");
		emailField.setText("");
		phoneField.setText("");
		partySizeField.setText("");
		availableTablesField.setText("");
		errorLabel.setText(errorMessage);
	}

	private void reserveTableButtonActionPerformed() {
		// create and call the controller
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		try {
			java.util.Date utilDate = (java.util.Date) datePicker.getModel().getValue();
			Date date = null;
			Time time = null;
			long hour = 0L, minute = 0L;
			hour = Long.parseLong(timeHourField.getText());
			hour = hour * (3600000);
			minute = Long.parseLong(timeMinuteField.getText());
			minute = minute * (60000);

			@SuppressWarnings("deprecation")
			long hourAhead = (utilDate.getHours()) * (3600000);
			@SuppressWarnings("deprecation")
			long minAhead = (utilDate.getMinutes()) * (60000);
			@SuppressWarnings("deprecation")
			long secAhead = (utilDate.getSeconds()) * (1000);
			long timeInMillis = utilDate.getTime() - hourAhead - minAhead - secAhead + hour + minute;
			date = (Date) utilDate;
			time = new Time(timeInMillis);

			int partySize = Integer.parseInt(partySizeField.getText());
			String name = nameField.getText();
			String email = emailField.getText();
			String phone = phoneField.getText();
			String chosenTables = availableTablesField.getText();
			List<Table> tables = tablesFromNumber(chosenTables);
			rac.reserveTable(date, time, partySize, name, email, phone, tables);
			errorMessage = "";
		} catch (InvalidInputException e) {
			errorMessage = e.getMessage();
		} finally {
			refreshData();
		}
	}

	private List<Table> tablesFromNumber(String chosenTables) {
		String[] tableNumbers = chosenTables.split(",");
		List<Table> tables = new ArrayList<Table>();
		for (String num : tableNumbers) {
			Table table = resto.getTable(Integer.parseInt(num));
			tables.add(table);
		}
		return tables;
	}

	private String stringTables(RestoApp r) {
		try {
			String tables = "";
			List<Integer> numbers = new ArrayList<Integer>();
			for (Table table : r.getCurrentTables()) {
				if (table.getStatus().toString().equals(Table.Status.Available.toString())) {
					numbers.add(table.getNumber());
				}
			}
			tables += ("" + numbers.get(0));
			for (int i = 1; i < numbers.size(); i++) {
				tables += ("," + numbers.get(i));
			}
			return tables;
		} catch (Exception e) {
			return "";
		}
	}

}

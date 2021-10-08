package ca.mcgill.ecse223.resto.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ChooseOrderPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JButton updateTableButton;
	private JLabel errorLabel, viewTableLabel;
	private JTextField viewTableField;

	private String errorMessage = "";

	public ChooseOrderPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		updateTableButton = new JButton();
		errorLabel = new JLabel();
		viewTableLabel = new JLabel();
		viewTableField = new JTextField();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Choose table");

		// Set text
		updateTableButton.setText("View order");
		viewTableLabel.setText("View order for table?");
		clearFields();

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(errorLabel).addComponent(viewTableLabel))
				.addComponent(viewTableField)
				.addComponent(updateTableButton)
				);

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(errorLabel)
				.addGroup(layout.createParallelGroup()
						.addComponent(viewTableLabel)
						.addComponent(viewTableField)
						.addComponent(updateTableButton))
				);

		updateTableButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateTableButtonActionPerformed();
			}
		});
	}

	private void refreshData() {
		errorLabel.setText(errorMessage);
		clearFields();
	}

	private void clearFields() {
		viewTableField.setText("");
		
	}

	private void updateTableButtonActionPerformed() {
		try {
			int chosenTable = Integer.parseInt(viewTableField.getText());
			ViewOrdersPage vop = new ViewOrdersPage(chosenTable);
			vop.setSize(300, 300);
			vop.setVisible(true);
		} catch (Exception e) {
			errorMessage = "Error : invalid table number !";
		} finally {
			refreshData();
		}
	}


}
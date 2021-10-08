package ca.mcgill.ecse223.resto.view;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class NumberedMenuPage extends JFrame {

	private static final long serialVersionUID = 8217252865589339506L;
	
	private JLabel label;
	private JButton button;

	String items;

	public NumberedMenuPage(String list) {
		items = list;
		initComponents();
	}

	private void initComponents() {
		label = new JLabel();
		button = new JButton();

		button.setText("Close this menu");

		label.setText(items);

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Numbered menu");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(button)
						.addComponent(label))
				);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(button)
				.addComponent(label)
				);
		
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonActionPerformed();
			}
		});
	}
	
	private void buttonActionPerformed() {
		this.setVisible(false);
	}
}

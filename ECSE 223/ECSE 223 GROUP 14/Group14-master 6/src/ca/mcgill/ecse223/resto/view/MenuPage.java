package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.MenuItem;

public class MenuPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private JLabel appetizersLabel, mainsLabel, dessertsLabel, alcBevLabel, nonAlcBevLabel;
	
	private JButton addButton, removeButton, updateButton;

	public MenuPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		appetizersLabel = new JLabel();
		mainsLabel = new JLabel();
		dessertsLabel = new JLabel();
		alcBevLabel = new JLabel();
		nonAlcBevLabel = new JLabel();
		
		addButton = new JButton();
		removeButton = new JButton();
		updateButton = new JButton();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Menu");

		// text values to set
		RestoAppController rac = RestoAppApplication.getControllerInstance();
		ArrayList<String> menuValues = new ArrayList<String>();
		for (MenuItem.ItemCategory itemCat : RestoAppController.getItemCategories()) {
			try {
				menuValues.add(menuItemsToString(rac.getMenuItems(itemCat)));
			} catch (InvalidInputException e) {
			}
		}

		// Set text
		appetizersLabel.setText("<html>Appetizers<br>" + menuValues.get(0));
		mainsLabel.setText("<html>Mains<br>" + menuValues.get(1));
		dessertsLabel.setText("<html>Desserts<br>" + menuValues.get(2));
		alcBevLabel.setText("<html>Alcoholic beverages<br>" + menuValues.get(3));
		nonAlcBevLabel.setText("<html>Non-Alcoholic Beverages<br>" + menuValues.get(4));
		
		addButton.setText("Add Menu Item");
		removeButton.setText("Remove Menu Item");
		updateButton.setText("Update Menu Item");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(addButton).addComponent(appetizersLabel).addComponent(mainsLabel))
				.addGroup(layout.createParallelGroup().addComponent(removeButton).addComponent(nonAlcBevLabel).addComponent(alcBevLabel))
				.addGroup(layout.createParallelGroup().addComponent(updateButton).addComponent(dessertsLabel)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(addButton).addComponent(removeButton).addComponent(updateButton))
				.addGroup(layout.createParallelGroup().addComponent(appetizersLabel).addComponent(nonAlcBevLabel).addComponent(dessertsLabel))
				.addGroup(layout.createParallelGroup().addComponent(mainsLabel).addComponent(alcBevLabel)));

		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addButtonActionPerformed();
			}
		});
		
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeButtonActionPerformed();
			}
		});
		
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateButtonActionPerformed();
			}
		});
		
	}
	
	private void addButtonActionPerformed() {
		AddMenuItemPage amip = new AddMenuItemPage();
		amip.setSize(500, 300);
		amip.setVisible(true);
	}
	
	private void removeButtonActionPerformed() {
		RemoveMenuItemPage rmip = new RemoveMenuItemPage();
		rmip.setSize(400, 200);
		rmip.setVisible(true);
	}
	
	private void updateButtonActionPerformed() {
		UpdateMenuItemPage umip = new UpdateMenuItemPage();
		umip.setSize(425, 300);
		umip.setVisible(true);
	}

	private String menuItemsToString(ArrayList<MenuItem> menuItems) {
		String returned = ""; // the starting html tag is not included due to it
								// being included in the setText calls above
		for (MenuItem mI : menuItems) {
			String itemAndPrice = mI.getName() + " : " + mI.getCurrentPricedMenuItem().getPrice();
			returned += (itemAndPrice + "<br>");
		}
		returned += "<br></html>";
		return returned;
	}
}

package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.OrderItem;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class ViewOrdersPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private int tableNumber;

	private List<Information> data;
	public static int seatsNumberOfLines;

	private String numberValString = "<html>", itemValString = "<html>", seatsValString = "<html>",
			qtyValString = "<html>";

	private JLabel numberLabel, itemLabel, seatsLabel, qtyLabel;
	private JLabel numberValuesLabel, itemValuesLabel, seatsValuesLabel, qtyValuesLabel;

	public ViewOrdersPage(int t) {
		setTableNumber(t);
		initComponents();
	}

	private void initComponents() {
		// elements
		numberLabel = new JLabel();
		itemLabel = new JLabel();
		seatsLabel = new JLabel();
		qtyLabel = new JLabel();
		numberValuesLabel = new JLabel();
		itemValuesLabel = new JLabel();
		seatsValuesLabel = new JLabel();
		qtyValuesLabel = new JLabel();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("View Order");

		// Set text
		numberLabel.setText("No");
		itemLabel.setText("Order item");
		seatsLabel.setText("Seats No");
		qtyLabel.setText("Qty");

		data = new ArrayList<Information>();
		try {
			for (Order o : Table.getWithNumber(tableNumber).getOrders()) {
				for (OrderItem item : o.getOrderItems()) {
					data.add(new Information(o.getNumber(), item.getPricedMenuItem().getMenuItem().getName(),
							item.getSeats(), item.getQuantity()));
				}
			}
			for (Information info : data) {
				numberValString += ("" + info.getOrderNumber() + lineBreaks(info.getSeatNumberOfLines()));
				itemValString += ("" + info.getOrderItemName() + lineBreaks(info.getSeatNumberOfLines()));
				seatsValString += ("" + info.getSeatsNumbers() + lineBreaks(1));
				qtyValString += ("" + info.getQuantity() + lineBreaks(info.getSeatNumberOfLines()));
			}
			numberValString += "</html>";
			itemValString += "</html>";
			seatsValString += "</html>";
			qtyValString += "</html>";
			numberLabel.setText(numberValString);
			itemLabel.setText(itemValString);
			seatsLabel.setText(seatsValString);
			qtyLabel.setText(qtyValString);
		} catch (Exception e) {
		}

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(numberLabel).addComponent(numberValuesLabel))
				.addGroup(layout.createParallelGroup().addComponent(itemLabel).addComponent(itemValuesLabel))
				.addGroup(layout.createParallelGroup().addComponent(seatsLabel).addComponent(seatsValuesLabel))
				.addGroup(layout.createParallelGroup().addComponent(qtyLabel).addComponent(qtyValuesLabel)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(numberLabel).addComponent(itemLabel)
						.addComponent(seatsLabel).addComponent(qtyLabel))
				.addGroup(layout.createParallelGroup().addComponent(numberValuesLabel).addComponent(itemValuesLabel)
						.addComponent(seatsValuesLabel).addComponent(qtyValuesLabel)));
	}
	
	private void setTableNumber(int num) {
		tableNumber = num;
	}

	private String lineBreaks(int howMany) {
		String breaks = "";
		for (int i = 0; i < howMany; i++) {
			breaks += "<br>";
		}
		return breaks;
	}

}

class Information {
	private int orderNumber;
	private String orderItemName;
	private String seatsNumbers;
	private int seatNumberOfLines = 0;
	private int quantity;

	public Information(int on, String oim, List<Seat> seats, int q) {
		this.orderNumber = on;
		this.orderItemName = oim;
		List<Integer> foundTables = new ArrayList<Integer>();
		String seatNums = "<html>";
		for (Seat s : seats) {
			if (!foundTables.contains(s.getTable().getNumber())) {
				foundTables.add(s.getTable().getNumber());
			}
		}
		for (Integer i : foundTables) {
			seatNums += ("(" + i + ") : ");
			for (Seat s : seats) {
				if (s.getTable().getNumber() == i) {
					seatNums += ("" + s.getTable().getSeats().indexOf(s) + ",");
				}
			}
			seatNumberOfLines++;
			seatNums += "<br>";
		}
		seatNums += "</html>";
		this.seatsNumbers = seatNums;
		this.quantity = q;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public String getOrderItemName() {
		return this.orderItemName;
	}

	public String getSeatsNumbers() {
		return this.seatsNumbers;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public int getSeatNumberOfLines() {
		return this.seatNumberOfLines;
	}

}

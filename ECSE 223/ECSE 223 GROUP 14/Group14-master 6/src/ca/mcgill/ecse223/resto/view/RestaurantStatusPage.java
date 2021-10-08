package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.Bill;
import ca.mcgill.ecse223.resto.model.MenuItem;
import ca.mcgill.ecse223.resto.model.Order;
import ca.mcgill.ecse223.resto.model.PricedMenuItem;
import ca.mcgill.ecse223.resto.model.Reservation;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.model.Seat;
import ca.mcgill.ecse223.resto.model.Table;

public class RestaurantStatusPage extends JFrame {

	private static final long serialVersionUID = -6671167773705738484L;

	private RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private JLabel reservationsLabel, tablesLabel, ordersLabel, PMILabel, billsLabel;

	public RestaurantStatusPage() {
		initComponents();
	}

	private void initComponents() {
		// elements
		reservationsLabel = new JLabel();
		tablesLabel = new JLabel();
		ordersLabel = new JLabel();
		PMILabel = new JLabel();
		billsLabel = new JLabel();

		// global settings and listeners
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle("Restaurant Status");

		// Set text
		reservationsLabel.setText("<html>Reservations<br>" + stringReservations(this.resto.getReservations()) + "</html");
		tablesLabel.setText("<html>Tables<br>" + stringTables(this.resto.getCurrentTables()) + "</html");
		ordersLabel.setText("<html>Orders<br>" + stringOrders(this.resto.getOrders()) + "</html");
		PMILabel.setText("<html>Priced Menu Items<br>" + stringPMIs(this.resto.getMenu().getMenuItems()) + "</html");
		billsLabel.setText("<html>Bills<br>" + stringBills(this.resto.getBills()) + "</html");

		// layout
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(reservationsLabel).addComponent(tablesLabel))
				.addGroup(layout.createParallelGroup().addComponent(ordersLabel).addComponent(billsLabel)
						.addComponent(PMILabel)));

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup().addComponent(reservationsLabel).addComponent(ordersLabel))
				.addGroup(layout.createParallelGroup().addComponent(tablesLabel).addComponent(billsLabel))
				.addComponent(PMILabel));

	}

	private String stringReservations(List<Reservation> reservations) {
		String returned = "";

		for (Reservation res : reservations) {
			returned += ("" + reservations.indexOf(res) + " : " + res.getReservationNumber() + "<br>");
		}
		return returned;
	}

	private String stringTables(List<Table> tables) {
		String returned = "";

		for (Table t : tables) {
			System.out.println("THIS IS ONE TABLE");
			returned += ("" + tables.indexOf(t) + " : " + t.getNumber() + "<br>");
			returned += ("---N" + t.getNumber() + " - L" + t.getLength() + " - W" + t.getWidth() + " - S" + t.getSeats().size() + "---<br>");
		}
		return returned;
	}

	private String stringOrders(List<Order> orders) {
		String returned = "";

		for (Order o : orders) {
			returned += ("" + orders.indexOf(o) + " : " + o.getNumber() + "<br>");
		}
		return returned;
	}

	private String stringPMIs(List<MenuItem> menuItems) {
		List<PricedMenuItem> pmis = new ArrayList<PricedMenuItem>();
		for (MenuItem mi : menuItems) {
			if (mi.hasCurrentPricedMenuItem()) {
				pmis.add(mi.getCurrentPricedMenuItem());
			}
		}
		String returned = "";
		int counter = 0;
		for (PricedMenuItem pmi : pmis) {
			returned += ("" + pmis.indexOf(pmi) + " : " + pmi.getMenuItem().getName() + (counter % 2 == 1 ? "<br>" : "&nbsp;&nbsp;"));
			counter++;
		}
		return returned;
	}

	private String stringBills(List<Bill> bills) {
		String returned = "";

		for (Bill b : bills) {
			returned += ("" + bills.indexOf(b) + " : <br>" + "Seats<br>" + stringSeats(b.getIssuedForSeats()) + "<br>");
		}
		return returned;
	}

	private String stringSeats(List<Seat> seats) {
		String returned = "";

		for (Seat s : seats) {
			returned += ("Table # : " + s.getTable().getNumber() + ". Seat # : " + getSeatPosition(s));
		}
		return returned;
	}

	private int getSeatPosition(Seat s) {
		for (Seat seat : s.getTable().getSeats()) {
			if (seat.equals(s)) {
				return s.getTable().indexOfSeat(seat);
			}
		}
		return -1;
	}

}

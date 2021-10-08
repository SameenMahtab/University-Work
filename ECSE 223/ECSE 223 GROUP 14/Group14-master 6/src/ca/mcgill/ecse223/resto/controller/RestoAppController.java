package ca.mcgill.ecse223.resto.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.*;

public class RestoAppController {

	private static RestoApp resto = RestoAppApplication.getRestaurantInstance();

	private static final int seatSize = 2;

	public RestoAppController() {
	}

	// First feature: Add a table and its seats to the restaurant
	public void createTable(int number, int x, int y, int width, int length, int numberOfSeats)
			throws InvalidInputException {
		load();
		try {
			isValueLessThan(x, 0.0, "x-coord");
			isValueLessThan(y, 0.0, "y-coord");
			isValueLessThan(number, 0.0, "table number");
			isValueLessThan(width, 0.0, "width");
			isValueLessThan(length, 0.0, "length");
			isValueLessThan(numberOfSeats, 0.0, "number of seats");
		} catch (InvalidInputException e) {
			throw e;
		}

		ArrayList<Table> tables = new ArrayList<Table>(resto.getCurrentTables());
		for (Table t : tables) {
			if (doesOverlap(t, x, y, width, length)) {
				throw new InvalidInputException("Error : table overlap !");
			}
		}

		Table newTable = null;
		try {
			newTable = new Table(number, x, y, width, length, resto);
		} catch (RuntimeException e) {
			System.out.println("ERROR");
			if (e.getMessage().equals("Cannot create due to duplicate number")) {
				throw new InvalidInputException("Error : invalid table number !");
			} else {
				throw new InvalidInputException("Error : RestoApp instance invalid !");
			}
		}

		if (resto.addCurrentTable(newTable)) {
			System.out.println("Table added");
		}
	
		for (int i = 0; i < numberOfSeats; i++) {
			Seat seat = newTable.addSeat();
			newTable.addCurrentSeat(seat);
		}
		save();
	}

	public void removeTable(int tableNumber) throws InvalidInputException {
		load();
		Table t = null;
		for (Table table : resto.getCurrentTables()) {
			if (table.getNumber() == tableNumber) {
				t = table;
			}
		}
		if (t == null) {
			throw new InvalidInputException("Error : No table was found with this number !");
		}
		boolean reserved = t.hasReservations();
		if (reserved) {
			throw new InvalidInputException("Error : this table has reservation(s) !");
		}
		List<Order> currentOrdersList = resto.getCurrentOrders();
		List<Order> currentOrders = new ArrayList<Order>();
		for (Order o : currentOrdersList) {
			currentOrders.add(o);
		}
		for (Order o : currentOrders) {
			ArrayList<Table> tables = (ArrayList<Table>) o.getTables();
			boolean inUse = tables.contains(t);
			if (inUse == true) {
				throw new InvalidInputException("Error : this table is in use !");
			}
		}
		for (Table curr : resto.getCurrentTables()) {
			System.out.print(curr.getNumber() + "---");
		}
		if (resto.removeCurrentTable(t)) {
			System.out.println("was printed");
		}
		save();
	}

	// Third feature: update the table number and number of seats of a table
	public void updateTable(int number, int newNumber, int numberOfSeats) throws InvalidInputException {
		load();
		try {
			isValueLessThan(number, 0.0, "table number");
			isValueLessThan(newNumber, 0.0, "new table number");
			isValueLessThan(numberOfSeats, 0.0, "number of seats");
		} catch (InvalidInputException e) {
			throw e;
		}

		Table t = null;
		for (Table table : resto.getCurrentTables()) {
			if (table.getNumber() == number) {
				t = table;
			}
		}
		if (t == null) {
			throw new InvalidInputException("Error : No table was found with this number !");
		}
		boolean reserved = t.hasReservations();
		if (reserved == true) {
			throw new InvalidInputException("Error : table has reservation(s) !");
		}
		ArrayList<Order> currentOrders = (ArrayList<Order>) resto.getCurrentOrders();
		for (Order o : currentOrders) {
			ArrayList<Table> tables = (ArrayList<Table>) o.getTables();
			boolean inUse = tables.contains(t);
			if (inUse == true) {
				throw new InvalidInputException("Error : table is in use !");
			}
		}
		try {
			t.setNumber(newNumber);
		} catch (RuntimeException e) {
			throw new InvalidInputException("Error : new table number is already used !");
		}
		int n = t.numberOfCurrentSeats();
		for (int i = 1; i <= numberOfSeats - n; i++) {
			Seat seat = t.addSeat();
			t.addCurrentSeat(seat);
		}
		for (int i = 1; i <= n - numberOfSeats; i++) {
			Seat seat = t.getCurrentSeat(0);
			t.removeCurrentSeat(seat);
		}
		save();
	}

	// Fourth feature: change the location of a table
	public void moveTable(int tableNumber, int x, int y) throws InvalidInputException {
		load();
		try {
			isValueLessThan(tableNumber, 0.0, "table number");
			isValueLessThan(x, 0.0, "new x-coord");
			isValueLessThan(y, 0.0, "new y-coord");
		} catch (InvalidInputException e) {
			throw e;
		}
		Table t = Table.getWithNumber(tableNumber);
		if (t == null) {
			throw new InvalidInputException("Error : No table found with this number !");
		}
		int width = t.getWidth(), length = t.getLength();
		ArrayList<Table> currTables = (ArrayList<Table>) resto.getCurrentTables();
		for (Table currTable : currTables) {
			boolean overlaps = doesOverlap(currTable, x, y, width, length);
			if (overlaps == true && tableNumber != currTable.getNumber()) {
				throw new InvalidInputException("Error : table overlap !");
			}
		}
		t.setX(x);
		t.setY(y);
		save();
	}

	public static ArrayList<MenuItem.ItemCategory> getItemCategories() {

		MenuItem.ItemCategory[] array = MenuItem.ItemCategory.values();
		ArrayList<MenuItem.ItemCategory> list = new ArrayList<MenuItem.ItemCategory>();
		for (MenuItem.ItemCategory itemCat : array) {
			list.add(itemCat);
		}
		return list;
	}

	// Fifth feature: display menu
	public ArrayList<MenuItem> getMenuItems(MenuItem.ItemCategory itemCategory) throws InvalidInputException {
		load();
		if (itemCategory == null) {
			throw new InvalidInputException("Error : invalid item category");
		}
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(resto.getMenu().getMenuItems());
		for (MenuItem menuItem : menuItems) {
			boolean current = menuItem.hasCurrentPricedMenuItem();
			MenuItem.ItemCategory category = menuItem.getItemCategory();
			if (current == true && category.equals(itemCategory)) {
				list.add(menuItem);
			}
		}
		save();
		return list;
	}

	private static boolean doesOverlap(Table t, int x, int y, int width, int length) {
		boolean overlaps = true;
		int seatSize = 2;
		int ulX = x - width / 2 - seatSize, brX = x + width / 2 + seatSize;
		int ulY = y + length / 2 + seatSize, brY = y - width / 2 - seatSize;
		int tULX = t.getX() - t.getWidth() / 2 - seatSize, tBRX = t.getX() + t.getWidth() / 2 + seatSize;
		int tULY = t.getY() + t.getLength() / 2 + seatSize, tBRY = t.getY() - t.getLength() / 2 - seatSize;
		if (ulX > tBRX || tULX > brX) {
			overlaps = false;
		}
		if (ulY < tBRY || tULY < brY) {
			overlaps = false;
		}
		if (overlaps) {
			System.out.println("table " + t.getNumber() + " overlap");
		}
		return overlaps;
	}

	public RestoApp getRestoInstance() {
		return resto;
	}

	private static boolean isBetween(int value, int small, int big) {
		return value >= small && value <= big;
	}

	// Sixth feature : reserve a table
	public Integer reserveTable(Date date, Time time, int numberInParty, String contactName, String contactEmailAddress,
			String contactPhoneNumber, List<Table> tables) throws InvalidInputException {
		load();
		try {
			isObjectNull(date, "date");
			isObjectNull(time, "time");
			isObjectNull(contactName, "Name");
			isObjectNull(contactEmailAddress, "Email address");
			isObjectNull(contactPhoneNumber, "Phone number");
			isStringEmpty(contactName, "Name");
			isStringEmpty(contactEmailAddress, "Email address");
			isStringEmpty(contactPhoneNumber, "Phone number");
			isValueLessThan(numberInParty, 1.0, "Size of party");
		} catch (InvalidInputException e) {
			throw e;
		}
		Calendar cal = Calendar.getInstance();
		if (date.before(cal.getTime())) {
			throw new InvalidInputException("Error : requested date is before today !");
		}

		List<Table> currTables = (ArrayList<Table>) resto.getCurrentTables();
		int seatCapacity = 0;
		for (Table table : tables) {
			boolean current = currTables.contains(table);
			if (!current) {
				throw new InvalidInputException("Error : The requested table was not found !");
			}
			seatCapacity += table.numberOfCurrentSeats();
			List<Reservation> reservations = (ArrayList<Reservation>) table.getReservations();
			for (Reservation res : reservations) {
				boolean overlaps = res.doesOverlap(date, time);
				if (overlaps) {
					throw new InvalidInputException("Error : time/date overlap with another reservation!");
				}
			}
		}
		if (seatCapacity < numberInParty) {
			throw new InvalidInputException("Error : party is too large for the selected table(s) !");
		}
		Table[] tableArray = listToArray(tables);
		Reservation res = new Reservation(date, time, numberInParty, contactName, contactEmailAddress,
				contactPhoneNumber, resto, tableArray);
		save();
		return res.getReservationNumber();
	}

	private static Table[] listToArray(List<Table> tables) {
		Table[] returned = new Table[tables.size()];
		int counter = 0;
		for (Table table : tables) {
			returned[counter++] = table;
		}
		return returned;
	}

	// Seventh feature part 1 : indicate table as in use
	public void startOrder(List<Integer> tableNumbers) throws InvalidInputException {
		load();
		
		List<Table> tables = tablesFromNumbers(tableNumbers);		
		
		if (tables == null) {
			throw new InvalidInputException("Error : Invalid list of tables !");
		}
		List<Table> currTablesList = resto.getCurrentTables();
		List<Table> currTables = new ArrayList<Table>();
		for (Table t : currTablesList) {
			currTables.add(t);
		}
		for (Table table : tables) {
			boolean current = currTables.contains(table);
			if (!current) {
				throw new InvalidInputException("Error : The requested table was not found !");
			}
		}
		boolean orderCreated = false;
		Order newOrder = null;
		for (Table table : tables) {
			if (orderCreated) {
				table.addToOrder(newOrder);
			} else {
				table.startOrder();
				Order lastOrder = null;
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				}
				if (table.numberOfOrders() > 0 && !table.getOrder(table.numberOfOrders() - 1).equals(lastOrder)) {
					orderCreated = true;
					newOrder = table.getOrder(table.numberOfOrders() - 1);
				}
			}
		}
		if (!orderCreated) {
			throw new InvalidInputException("Error : order could not be created !");
		}
		resto.addCurrentOrder(newOrder);
		save();
	}

	// Seventh feature part 2 : indicate table as available
	public void endOrder(Order order) throws InvalidInputException {
		load();
		if (order == null) {
			throw new InvalidInputException("Error : order is null !");
		}
		List<Order> currOrders = (ArrayList<Order>) resto.getCurrentOrders();
		boolean current = currOrders.contains(order);
		if (!current) {
			throw new InvalidInputException("Error : The requested order was not found !");
		}
		List<Table> tables = (ArrayList<Table>) order.getTables();
		for (Table table : tables) {
			if (table.numberOfOrders() > 0 && table.getOrder(table.numberOfOrders() - 1).equals(order)) {
				table.endOrder(order);
			}
		}
		if (allTablesAvailableOrDifferentCurrentOrder(tables, order)) {
			resto.removeCurrentOrder(order);
		}
		save();
	}

	private static boolean allTablesAvailableOrDifferentCurrentOrder(List<Table> tables, Order order) {
		boolean works = true;
		for (Table table : tables) {
			if (!table.getStatus().equals(Table.Status.Available)) {
				if (table.getOrder(table.numberOfOrders() - 1).equals(order)) {
					works = false;
				}
			}
		}
		return works;
	}

	private static void isValueLessThan(double value, double limit, String name) throws InvalidInputException {
		if (value < limit) {
			if (limit == 0.0) {
				throw new InvalidInputException("Error : " + name + " is negative !");
			} else {
				throw new InvalidInputException("Error : " + name + " is less than " + limit + " !");
			}
		}
	}

	private static void isObjectNull(Object value, String name) throws InvalidInputException {
		if (value == null) {
			throw new InvalidInputException("Error : " + name + " instance is null !");
		}
	}

	private static void isStringEmpty(String value, String name) throws InvalidInputException {
		if (value.equals("")) {
			throw new InvalidInputException("Error : " + name + " is empty !");
		}
	}

	// Eighth feature part 1 : add a menu item
	public void addMenuItem(String name, MenuItem.ItemCategory category, double price) throws InvalidInputException {
		load();
		try {
			isObjectNull(name, "Name");
			isObjectNull(category, "Item category");
			isStringEmpty(name, "Name");
			isValueLessThan(price, 0.0, "Price");
		} catch (InvalidInputException e) {
			throw e;
		}
		Menu menu = resto.getMenu();
		MenuItem newMenuItem;
		try {
			newMenuItem = new MenuItem(name, menu);
		} catch (RuntimeException e) {
			throw new InvalidInputException("Error : menu item name already exists !");
		}
		newMenuItem.setItemCategory(category);
		PricedMenuItem pmi = newMenuItem.addPricedMenuItem(price, resto);
		newMenuItem.setCurrentPricedMenuItem(pmi);
		save();
	}

	// Eighth feature part 2 : remove a menu item
	public boolean removeMenuItem(String menuItemName) throws InvalidInputException {
		load();
		boolean exists = false;
		MenuItem toRemove = null;
		for (MenuItem mi : resto.getMenu().getMenuItems()) {
			if (mi.getName().equals(menuItemName)) {
				exists = true;
				toRemove = mi;
			}
		}
		if (!exists) {
			throw new InvalidInputException("Error : menu item name (" + menuItemName + ") is invalid !");
		}
		exists = toRemove.hasCurrentPricedMenuItem();
		if (!exists) {
			throw new InvalidInputException("Error : menu item has no priced menu item associated");
		}
		toRemove.setCurrentPricedMenuItem(null);
		save();
		return true;
	}

	// Eighth feature part 3 : update a menu item
	public void updateMenuItem(String menuItemName, String newName, MenuItem.ItemCategory category, double price)
			throws InvalidInputException {
		load();
		MenuItem toUpdate = MenuItem.getWithName(menuItemName);
		try {
			// menuItemName is not checked for being empty or null
			// as either of those would lead to a null toUpdate
			isObjectNull(toUpdate, "Menu item");
			isObjectNull(newName, "New name");
			isObjectNull(category, "Category");
			isStringEmpty(newName, "New name");
			isValueLessThan(price, 0.0, "Price");

		} catch (InvalidInputException e) {
			throw e;
		}
		boolean current = toUpdate.hasCurrentPricedMenuItem();
		if (!current) {
			throw new InvalidInputException("Error : menu item has no priced menu item associated");
		}
		boolean duplicate = toUpdate.setName(newName);
		if (!duplicate) {
			throw new InvalidInputException("Error : new name is duplicate, could not be set !");
		}
		toUpdate.setItemCategory(category);
		if (price != toUpdate.getCurrentPricedMenuItem().getPrice()) {
			PricedMenuItem pmi = toUpdate.addPricedMenuItem(price, resto);
			toUpdate.setCurrentPricedMenuItem(pmi);
		}
		save();
	}

	// Ninth feature : order a menu item for a customer at a table
	public void orderMenuItem(String menuItemName, int quantity, List<Seat> seats) throws InvalidInputException {
		load();
		MenuItem menuItem = MenuItem.getWithName(menuItemName);
		try {
			isObjectNull(menuItem, "Menu item");
			isObjectNull(seats, "List of seats");
			isValueLessThan(seats.size(), 0.0, "Size of seats list");
			isValueLessThan(quantity, 0.0, "Item quantity");
		} catch (InvalidInputException e) {
			throw e;
		}
		boolean current = menuItem.hasCurrentPricedMenuItem();
		if (!current) {
			throw new InvalidInputException("Error : menu item has no priced menu item associated");
		}
		List<Table> currentTables = resto.getCurrentTables();
		Order lastOrder = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			current = currentTables.contains(table);
			if (!current) {
				throw new InvalidInputException("Error : seat has a non-current table");
			}
			List<Seat> currentSeats = table.getCurrentSeats();
			current = currentSeats.contains(seat);
			if (!current) {
				throw new InvalidInputException("Error : table has a non-current seat");
			}
			if (lastOrder == null) {
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				} else {
					throw new InvalidInputException("Error : table has no orders");
				}
			} else {
				Order comparedOrder = null;
				if (table.numberOfOrders() > 0) {
					comparedOrder = table.getOrder(table.numberOfOrders() - 1);
				} else {
					throw new InvalidInputException("Error : table has no orders");
				}
				if (!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("Error : invalid order matching !");
				}
			}
		}
		if (lastOrder == null) {
			throw new InvalidInputException("Error : last order is still null");
		}
		PricedMenuItem pmi = menuItem.getCurrentPricedMenuItem();
		boolean itemCreated = false;
		OrderItem newItem = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (itemCreated) {
				table.addToOrderItem(newItem, seat);
			} else {
				OrderItem lastItem = null;
				if (lastOrder.numberOfOrderItems() > 0) {
					lastItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);
					table.orderItem(quantity, lastOrder, seat, pmi);
				}
				if (lastOrder.numberOfOrderItems() > 0
						&& !lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1).equals(lastItem)) {
					itemCreated = true;
					newItem = lastOrder.getOrderItem(lastOrder.numberOfOrderItems() - 1);
				}
			}
		}
		if (!itemCreated) {
			throw new InvalidInputException("Error : item was not created !");
		}
		save();
	}

	// Tenth feature part 1 : cancel order item
	public void cancelOrderItem(OrderItem orderItem) throws InvalidInputException {
		load();
		if (orderItem == null) {
			throw new InvalidInputException("Error : orderItem instance is null !");
		}
		List<Seat> seats = orderItem.getSeats();
		Order order = orderItem.getOrder();
		List<Table> tables = new ArrayList<Table>();
		for (Seat seat : seats) {
			Table table = seat.getTable();
			Order lastOrder = null;
			if (table.numberOfOrders() > 0) {
				lastOrder = table.getOrder(table.numberOfOrders() - 1);
			} else {
				throw new InvalidInputException("Error : table has no orders !");
			}
			if (lastOrder.equals(order) && !tables.contains(table)) {
				tables.add(table);
			}
		}
		for (Table table : tables) {
			table.cancelOrderItem(orderItem);
		}
		save();
	}

	// Tenth feature part 2 : cancel order
	public void cancelOrder(int tableNumber) throws InvalidInputException {
		load();
		Table table = Table.getWithNumber(tableNumber);
		if (table == null) {
			throw new InvalidInputException("Error : specified table does not exist !");
		}
		List<Table> currentTables = resto.getCurrentTables();
		boolean current = currentTables.contains(table);
		if (!current) {
			throw new InvalidInputException("Error : specified table is not current !");
		}
		table.cancelOrder();
		save();
	}

	// Eleventh feature : view order for customers at a table
	public List<OrderItem> getOrderItems(int tableNumber) throws InvalidInputException {
		load();
		Table table = Table.getWithNumber(tableNumber);
		if (table == null) {
			throw new InvalidInputException("Error : specified table does not exist !");
		}
		List<Table> currentTables = resto.getCurrentTables();
		boolean current = currentTables.contains(table);
		if (!current) {
			throw new InvalidInputException("Error : specified table is not current !");
		}
		Table.Status tableStatus = table.getStatus();
		if (tableStatus == Table.Status.Available) {
			throw new InvalidInputException("Error : table is marked as Available !");
		}
		Order lastOrder = null;
		if (table.numberOfOrders() > 0) {
			lastOrder = table.getOrder(table.numberOfOrders() - 1);
		} else {
			throw new InvalidInputException("Error : table marked in use has no orders !");
		}
		List<Seat> currentSeats = table.getCurrentSeats();
		List<OrderItem> result = new ArrayList<OrderItem>();
		for (Seat seat : currentSeats) {
			List<OrderItem> orderItems = seat.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				Order order = orderItem.getOrder();
				if (lastOrder.equals(order) && !result.contains(orderItem)) {
					result.add(orderItem);
				}
			}
		}
		save();
		return result;
	}

	// Twelfth feature : issue bill for arbitrary customers
	public void issueBill(List<Seat> seats) throws InvalidInputException {
		load();
		try {
			isObjectNull(seats, "List of seats");
			isValueLessThan(seats.size(), 0.0, "Number of seats");
		} catch (InvalidInputException e) {
			throw e;
		}
		List<Table> currentTables = resto.getCurrentTables();
		Order lastOrder = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			boolean current = currentTables.contains(table);
			if (!current) {
				throw new InvalidInputException("Error : specified table is not current !");
			}
			List<Seat> currentSeats = table.getCurrentSeats();
			current = currentSeats.contains(seat);
			if (!current) {
				throw new InvalidInputException("Error : specified seat is not current !");
			}
			if (lastOrder == null) {
				if (table.numberOfOrders() > 0) {
					lastOrder = table.getOrder(table.numberOfOrders() - 1);
				} else {
					throw new InvalidInputException("Error : table has no orders !");
				}
			} else {
				Order comparedOrder = null;
				if (table.numberOfOrders() > 0) {
					comparedOrder = table.getOrder(table.numberOfOrders() - 1);
				} else {
					throw new InvalidInputException("Error : table has no orders !");
				}
				if (!comparedOrder.equals(lastOrder)) {
					throw new InvalidInputException("Error : invalid order matching !");
				}
			}
		}
		if (lastOrder == null) {
			throw new InvalidInputException("Error : last order is still null !");
		}
		boolean billCreated = false;
		Bill newBill = null;
		for (Seat seat : seats) {
			Table table = seat.getTable();
			if (billCreated) {
				table.addToBill(newBill, seat);
			} else {
				Bill lastBill = null;
				if (lastOrder.numberOfBills() > 0) {
					lastBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
				}
				table.billForSeat(lastOrder, seat);
				if (lastOrder.numberOfBills() > 0
						&& !lastOrder.getBill(lastOrder.numberOfBills() - 1).equals(lastBill)) {
					billCreated = true;
					newBill = lastOrder.getBill(lastOrder.numberOfBills() - 1);
				}
			}
		}
		if (!billCreated) {
			throw new InvalidInputException("Error : bill was never created !");
		}
		save();
	}

	private static void load() {
		RestoAppApplication.setRestaurantInstance(RestoAppApplication.load());
		resto = RestoAppApplication.getRestaurantInstance();
	}

	private static void save() {
		RestoAppApplication.save(resto);
	}
	
	private List<Table> tablesFromNumbers(List<Integer> numbers) {
		List<Table> tables = new ArrayList<Table>();
		for (int num : numbers) {
			Table table = null;
			for (Table t : resto.getCurrentTables()) {
				if (t.getNumber() == num) {
					table = t;
				}
			}
			if (table != null) {
				tables.add(table);	
			}
			
		}
		if (tables.size() == 0) {
			return null;
		}
		return tables;
	}
}

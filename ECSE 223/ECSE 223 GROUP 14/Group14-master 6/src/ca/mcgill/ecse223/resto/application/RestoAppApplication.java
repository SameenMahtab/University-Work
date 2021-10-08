package ca.mcgill.ecse223.resto.application;

import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.resto.view.*;

public class RestoAppApplication {

	private static RestoApp restaurant = null;
	private static RestoAppController rac = null;

	private static String fileName = "src/menuPlay.resto";

	public static void main(String[] args) {

		restaurant = getRestoApp();
		rac = new RestoAppController();
		
		PersistenceObjectStream.setFilename(fileName);
		
		save(restaurant);
		restaurant = load();
	
		// start UI
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainPage main = new MainPage();
				main.loadPage("main");
//				TablesLayoutPage t = new TablesLayoutPage();
			}
		});

	}

	public static RestoApp getRestoApp() {
		RestoApp resto;
		if (restaurant == null) {
			resto = load();
		} else {
			resto = restaurant;
		}
		return resto;
	}

	// save restoapp
	public static void save(Object o) {
		PersistenceObjectStream.serialize(o);
	}

	// load restoapp from persistence
	public static RestoApp load() {
		PersistenceObjectStream.setFilename(fileName);
		Object loadedFile = PersistenceObjectStream.deserialize();
		RestoApp resto;

		if (loadedFile == null) {
			resto = new RestoApp();
		} else {
			resto = (RestoApp) loadedFile;
			resto.reinitialize();
		}
		return resto;
	}
	
	public static RestoApp getRestaurantInstance() {
		return restaurant;
	}
	
	public static RestoAppController getControllerInstance() {
		return rac;
	}
	
	public static void setRestaurantInstance(RestoApp r) {
		restaurant = r;
	}
}

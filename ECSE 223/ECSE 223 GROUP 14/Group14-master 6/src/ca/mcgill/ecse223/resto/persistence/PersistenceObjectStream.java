package ca.mcgill.ecse223.resto.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {

	private static String fileName = "";

	public static void serialize(Object object) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			throw new RuntimeException("Could not save data to file '" + fileName + "'.");
		}
	}

	public static Object deserialize() {
		Object o = null;
		ObjectInputStream in, in2;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			in = new ObjectInputStream(fileIn);
			o = in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			try {
				FileInputStream fileIn2 = new FileInputStream(fileName);
				in2 = new ObjectInputStream(fileIn2);
				o = in2.readObject();
				in2.close();
				fileIn2.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
				o = null;
			}
		}
		return o;
	}

	public static void setFilename(String newFileName) {
		fileName = newFileName;
	}
}

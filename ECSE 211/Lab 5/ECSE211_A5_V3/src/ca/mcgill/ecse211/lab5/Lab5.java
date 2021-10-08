package ca.mcgill.ecse211.lab5;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.SensorPort;


public class Lab5{
	// Motor Objects, Sensor Objects and Robot related parameters
	public static final EV3LargeRegulatedMotor leftMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("C"));
	public static final EV3LargeRegulatedMotor rightMotor =
			new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	public static final EV3UltrasonicSensor usSensor = new EV3UltrasonicSensor(SensorPort.S3);
	public static SampleProvider usDistance = usSensor.getMode("Distance");	
	//back light sensor
	public static EV3ColorSensor lightSensor = new EV3ColorSensor(SensorPort.S4);
	public static SampleProvider lightStatus = lightSensor.getMode("RGB");

	//Y=0; R=1; G=2; B=3;
	public final static int TARGET = 2;
	private static final TextLCD lcd = LocalEV3.get().getTextLCD();
  
	

	
	  public static final double WHEEL_RAD = 2.1;
	  public static final double TRACK = 9;
	 
	public static void main(String[] args) throws OdometerExceptions{
	    
			// Odometer related objects
		  final Odometer odometer = Odometer.getOdometer(leftMotor, rightMotor, TRACK, WHEEL_RAD);
		  Display odometryDisplay = new Display(lcd); // No need to change
		
		  // Start odometer thread
	      Thread odoThread = new Thread(odometer);
	      odoThread.start();
	      
	     //Used for testing
	     // final Thread odoDisplayThread = new Thread(odometryDisplay);
	     // odoDisplayThread.start();
	      	      
	      // spawn a new Thread for localization and color detection
	      (new Thread() {
			public void run() {
			    UltrasonicLocalizer robot = new UltrasonicLocalizer(leftMotor, rightMotor, usSensor);
				LightLocalizer lightLcl2 = new LightLocalizer(leftMotor, rightMotor, lightSensor);
	   	   		SearchCans Cansearch = new SearchCans(leftMotor, rightMotor, usSensor);

	      	try {
	        	  Thread.sleep(5000);//to make sure usSensor is turned on
	          }catch (InterruptedException e) {
	        	//do nothing
	          }	          
	      		
	      	//2nd part of the lab
	      	robot.risingEdge();
      		lightLcl2.localization(); //add in the localization, move to (LL)	
      		Cansearch.run();
	      	
	        }
	      }).start();
	      
	      while (Button.waitForAnyPress() != Button.ID_ESCAPE);
	      System.exit(0);
	}
	  
}
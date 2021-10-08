

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

public class LocalizationLab {

	  public static final double WHEEL_RADIUS = 2.1;
	  public static final double TRACK = 10.15;
	  public static double wayPoints[] = {0.0,0.0};
	  private static final EV3LargeRegulatedMotor leftMotor =
		      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		  
	  private static final EV3LargeRegulatedMotor rightMotor =
		      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
	  private static final Port usPort = LocalEV3.get().getPort("S1");		
	  private static final EV3ColorSensor colorSensor = new EV3ColorSensor(LocalEV3.get().getPort("S2"));
	  
	  public static void main(String[] args) {
		  
		    int buttonChoice;
		    int buttonchoice2;

		    final TextLCD t = LocalEV3.get().getTextLCD();
		    
		    //sensor initialization
		    @SuppressWarnings("resource")	
		    SensorModes usSensor = new EV3UltrasonicSensor(usPort);
			SampleProvider usValue = usSensor.getMode("Distance");			
			float[] usData = new float[usValue.sampleSize()];
		    //classes initialization
			String fallingEdge = "Falling Edge";
			String risingEdge = "Rising Edge";
		    Odometer odometer = new Odometer(leftMotor, rightMotor);
		    Navigation navigator = new Navigation(leftMotor, rightMotor, odometer, wayPoints);
		    UltrasonicLocalization usLocalize = new UltrasonicLocalization(leftMotor, rightMotor, usValue, usData, odometer);
		    LightLocalization lightlocalizer = new LightLocalization(odometer, colorSensor, leftMotor, rightMotor, navigator);
		    displayLCD display = new displayLCD(odometer,t);
		    
		    do {
			      // clear the display
			      t.clear();
			      //Making choice for localization type
			      t.drawString("< Left | Right >", 0, 0);
			      t.drawString("Localiz|Localize", 0, 1);
			      t.drawString(" with  | with   ", 0, 2);
			      t.drawString("Falling| Rising ", 0, 3);
			      t.drawString(" Edge  | Edge   ", 0, 4);

			      buttonChoice = Button.waitForAnyPress();
			    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
			 if (buttonChoice == Button.ID_LEFT) {
				 odometer.start();
				 display.start();
				 usLocalize.fallingedge();
				 
				 buttonchoice2 = Button.waitForAnyPress();
				 // press Up button to start the light localization
				 if (buttonchoice2 == Button.ID_UP){
					 lightlocalizer.doLocalization();
					 usLocalize.fallingedge();
				 }
				 
			 }
			 if (buttonChoice == Button.ID_RIGHT) {
				 odometer.start();
				 display.start();
				 usLocalize.risingEdge();
				 
			 }
			 while (Button.waitForAnyPress() == Button.ID_ESCAPE);
			    System.exit(0);
	  }
}

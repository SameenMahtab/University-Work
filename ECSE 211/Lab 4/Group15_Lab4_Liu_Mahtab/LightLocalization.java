


import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;

public class LightLocalization {
	
	  private EV3ColorSensor colorSensor ;
	  private float[] rgbArray = new float [1];
	  private String rgbString = "";
	  private static double sensorValue;
	  private static float[] colorData;
	  private static final long CORRECTION_PERIOD = 10;
	  private static double[] angles;
	  private static int angle_index;
	  private static boolean running;
	  private static final double SIZEOFROBOT = 5.0;
	  private float brightness;
	  private float referencebrightness;
	  private Odometer odometer;
	  private Navigation navigator;
	  private EV3LargeRegulatedMotor leftMotor;
	  private EV3LargeRegulatedMotor rightMotor;
	  
	  public LightLocalization(Odometer odo, EV3ColorSensor ColorSensor, EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor, Navigation navi){
		  
		  this.odometer = odo;
		  this.colorSensor = ColorSensor;
		  this.leftMotor = leftMotor;
		  this.rightMotor = rightMotor;
		  this.navigator = navi;
		  angles = new double[4];
		  angle_index = 0;
		  running = true;
	  }
	  
	  public void doLocalization(){
		  
		  while(running){
			  //rotate to seek for four lines
			  leftMotor.setSpeed(150);
			  rightMotor.setSpeed(150);
			  leftMotor.forward();
			  rightMotor.backward();
			  colorSensor.getRedMode().fetchSample(rgbArray, 0); 
		      brightness = rgbArray[0];  
		      //if recognize a line
		      if (Math.abs(referencebrightness-brightness)*100>4.0){
		    	  Sound.beep();
		    	  angles[angle_index] = odometer.getTheta();
		    	  angle_index++;
		      }
		      if(angles[3]!=0) {
		    	   running = false;
		      }
		  }
		  double x=SIZEOFROBOT*Math.sin(angles[0]*Math.PI/180);
		  double y=-SIZEOFROBOT*Math.sin((angles[1]-90)*Math.PI/180);
		  odometer.setX(x);
		  odometer.setY(y);
		  navigator.start();
	  }

	  private static int convertDistance(double radius, double distance) {
		    return (int) ((180.0 * distance) / (Math.PI * radius));
		  }

	  private static int convertAngle(double radius, double width, double angle) {
		    return convertDistance(radius, Math.PI * width * angle / 360.0);
		  }
	  
}

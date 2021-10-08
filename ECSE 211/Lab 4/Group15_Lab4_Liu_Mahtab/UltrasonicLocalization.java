

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicLocalization  {
	  
	  //Motors
	  private EV3LargeRegulatedMotor leftMotor;
	  private EV3LargeRegulatedMotor rightMotor;
	  //sensor
	  private static float[] usData;
	  private SampleProvider usSensor;
	  //odometer
	  private Odometer odometer;
	  //class variables 
	  static float distance;
	  private static final int ROTATE_SPEED_LOW = 34;
	  private static final int ROTATE_SPEED_HIGH = 100;
	  private static final int MINIMUM_DISTANCE_Falling = 19;
	  private static final int MINIMUM_DISTANCE_RISING = 30;
	  private static final int WALL_MARGIN = 3;
	  private static final int MAXUSDISTANCE = 255;
	  private static final int FILTER_OUT = 10;
	  private int filterControl;
	  static double angleA;
	  static double angleB;
	  static double deltaT;
	  private static boolean running;
	  private static Object lock; /*lock object for mutual exclusion*/
	  
	  //constructor
	  UltrasonicLocalization(EV3LargeRegulatedMotor leftmotor, EV3LargeRegulatedMotor rightmotor, SampleProvider usSensor,float[] usData, Odometer odo){
		  
		  this.leftMotor = leftmotor;
		  this.rightMotor= rightmotor;
		  this.odometer = odo;
		  this.usSensor = usSensor;
		  this.usData = usData;
		  this.angleA = 0.0;
		  this.angleB = 0.0;
		  this.deltaT = 0.0;
	  }
	  public void fallingedge() {
		    leftMotor.setSpeed(ROTATE_SPEED_LOW);
  		rightMotor.setSpeed(ROTATE_SPEED_LOW);
  		
  		running = true;
		    
		    try {
		        Thread.sleep(4500);
		      } catch (Exception e) {
		      }
		    while (running) {
		    		//Rotate Robot until it sees no wall
			    	while(getFilteredData() <= (MINIMUM_DISTANCE_Falling + WALL_MARGIN)){
			    		
			    		leftMotor.forward();
			    		rightMotor.backward();

			    	}
			    	
			    	//Start Localization With Falling edge
			    	while(getFilteredData()> MINIMUM_DISTANCE_Falling){
			    		leftMotor.forward();
			    		rightMotor.backward();
			    	}
			    	System.out.println(getFilteredData());
			    	//Robot stopped, fetch angleA
			    	this.angleA = odometer.getTheta();
			    	
			    	while(getFilteredData() <=MINIMUM_DISTANCE_Falling){
			    		//Move the opposite direction
			    		leftMotor.backward();
						rightMotor.forward();
			    	}
			    	while(getFilteredData() > MINIMUM_DISTANCE_Falling){
			    		//Keep moving the counter-clockwise
			    		leftMotor.backward();
						rightMotor.forward();
			    	}
			    	
			    	leftMotor.setSpeed(0);
			    	rightMotor.setSpeed(0);
			    	
					
					//get the angle from the odometer
					this.angleB = odometer.getTheta();
					
					
					
					//Find deltaT
					if(this.angleA < this.angleB){
		    			this.deltaT = (double) (45 - ((this.angleA + (360-this.angleB))/2)-90 + 360 -4); 
		    			this.odometer.setTheta(this.deltaT);
		    			turnTo(0, deltaT);
		    			
		    		}
		    		 running = false;
		    	} //end of if falling edge
		    try {
    	        Thread.sleep(50);
    	      } catch (Exception e) {
    	      } // Poor man's timed sampling
	  }
	  public void risingEdge() {
		    leftMotor.setSpeed(ROTATE_SPEED_LOW);
  		rightMotor.setSpeed(ROTATE_SPEED_LOW);
  		
  		running = true;
		    
		    try {
		        Thread.sleep(4500);
		      } catch (Exception e) {
		      }
		    while(running) {
		    while(getFilteredData() >= (MINIMUM_DISTANCE_RISING - WALL_MARGIN)){
    			leftMotor.backward();
    			rightMotor.forward();
    		}
    		//keep rotating counter clockwise until no wall is seen
    		while(getFilteredData() < MINIMUM_DISTANCE_RISING){
    			leftMotor.backward();
    			rightMotor.forward();
    		}
    		//fetch the first angle when we no longer see the back wall
    		this.angleA = odometer.getTheta();
    		
    		//rotate clockwise until you see a wall again
    		while(getFilteredData() > MINIMUM_DISTANCE_RISING){
    			leftMotor.forward();
    			rightMotor.backward();
    		}
    		//rotate clockwise until the robot no longer sees the wall
    		while(getFilteredData() < MINIMUM_DISTANCE_RISING){
    			leftMotor.forward();
    			rightMotor.backward();
    		}
    		this.angleB = odometer.getTheta();
    		
    		leftMotor.stop();
	    	rightMotor.stop();
	    	
	    	//Find deltaT
			if(this.angleA < this.angleB){
    			this.deltaT = (double) (45 - ((this.angleA + (360-this.angleB))/2) -90+ 360+7);
    			this.odometer.setTheta(this.deltaT);
    			turnTo(0, deltaT);
    			
    		}
		    running=false;
		    }
		    try {
    	        Thread.sleep(50);
    	      } catch (Exception e) {
    	      }
    
	  }	  
	//Conversion methods.
		private static int convertDistance(double radius, double distance) {
			return (int) ((180.0 * distance) / (Math.PI * radius));
		}

		private static int convertAngle(double radius, double width, double angle) {
			return convertDistance(radius, Math.PI * width * angle / 360.0);
		}
	  
	//Get US data. Function taken from github user WPoole 
	  
		private float getFilteredData() {
			//usdata = nothing
			usSensor.fetchSample(usData, 0);
			//usdata contains the distance data from the US sensor
			
			float distance = (int) (100*usData[0]);
					
			return distance;
		}
		
		void turnTo(double theta, double currentTheta){ //this function makes sure the robot is rotating the optimized angle
			
			double thetaCorrection = 0.0; //[in degrees]
			thetaCorrection = theta - currentTheta;
			
			leftMotor.setSpeed(ROTATE_SPEED_LOW);
		    rightMotor.setSpeed(ROTATE_SPEED_LOW);
		    
			if(thetaCorrection <= 180 && thetaCorrection >=-180){ //acceptable range, no correction needed
				leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
			    rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
			   
			}
			else if(thetaCorrection <-180){ //move counter-clockwise
				thetaCorrection = thetaCorrection +360;
				 leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
			     rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
			     
			}
			else if(thetaCorrection >180){ //move clockwise
				thetaCorrection = thetaCorrection - 360;
				leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
			    rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
			   
			}
			
		}
		
	   
	 
}

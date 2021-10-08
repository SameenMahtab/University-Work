package ca.mcgill.ecse211.lab5;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.SampleProvider;

public class Navigation{
	private static final int FORWARD_SPEED = 250;
	private static final int ROTATE_SPEED = 150;
	private static final double WHEEL_RAD=2.12;
	private static final double TRACK=13.20;
	public static int obstDistance;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private static SampleProvider usDistance;
	private static float[] usData;
	private static Odometer odometer;
	
	private static boolean isNavigating=true;
	
	/**
	 * Navigation constructor
	 * 
	 * @param leftMotor
	 * @param rightMotor
	 * @param odometer
	 * @param usdata
	 * @param usDistance
	 * @throws OdometerExceptions
	 */
	public Navigation(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,Odometer odometer,float[]usdata,SampleProvider usDistance) throws OdometerExceptions {
		    this.leftMotor = leftMotor;
		    this.rightMotor = rightMotor;
		    this.odometer=odometer;
		    this.usData=usdata;
		    this.usDistance=usDistance;
	}
	
	/**
	 * This method calculates the distance to travel to a point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int convertDistance(double x,double y) {
	    return (int) ((180.0 * (Math.sqrt(x*x+y*y))) / (Math.PI * WHEEL_RAD));
	  }
	/**
	 * Calculate the rotations when turning for a specific angle
	 * 
	 * @param distance
	 * @return
	 */
	public static int convertDist(double distance) {
	    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
	  }
	 /**
	   * This method allows the conversion of a angle to the total rotation of each wheel 
	   * needed to turn at that angle 
	   * 
	   * @param angle
	   * @return integer
	   */

	  public static int convertAngle(double angle) {
	    return convertDist(Math.PI * TRACK * (angle) / 360.0);
	  }
	/**
	 * Travel to a specific coordination without obstacles in between
	 * 
	 * @param x
	 * @param y
	 * @param leftMotor
	 * @param rightMotor
	 */
	public static void TravelTo(double x,double y,EV3LargeRegulatedMotor leftMotor,EV3LargeRegulatedMotor rightMotor) {
		
				double Xn=odometer.getX();
				double Yn=odometer.getY();
				TurnTo((x*30.48-Xn),(y*30.48-Yn),leftMotor,rightMotor);
				leftMotor.setSpeed(FORWARD_SPEED);
				rightMotor.setSpeed(FORWARD_SPEED);
				leftMotor.rotate((int)(convertDistance((x*30.48-Xn),(y*30.48-Yn))),true);
				rightMotor.rotate((int)(convertDistance((x*30.48-Xn),(y*30.48-Yn))),false);
				isNavigating=true;
	}
	/**
	 * Travel to a specific coordination with an obstacle in between, using the TravelTo method above
	 * @param x
	 * @param y
	 * @param leftMotor
	 * @param rightMotor
	 */
	public static void NavigateTo(double x,double y,EV3LargeRegulatedMotor leftMotor,EV3LargeRegulatedMotor rightMotor) {
		double Xn=odometer.getX();
		double Yn=odometer.getY();
		
		TurnTo((x*30.48-Xn),(y*30.48-Yn),leftMotor,rightMotor);
		leftMotor.forward();
		rightMotor.forward();
		while(isNavigating==true) {
				usDistance.fetchSample(usData, 0); // acquire data
				obstDistance = (int) (usData[0] * 100.0); // extract from buffer, cast to int
			
			double deltaX = x * 30.48- odometer.getX();
			double deltaY = y * 30.48 -odometer.getY();
			double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
			if (obstDistance<6) {
					Sound.beep();
				

					leftMotor.stop(true);
					rightMotor.stop();
					
					leftMotor.rotate(convertAngle(90), true); 
					rightMotor.rotate(-convertAngle(90), false);
					
					leftMotor.rotate(convertDist(25), true);
					rightMotor.rotate(convertDist(25), false);
					
					leftMotor.rotate(convertAngle(-90), true); 
					rightMotor.rotate(-convertAngle(-90), false);
					
					leftMotor.rotate(convertDist(40), true);
					rightMotor.rotate(convertDist(40), false);

					leftMotor.rotate(convertAngle(-90), true); 
					rightMotor.rotate(-convertAngle(-90), false);
					
					leftMotor.rotate(convertDist(25), true);
					rightMotor.rotate(convertDist(25), false);
					
					leftMotor.rotate(convertAngle(90), true); 
					rightMotor.rotate(-convertAngle(90), false);
					
					TravelTo(x,y,leftMotor,rightMotor);
					
					isNavigating=false;
					}
			if(distance < 1.5) {
				leftMotor.stop(true);
				rightMotor.stop();
				isNavigating= false;

			}
				
		}	
	}
	
	/**
	 * Turn the direction to a specific coordination(x,y) 
	 * @param x
	 * @param y
	 * @param leftMotor
	 * @param rightMotor
	 */
	public static void TurnTo(double x,double y,EV3LargeRegulatedMotor leftMotor,EV3LargeRegulatedMotor rightMotor) {
		double Thetan=odometer.getTheta();
		isNavigating=true;
		double temp=(Math.atan((x)/(y))*180/Math.PI);
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		if((x)>=0&&(y)>=0) {
			rightMotor.rotate(convertAngle(temp-Thetan),true);
			leftMotor.rotate(-convertAngle(temp-Thetan),false);
		}
		if((x)<0&&(y)>=0) {
			rightMotor.rotate(convertAngle(temp-Thetan),true);
			leftMotor.rotate(-convertAngle(temp-Thetan),false);
		}
		if(x>0&&y<0) {
			leftMotor.rotate(convertAngle(Thetan-temp-180),true);
			rightMotor.rotate(-convertAngle(Thetan-temp-180),false);
		}
		if(x<=0&&y<0) {
			leftMotor.rotate(-convertAngle(temp+180-Thetan),true);
			rightMotor.rotate(convertAngle(temp+180-Thetan),false);
		}
	}
}

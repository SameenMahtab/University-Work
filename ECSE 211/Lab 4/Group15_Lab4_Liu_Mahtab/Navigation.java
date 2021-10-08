

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Navigation extends Thread {
	
	 private EV3LargeRegulatedMotor leftMotor;
	 private EV3LargeRegulatedMotor rightMotor;
	 private Odometer odometer;
	 private static final int FORWARD_SPEED = 250;
	 private static final int ROTATE_SPEED = 50;

	 public double startingPoint[];
	 
	 
	public Navigation (EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor, Odometer odometer, double wayPoint[]) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.odometer = odometer;
		this.startingPoint = wayPoint;
	}
	
	public void run(){
		
			travelTo(startingPoint[0], startingPoint[1]);
	}
	
	//helper methods
	void travelTo(double x, double y){
		//move forward until you reach the position (x,y)
		double deltaX, deltaY;
		deltaX = x - odometer.getX();
		deltaY= y - odometer.getY();
		double thetaFinal = Math.toDegrees(Math.atan2(deltaX,deltaY));
		
		turnTo(thetaFinal, odometer.getTheta());
		
		double distanceToTravel = Math.hypot(deltaY, deltaX); //minimum distance to travel to waypoint
		
			isNavigating(true); 
			
			leftMotor.setSpeed(FORWARD_SPEED);
		    rightMotor.setSpeed(FORWARD_SPEED);
		    leftMotor.rotate(convertDistance(LocalizationLab.WHEEL_RADIUS, distanceToTravel), true);
		    rightMotor.rotate(convertDistance(LocalizationLab.WHEEL_RADIUS, distanceToTravel), false);
		    
		    
		    isNavigating(false); 
		
	}
	void turnTo(double theta, double currentTheta){ //this function makes sure the robot is rotating the optimized angle
		
		isNavigating(true); 
		
		double thetaCorrection = 0.0; //[in degrees]
		thetaCorrection = theta - currentTheta;
		
		leftMotor.setSpeed(ROTATE_SPEED);
	    rightMotor.setSpeed(ROTATE_SPEED);
	    
		if(thetaCorrection <= 180 && thetaCorrection >=-180){ //acceptable range, no correction needed
			leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
		    rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
		    isNavigating(false); //this logic is inspired from group 51 
		}
		else if(thetaCorrection <-180){ //move counter-clockwise
			thetaCorrection = thetaCorrection +360;
			 leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
		     rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
		     isNavigating(false); //this logic is inspired from group 51 
		}
		else if(thetaCorrection >180){ //move clockwise
			thetaCorrection = thetaCorrection - 360;
			leftMotor.rotate(convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), true);
		    rightMotor.rotate(-convertAngle(LocalizationLab.WHEEL_RADIUS, LocalizationLab.TRACK, thetaCorrection), false);
		    isNavigating(false); //this logic is inspired from group 51 
		}
		
	}
	//the logic is inspired from group 51 
	boolean isNavigating(boolean check){ 
		return check; //another thread has called a method and has yet to return
	}
	
	//helper functions to convert distances to angles and vice-versa
	 private static int convertDistance(double radius, double distance) {
		    return (int) ((180.0 * distance) / (Math.PI * radius));
		  }

	 private static int convertAngle(double radius, double width, double angle) {
		    return convertDistance(radius, Math.PI * width * angle / 360.0);
		  }
}

package ca.mcgill.ecse211.lab5;
import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
public class SearchCans implements Runnable {
	private static final int LLx=1;
	private static final int LLy=1;
	private static final int URx=4;
	private static final int URy=4;
	private static final double TILE_SIZE=30.38;
	private static final int FORWARD_SPEED = 175;
	private static final int ROTATE_SPEED = 50;
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private EV3LargeRegulatedMotor arm;
	private EV3UltrasonicSensor ultraSensor;
	private Odometer odometer;
	private float[] RGB = new float[3];
	
	public SearchCans(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,EV3UltrasonicSensor ultraSensor ) {
		this.leftMotor = leftMotor;
	    this.rightMotor = rightMotor;
	    this.ultraSensor = ultraSensor;
	    try {
	      this.odometer = Odometer.getOdometer();
	    } catch (OdometerExceptions e) {
	      e.printStackTrace();
	    }
	}
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
		    leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, distanceToTravel), true);
		    rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, distanceToTravel), false);
		    
		    
		    isNavigating(false); 
		
	}
	void turnTo(double theta, double currentTheta){ //this function makes sure the robot is rotating the optimized angle
		
		isNavigating(true); 
		
		double thetaCorrection = 0.0; //[in degrees]
		thetaCorrection = theta - currentTheta;
		
		leftMotor.setSpeed(ROTATE_SPEED);
	    rightMotor.setSpeed(ROTATE_SPEED);
	    
		if(thetaCorrection <= 180 && thetaCorrection >=-180){ //acceptable range, no correction needed
			leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), true);
		    rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), false);
		    isNavigating(false); 
		}
		else if(thetaCorrection <-180){ //move counter-clockwise
			thetaCorrection = thetaCorrection +360;
			 leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), true);
		     rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), false);
		     isNavigating(false); 
		}
		else if(thetaCorrection >180){ //move clockwise
			thetaCorrection = thetaCorrection - 360;
			leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), true);
		    rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, thetaCorrection), false);
		    isNavigating(false); 
		}
		
	}
	/**
	 * sets both wheel speeds and makes the robot either move forward or backwards based on those speeds
	 * 
	 * @param lSpd
	 * @param rSpd
	 */
	public void setSpeeds(int lSpd, int rSpd) {
		this.leftMotor.setSpeed(lSpd);
		this.rightMotor.setSpeed(rSpd);
		if (lSpd < 0)
			this.leftMotor.backward();
		else
			this.leftMotor.forward();
		if (rSpd < 0)
			this.rightMotor.backward();
		else
			this.rightMotor.forward();
	}
	public void turnL() {
		//turn 90 degrees to the left
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		leftMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), true);
		rightMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), false);
		
		//go forward one tile
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE/2 ), true);
		rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE/2), false);
		
		//turn 90 degrees to the right 
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), true);
		rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), false);
		
		//go forward more than one tile so we know no matter what orientation the obstacle is in we passed it
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE), true);
		rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE), false);
		
		//turn 90 degrees to the right 
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		leftMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), true);
		rightMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), false);
		
		//go forward one tile
		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE/2 ), true);
		rightMotor.rotate(convertDistance(Lab5.WHEEL_RAD, TILE_SIZE/2), false);
		
		//turn 90 degrees to the right 
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		leftMotor.rotate(-convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), true);
		rightMotor.rotate(convertAngle(Lab5.WHEEL_RAD, Lab5.TRACK, 90), false);
		
		
	}
	
	public void Search() {
		isSearching(true);
		SampleProvider provider=ultraSensor.getDistanceMode();
		float sample[]=new float[3];
		for(int j=0;j<=(URy-LLy);j++){
			leftMotor.setSpeed(ROTATE_SPEED);
	    	rightMotor.setSpeed(ROTATE_SPEED);
	    	if((LLx*TILE_SIZE-20)<(odometer.getX())&&(LLx*TILE_SIZE+20)>(odometer.getX())) {//robot on the left side of the searching area
	    		turnTo(90,odometer.getTheta());//FACING THE SEARCHING AREA
	    		provider.fetchSample(sample, 0);
		    	if((sample[0]*100)<=((URx-LLx)*TILE_SIZE-odometer.getX())) {//IF THERE IS THE FIRST CAN ON THIS LINE
		    		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
		    		while((sample[0]*100)>5) {
		    			provider.fetchSample(sample, 0);	
				    	}
		    		setSpeeds(0,0);
				   	
		    		float[] sampleCan1 = new float[3];
					 sampleCan1 = ColorScanner.scanCan();
					 ColorScanner.colorScanner(sampleCan1);	
					 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
					rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
				   	turnL();//AVOID THE CAN
				   	provider.fetchSample(sample, 0);
			    	if((sample[0]*100)<=((URx-LLx)*TILE_SIZE-odometer.getX())) {//IF THERE IS A SECOND CAN ON THIS LINE
			    	System.out.println(sample[0]);
				   		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
			    		while((sample[0]*100)>5) {
			    			provider.fetchSample(sample, 0);	
					    	}
			    		setSpeeds(0,0);
					   	//detectcolor();
			    		float[] sampleCan2 = new float[3];
						 sampleCan2 = ColorScanner.scanCan();
						 ColorScanner.colorScanner(sampleCan2);	
						 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
						rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
					   	turnL();//AVOID THE CAN
					   	provider.fetchSample(sample, 0);
				    	if((sample[0]*100)<=((URx-LLx)*TILE_SIZE-odometer.getX())) {//IF THERE IS A THIRD CAN ON THIS LINE
				    		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
				    		while((sample[0]*100)>5) {
				    			provider.fetchSample(sample, 0);	
						    	}
				    		setSpeeds(0,0);
						   	//detectcolor();
				    		float[] sampleCan3 = new float[3];
							 sampleCan3 = ColorScanner.scanCan();
							 ColorScanner.colorScanner(sampleCan3);	
							 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
							rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
						   	turnL();
						   	leftMotor.setSpeed(FORWARD_SPEED);
				    		rightMotor.setSpeed(FORWARD_SPEED);
				    		travelTo(URx*TILE_SIZE,odometer.getY());// GO TO THE OTHER END OF THE LINE
				    		travelTo(URx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
				    		}
				    	else {
				    		travelTo(URx*TILE_SIZE,odometer.getY());// GO TO THE OTHER END OF THE LINE
				    		travelTo(URx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
				    	}
			    		}
			    	else {
			    		travelTo(URx*TILE_SIZE,odometer.getY());// GO TO THE OTHER END OF THE LINE
			    		travelTo(URx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
			    	}
		    		}
		    	else {
		    		travelTo(LLx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);// IF THERE'S NO CAN ON THIS LINE, GO UP TO CHECK THE NEXT LINE
		    	}	
	    	}
	    	else if((odometer.getX())<(URx*TILE_SIZE+20)&&(odometer.getX())>(URx*TILE_SIZE-20)) {//robot on the right side of the searching area
	    		turnTo(-90,odometer.getTheta());
	    		provider.fetchSample(sample, 0);
	    		
		    	if((sample[0]*100)<=(odometer.getX()-LLx*TILE_SIZE)) {//IF THERE IS THE FIRST CAN ON THIS LINE
		    		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
		    		while((sample[0]*100)>5) {
		    			provider.fetchSample(sample, 0);	
				    	}
		    		setSpeeds(0,0);
				   //	detectcolor();
		    		float[] sampleCan4 = new float[3];
					 sampleCan4 = ColorScanner.scanCan();
					 ColorScanner.colorScanner(sampleCan4);	
					 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
					rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
				   	turnL();
				   	provider.fetchSample(sample, 0);
		    		
			    	if((sample[0]*100)<=(odometer.getX()-LLx*TILE_SIZE)) {//IF THERE IS A SECOND CAN ON THIS LINE
			    		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
			    		while((sample[0]*100)>5) {
			    			provider.fetchSample(sample, 0);	
					    	}
			    		setSpeeds(0,0);
					   //	detectcolor();
			    		float[] sampleCan5 = new float[3];
						 sampleCan5 = ColorScanner.scanCan();
						 ColorScanner.colorScanner(sampleCan5);	
						 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
						rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
					   	turnL();
					   	provider.fetchSample(sample, 0);
			    		
				    	if((sample[0]*100)<=(odometer.getX()-LLx*TILE_SIZE)) {//IF THERE IS A THIRD CAN ON THIS LINE
				    		setSpeeds(FORWARD_SPEED,FORWARD_SPEED);
				    		while((sample[0]*100)>5) {
				    			provider.fetchSample(sample, 0);	
						    	}
				    		setSpeeds(0,0);
						   //	detectcolor();
				    		float[] sampleCan6 = new float[3];
							 sampleCan6 = ColorScanner.scanCan();
							 ColorScanner.colorScanner(sampleCan6);	
							 leftMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),true);
							rightMotor.rotate(-convertDistance(Lab5.WHEEL_RAD,5),false);
						   	turnL();
						   	leftMotor.setSpeed(FORWARD_SPEED);
						   	rightMotor.setSpeed(FORWARD_SPEED);
						   	travelTo(LLx*TILE_SIZE,odometer.getY());//GO TO THE OTHER END OF THE LINE
				    		travelTo(LLx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
				    		}
				    	else {
				    		travelTo(LLx*TILE_SIZE,odometer.getY());//GO TO THE OTHER END OF THE LINE
				    		travelTo(LLx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
				    	}
			    		}
			    	else {
			    		travelTo(LLx*TILE_SIZE,odometer.getY());//GO TO THE OTHER END OF THE LINE
			    		travelTo(LLx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);//GO TO CHECK THE NEXT LINE
			    	}
		    		}
		    		else {
		    			travelTo(URx*TILE_SIZE,(LLy+j+1)*TILE_SIZE);// IF THERE'S NO CAN ON THIS LINE, GO UP TO CHECK THE NEXT LINE
		    		}
	    	}
		}
	}
	
	boolean isNavigating(boolean check){ 
		return check; //another thread has called a method and has yet to return
	}
	boolean isSearching(boolean check) {
		return check;
	}
	 private static int convertDistance(double radius, double distance) {
		    return (int) ((180.0 * distance) / (Math.PI * radius));
		  }

	 private static int convertAngle(double radius, double width, double angle) {
		    return convertDistance(radius, Math.PI * width * angle / 360.0);
		  }
	public void run() {
		travelTo(LLx*TILE_SIZE,LLy*TILE_SIZE);
		Sound.beep();
		
		turnTo(0,45);
		Search();
		travelTo(URx*TILE_SIZE,URy*TILE_SIZE);
	}
}

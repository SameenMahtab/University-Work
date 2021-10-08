//package ca.mcgill.ecse211.wallfollowing;

import lejos.hardware.motor.*;

public class BangBangController implements UltrasonicController {

  private final int bandCenter;
  private final int bandwidth;
  private final int motorLow;
  private final int motorHigh;
  private int distance;

  public BangBangController(int bandCenter, int bandwidth, int motorLow, int motorHigh) {
    // Default Constructor
    this.bandCenter = bandCenter;
    this.bandwidth = bandwidth;
    this.motorLow = motorLow;
    this.motorHigh = motorHigh;
    WallFollowingLab.leftMotor.setSpeed(motorHigh); // Start robot moving forward
    WallFollowingLab.rightMotor.setSpeed(motorHigh);
    WallFollowingLab.leftMotor.forward();
    WallFollowingLab.rightMotor.forward();
  }

  @Override
  public void processUSData(int distance) {
    this.distance = distance;
    // TODO: process a movement based on the us distance passed in (BANG-BANG style)
    if(Math.abs(distance-bandCenter)<=(bandwidth)) {   //IF THE DIFFERENCE IS WITHIN THE ACCEPTABLE ERROR, GO STRAIGHT
    	 WallFollowingLab.leftMotor.setSpeed(motorHigh); 
    	 WallFollowingLab.rightMotor.setSpeed(motorHigh);
    	 WallFollowingLab.leftMotor.forward();
    	 WallFollowingLab.rightMotor.forward();
    }
    if(distance<10) {//BEFORE HITTING THE WALL, GO BACKWARDS TO ADJUST IT
   	 WallFollowingLab.leftMotor.setSpeed(0); 
     	 WallFollowingLab.rightMotor.setSpeed(motorHigh+200);
     	 WallFollowingLab.leftMotor.backward();; 
     	 WallFollowingLab.rightMotor.backward();;
 	
   	
   }
    else if(distance-bandCenter<0) {  //TURNING RIGHT
    	WallFollowingLab.leftMotor.setSpeed(motorHigh +200); 
    	WallFollowingLab.rightMotor.setSpeed(0);
    	WallFollowingLab.leftMotor.forward();
    	WallFollowingLab.rightMotor.forward();
    }
    else if(distance-bandCenter>0) { //TURNING LEFT
    	WallFollowingLab.leftMotor.setSpeed(motorLow+20); 
    	WallFollowingLab.rightMotor.setSpeed(motorHigh+20);
    	WallFollowingLab.leftMotor.forward();
    	WallFollowingLab.rightMotor.forward();
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
  
  
}

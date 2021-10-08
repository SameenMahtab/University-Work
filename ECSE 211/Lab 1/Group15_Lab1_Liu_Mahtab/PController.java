//package ca.mcgill.ecse211.wallfollowing;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class PController implements UltrasonicController {

  /* Constants */
  private static final int MOTOR_SPEED = 200;
  private static final int FILTER_OUT = 20;

  private final int bandCenter;
  private final int bandWidth;
  private int distance;
  private int filterControl;

  public PController(int bandCenter, int bandwidth) {
    this.bandCenter = bandCenter;
    this.bandWidth = bandwidth;
    this.filterControl = 0;

    WallFollowingLab.leftMotor.setSpeed(MOTOR_SPEED); // Initalize motor rolling forward
    WallFollowingLab.rightMotor.setSpeed(MOTOR_SPEED);
    WallFollowingLab.leftMotor.forward();
    WallFollowingLab.rightMotor.forward();
  }

  @Override
  public void processUSData(int distance) {

    // rudimentary filter - toss out invalid samples corresponding to null
    // signal.
    // (n.b. this was not included in the Bang-bang controller, but easily
    // could have).
    //
    if (distance >= 255 && filterControl < FILTER_OUT) {
      // bad value, do not set the distance var, however do increment the
      // filter value
      filterControl++;
    } else if (distance >= 255) {
      // We have repeated large values, so there must actually be nothing
      // there: leave the distance alone
      this.distance = distance;
    } else {
      // distance went below 255: reset filter and leave
      // distance alone.
      filterControl = 0;
      this.distance = distance;
    }

    // TODO: process a movement based on the us distance passed in (P style)
    
    
    
 
    if(Math.abs(distance-bandCenter)<=bandWidth) {//IF THE DIFFERENCE IS WITHIN THE ACCEPTABLE ERROR, GO STRAIGHT
   	 WallFollowingLab.leftMotor.setSpeed(MOTOR_SPEED); 
   	 WallFollowingLab.rightMotor.setSpeed(MOTOR_SPEED);
   	 WallFollowingLab.leftMotor.forward();
   	 WallFollowingLab.rightMotor.forward();
   }
    if(distance<10) {//BEFORE HITTING THE WALL, GO BACKWARDS TO ADJUST IT
    	WallFollowingLab.leftMotor.setSpeed(MOTOR_SPEED); 
     	 WallFollowingLab.rightMotor.setSpeed(MOTOR_SPEED);
     	 WallFollowingLab.leftMotor.backward(); 
     	 WallFollowingLab.rightMotor.backward();
    	 WallFollowingLab.leftMotor.setSpeed(0); 
      	 WallFollowingLab.rightMotor.setSpeed(MOTOR_SPEED+230);
      	 WallFollowingLab.leftMotor.backward(); 
      	 WallFollowingLab.rightMotor.backward();
  	
    	
    }
    else if(distance-bandCenter<0) {  //TURNING RIGHT
    	 WallFollowingLab.leftMotor.setSpeed(MOTOR_SPEED+150+calcTurn(distance-bandCenter)); 
       	 WallFollowingLab.rightMotor.setSpeed(0);
       	 WallFollowingLab.leftMotor.forward();
       	 WallFollowingLab.rightMotor.forward();
    }
    else if (distance-bandCenter>0) {  //TURNING LEFT
    	 WallFollowingLab.leftMotor.setSpeed(MOTOR_SPEED); 
       	 WallFollowingLab.rightMotor.setSpeed(MOTOR_SPEED-13+calcTurn(distance-bandCenter));
       	 WallFollowingLab.leftMotor.forward();
       	 WallFollowingLab.rightMotor.forward();
    }
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }
  public int calcTurn(int error) {
	  if (error<0) error=-error;
	  int diff=(int) (4.5*(double)error);
	if (diff>200) diff=200;
	return diff;
	
	  
  }
}




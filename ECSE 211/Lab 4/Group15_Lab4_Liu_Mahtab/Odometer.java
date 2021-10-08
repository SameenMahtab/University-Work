

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Odometer extends Thread {
	
	 //Robot position (x, y, thetha)
	  private double x;
	  private double y;
	  private double theta;
	  //TachoCounts
	  private int nowleftMotorTachoCount;
	  private int nowrightMotorTachoCount;
	  private int lastTachoL;
	  private int lastTachoR;
	  //Motors
	  private EV3LargeRegulatedMotor leftMotor;
	  private EV3LargeRegulatedMotor rightMotor;
	  
	  private static final long ODOMETER_PERIOD = 25; /*odometer update period, in ms*/

	  private Object lock; /*lock object for mutual exclusion*/
	  
	// default constructor
	  public Odometer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
	    this.leftMotor = leftMotor;
	    this.rightMotor = rightMotor;
	    this.x = 0.0;
	    this.y = 0.0;
	    this.theta = 0.0;
	    this.nowleftMotorTachoCount = 0;
	    this.nowrightMotorTachoCount = 0;
	    lock = new Object();
	    //my new variables
	    this.lastTachoL = 0;
	    this.lastTachoR = 0;
	  }
	  
	// run thread
	  public void run() {
	    long updateStart, updateEnd;
	    
	    double distL, distR, deltaD, deltaT, dX, dY, thetaHeading, deltaTinDEG;
	    
	    leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    
	    lastTachoL = leftMotor.getTachoCount(); //get a first reading of tachoCount
	    lastTachoR = rightMotor.getTachoCount();
	    
	    while (true) {
	      updateStart = System.currentTimeMillis();
	      
	      nowleftMotorTachoCount = leftMotor.getTachoCount();
	      nowrightMotorTachoCount = rightMotor.getTachoCount();
	      
	      distL = Math.PI * LocalizationLab.WHEEL_RADIUS * (nowleftMotorTachoCount - lastTachoL)/180; //delta dist. traveled by left wheel
	      distR = Math.PI * LocalizationLab.WHEEL_RADIUS * (nowrightMotorTachoCount - lastTachoR)/180; //delta dist. traveled by right wheel
	      
	      lastTachoL = nowleftMotorTachoCount; //update tachoCount
	      lastTachoR = nowrightMotorTachoCount;
	      
	      deltaD = 0.5 * (distL + distR); //change in distance heading
	      deltaT = (distL-distR)/ LocalizationLab.TRACK; //change in theta heading
	      
	      thetaHeading = Math.toRadians(getTheta()) + deltaT; //theta heading in radians 
	      dX = deltaD * Math.sin(thetaHeading);
	      dY = deltaD * Math.cos(thetaHeading);
	      
	      deltaTinDEG = Math.toDegrees(deltaT); //update change in angle [in degrees]
	      
	      //making sure the range of theta is (0,359.99)
	      if(thetaHeading >= Math.toRadians(360) ){
	    	  deltaTinDEG = deltaTinDEG - 360;
	      }
	      else if(thetaHeading <Math.toRadians(0)){
	    	  deltaTinDEG = deltaTinDEG + 360;
	      }

	      synchronized (lock) {
	        /**
	         * Only update the values of x, y, and theta in this block. Do not perform complex math
	         * 
	         */
	    	
	    	  theta += deltaTinDEG;
	    	  
	    	  x += dX;
	    	  y += dY;
	      }

	      // this ensures that the odometer only runs once every period
	      updateEnd = System.currentTimeMillis();
	      if (updateEnd - updateStart < ODOMETER_PERIOD) {
	        try {
	          Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
	        } catch (InterruptedException e) {
	          // there is nothing to be done here because it is not
	          // expected that the odometer will be interrupted by
	          // another thread
	        }
	      }
	    }
	  }
	  
	  public void getPosition(double[] position, boolean[] update) {
		    // ensure that the values don't change while the odometer is running
		    synchronized (lock) {
		      if (update[0])
		        position[0] = x;
		      if (update[1])
		        position[1] = y;
		      if (update[2])
		        position[2] = theta;
		    }
		  }

		  public double getX() {
		    double result;

		    synchronized (lock) {
		      result = x;
		    }

		    return result;
		  }

		  public double getY() {
		    double result;

		    synchronized (lock) {
		      result = y;
		    }

		    return result;
		  }

		  public double getTheta() {
		    double result;

		    synchronized (lock) {
		      result = theta;
		    }

		    return result;
		  }

		  // mutators
		  public void setPosition(double[] position, boolean[] update) {
		    // ensure that the values don't change while the odometer is running
		    synchronized (lock) {
		      if (update[0])
		        x = position[0];
		      if (update[1])
		        y = position[1];
		      if (update[2])
		        theta = position[2];
		    }
		  }

		  public void setX(double x) {
		    synchronized (lock) {
		      this.x = x;
		    }
		  }

		  public void setY(double y) {
		    synchronized (lock) {
		      this.y = y;
		    }
		  }

		  public void setTheta(double theta) {
		    synchronized (lock) {
		      this.theta = theta;
		    }
		  }

		  /**
		   * @return the leftMotorTachoCount
		   */
		  public int getLeftMotorTachoCount() {
		    return nowleftMotorTachoCount;
		  }

		  /**
		   * @param leftMotorTachoCount the leftMotorTachoCount to set
		   */
		  public void setLeftMotorTachoCount(int leftMotorTachoCount) {
		    synchronized (lock) {
		      this.nowleftMotorTachoCount = leftMotorTachoCount;
		    }
		  }

		  /**
		   * @return the rightMotorTachoCount
		   */
		  public int getRightMotorTachoCount() {
		    return nowrightMotorTachoCount;
		  }

		  /**
		   * @param rightMotorTachoCount the rightMotorTachoCount to set
		   */
		  public void setRightMotorTachoCount(int rightMotorTachoCount) {
		    synchronized (lock) {
		      this.nowrightMotorTachoCount = rightMotorTachoCount;
		    }
		  }

}

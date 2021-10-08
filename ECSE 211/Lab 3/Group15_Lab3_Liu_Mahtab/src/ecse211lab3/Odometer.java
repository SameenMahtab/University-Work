/**
 * This class is meant as a skeleton for the odometer class to be used.
 * 
 * @author Rodrigo Silva
 * @author Dirk Dubois
 * @author Derek Yu
 * @author Karim El-Baba
 * @author Michael Smith
 */

package ecse211lab3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Odometer extends OdometerData implements Runnable {

  private OdometerData odoData;
  private static Odometer odo = null; // Returned as singleton

  // Motors and related variables
  private int leftCount;
  private int rightCount;
  private EV3LargeRegulatedMotor leftMotor;
  private EV3LargeRegulatedMotor rightMotor;
  
  private final double TRACK;
  private final double WHEEL_RAD;
  private double theta;
  private int LastRightTachoCount;
  private int LastLeftTachoCount;
  private double x, y; 

  private double[] position;
  


  private static final long ODOMETER_PERIOD = 25; // odometer update period in ms

  /**
   * This is the default constructor of this class. It initiates all motors and variables once.It
   * cannot be accessed externally.
   * 
   * @param leftMotor
   * @param rightMotor
   * @throws OdometerExceptions
   */
  private Odometer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
      final double TRACK, final double WHEEL_RAD) throws OdometerExceptions {
    odoData = OdometerData.getOdometerData(); // Allows access to x,y,z
                                              // manipulation methods
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;

    // Reset the values of x, y and z to 0
    odoData.setXYT(0, 0, 0);

    this.leftCount = 0;
    this.rightCount = 0;

    this.TRACK = TRACK;
    this.WHEEL_RAD = WHEEL_RAD;

  }

  /**
   * This method is meant to ensure only one instance of the odometer is used throughout the code.
   * 
   * @param leftMotor
   * @param rightMotor
   * @return new or existing Odometer Object
   * @throws OdometerExceptions
   */
  public synchronized static Odometer getOdometer(EV3LargeRegulatedMotor leftMotor,
      EV3LargeRegulatedMotor rightMotor, final double TRACK, final double WHEEL_RAD)
      throws OdometerExceptions {
    if (odo != null) { // Return existing object
      return odo;
    } else { // create object and return it
      odo = new Odometer(leftMotor, rightMotor, TRACK, WHEEL_RAD);
      return odo;
    }
  }

  /**
   * This class is meant to return the existing Odometer Object. It is meant to be used only if an
   * odometer object has been created
   * 
   * @return error if no previous odometer exists
   */

  public synchronized static Odometer getOdometer() throws OdometerExceptions {

    if (odo == null) {
      throw new OdometerExceptions("No previous Odometer exits.");
    }
    return odo;
  }

  /**
   * This method is where the logic for the odometer will run. Use the methods provided from the
   * OdometerData class to implement the odometer.
   */
  // run method (required for Thread)
    double deltaLeft;
    double deltaRight;  
    
    public void run() {
    long updateStart, updateEnd;
    double deltax=0;
    double deltay=0;
    double dtheta;
    double LeftLength;
    double RightLength;
    double Length;
   // double arcLengthL;
    //double arcLengthR;
    leftCount = leftMotor.getTachoCount();
    rightCount = rightMotor.getTachoCount(); 

    while (true) {
      updateStart = System.currentTimeMillis();

      // TODO Calculate new robot position based on tachometer counts
      LastLeftTachoCount = leftMotor.getTachoCount();
      LastRightTachoCount = rightMotor.getTachoCount();
      
      deltaLeft=LastLeftTachoCount- leftCount;
      deltaRight=LastRightTachoCount- rightCount;
      
      LeftLength=deltaLeft*Math.PI/180.0*WHEEL_RAD;
      RightLength=deltaRight*Math.PI/180.0*WHEEL_RAD;
      
      leftCount=LastLeftTachoCount;
      rightCount=LastRightTachoCount;
      
      dtheta=((RightLength-LeftLength)/TRACK)*180/(Math.PI);
      theta=theta+dtheta;
      Length=(LeftLength+RightLength)/2.0;
      
      deltax = Length * Math.sin(theta*Math.PI/180);
      deltay = Length * Math.cos(theta*Math.PI/180);
      
      x=x+deltax;
      y=y+deltay;
      
      // TODO Update odometer values with new calculated values
      odo.update(deltax,deltay, dtheta);

      // this ensures that the odometer only runs once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < ODOMETER_PERIOD) {
        try {
          Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
        } catch (InterruptedException e) {
          // there is nothing to be done
        }
      }
    }
  }
    public void setXData(double Xposition) {
  	  x=Xposition;
    }
    
    public void setYData(double Yposition) {
  	  y=Yposition;
    }
    public void setThetaData(double theta) {
    	this.theta=theta;
    }
    public double getY() {
  	  return y;
    }

    public double getX() {
  	  return x;
    }
    public double getTheta() {
    return theta;
    }
}

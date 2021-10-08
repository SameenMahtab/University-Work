package ca.mcgill.ecse211.lab5;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class LightLocalizer extends Thread {

  private EV3LargeRegulatedMotor leftMotor;
  private EV3LargeRegulatedMotor rightMotor;
  private EV3ColorSensor colorSensor;
  private EV3UltrasonicSensor ultraSensor;
  private Odometer odometer;

  /**
   * @param FORWARD_SPEED rotate speed
   * @param SENSOR_DISTANCE the distance between light sensor and center of robot
   * @param FILTER_THRESHOLD The threshold value used by the differential filter
   */
  private static final int FORWARD_SPEED = 100;
  private static final double SENSOR_DISTANCE = 10.75;
  private static final double FILTER_THRESHOLD = 0.1;

  /**
   * @param deltaX the x displacement required
   * @param deltaY the y displacement required
   * @param deltaTheta the angle difference required
   * @param reflection the RED value read by the sensor
   * @param reflectionRef the previous reading from sensor, used for differential filter
   * @param blackLine the number of black lines detected
   */
  private double deltaX;
  private double deltaY;
  private double theta;
  private double deltaTheta;
  private double distance;
  private float[] RGB = new float[3];
  private float reflection;
  private int blackLine = 0;

  /**
   * LightLocalizer constructor
   * 
   * @param leftMotor
   * @param rightMotor
   * @param colorSensor
   */
  public LightLocalizer(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
      EV3ColorSensor colorSensor) {
    this.colorSensor = colorSensor;
    this.rightMotor = rightMotor;
    this.leftMotor = leftMotor;
    try {
      this.odometer = Odometer.getOdometer();
    } catch (OdometerExceptions e) {
      e.printStackTrace();
    }
  }

  /**
   * Light localization routine method
   */
  public void localization() {

    UltrasonicLocalizer robot = new UltrasonicLocalizer(leftMotor, rightMotor, ultraSensor);

    // initializing constants and array
    double[] theta = new double[4];
    double thetaY;
    double thetaX;
    double thetaA;
    double finalTheta;
    double x, y;

    // Adjust the orientation and position of the robot, such that sensor sees the line
    robot.turnTo(45);
    odometer.setTheta(0);
    robot.goStraight(14);


    // turn leftward and count the grid lines
    leftMotor.setSpeed(FORWARD_SPEED);
    rightMotor.setSpeed(FORWARD_SPEED);
    leftMotor.backward();
    rightMotor.forward();


    while (blackLine < 4) {

      colorSensor.getRGBMode().fetchSample(RGB, 0);
      reflection = RGB[0];


      if (Math.abs(reflection) < FILTER_THRESHOLD) {
        Sound.beep();
        theta[blackLine] = odometer.getTheta();

        // short pause to eliminate false detection
        try {
          Thread.sleep(200);
        } catch (InterruptedException ex) {
        }
        blackLine++;
      }
    }

    // The robot should have completed a full circle by now
    robot.turnTo(0);
    Sound.buzz();

    thetaY = Math.abs((theta[2] - theta[0]) / 2);
    thetaX = Math.abs((theta[3] - theta[1]) / 2);
   
    thetaA = 270 + thetaY - (theta[0]);

    // manipulation on thetaA
    if (thetaA >= 0) {
      finalTheta = thetaA;
    } else {
      finalTheta = 360 + thetaA;
    }

    // The sensor is not on the center of the robot, therefore calculating the robot's position
    x = -SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaX));
    y = SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaY));

    // update odometer
    odometer.setX(x);
    odometer.setY(y);
    odometer.setTheta(finalTheta);

    // Localize
    travelTo(2, 5);
    Sound.beep();
    robot.turnTo(10.0);
    odometer.setX(0);
    odometer.setY(0);
    odometer.setTheta(0);
    
  }

	/**
	 * Makes the robot travel to the absolute field location (targetx, targety)
	 * calls turnTo()
	 * 
	 * @param x
	 * @param y
	 * @return void
	 */

  public void travelTo(double x, double y) {

    UltrasonicLocalizer robot = new UltrasonicLocalizer(leftMotor, rightMotor, ultraSensor);

    deltaX = x - odometer.getX();
    deltaY = y - odometer.getY();
    distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

    // further manipulation on the angle before sending to the motors

    theta = Math.atan2(deltaX, deltaY) * 180 / Math.PI;

    deltaTheta = theta - odometer.getTheta();   
    robot.turnTo(deltaTheta);

    robot.goStraight(distance);

  }
}

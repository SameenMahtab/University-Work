/*
 * OdometryCorrection.java
 */
//package ca.mcgill.ecse211.odometer;
package ecse211lab3;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.sensor.EV3ColorSensor;

public class OdometryCorrection implements Runnable {
	
  private static final long CORRECTION_PERIOD = 10;
  private Odometer odometer;
  int countLines;
  private float referenceBrightness;
  double diffThreshold=3.42;
  boolean XCorrection, YCorrection; 
  private static final double TILE_SIZE = 30.48;
  private static final EV3ColorSensor colorSensor = new EV3ColorSensor(LocalEV3.get().getPort("S1"));

  double firstX;
  double firstY;

  
  private float[] RGBValues = new float[3];
  private float brightness;
  
  private boolean reachedBlackLine = false;
  /**
   * This is the default class constructor. An existing instance of the odometer is used. This is to
   * ensure thread safety.
   * 
   * @throws OdometerExceptions
   */
  public OdometryCorrection() throws OdometerExceptions {

    this.odometer = Odometer.getOdometer();

  }

  /**
   * Here is where the odometer correction code should be run.
   * 
   * @throws OdometerExceptions
   */
  // run method (required for Thread)
  public void run() {
    long correctionStart, correctionEnd;

    colorSensor.getRedMode().fetchSample(RGBValues,0);
    referenceBrightness = (RGBValues[0]+RGBValues[1]+RGBValues[2]);

    while (true) {
      correctionStart = System.currentTimeMillis();

      colorSensor.getRedMode().fetchSample(RGBValues,0);

      brightness = (RGBValues[0] + RGBValues[1]+ RGBValues[2]);
      
      if(Math.abs(brightness-referenceBrightness)*100 > diffThreshold) {
    	  reachedBlackLine = true;
      }

      else {
    	 reachedBlackLine = false;
      }

      if(reachedBlackLine == true) {
    	  correctOdometer();
    	  Sound.beep();
      }

      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        try {
          Thread.sleep(CORRECTION_PERIOD - (correctionEnd - correctionStart));
        } catch (InterruptedException e) {
          // there is nothing to be done here
        }
      }
    }
  }
  
  
  public void correctOdometer() {

	  countLines++;
	  if(countLines == 1) {
		  odometer.setY(0);
		  odometer.setXData(0);
	  }
	  else if(countLines<4) {
		  odometer.setY((countLines-1)*30.48);
		  odometer.setXData((countLines-1)*30.48);
	  }
	  else if(countLines==4) {
		  odometer.setX(0);
	  }
	  else if(countLines<7) {
			  odometer.setX((countLines-4)*30.48);
			  odometer.setXData((countLines-4)*30.48);
	 }
	  else if(countLines<10) {
		 odometer.setY((9-countLines)*30.48);
		 odometer.setYData((9-countLines)*30.48);
	  }
	  else if(countLines==10) {
		  firstY=(odometer.getY());
		  odometer.setX(30.48*2);
		  odometer.setXData(30.48*2);
	  }
	  else if(countLines <13) {
		  odometer.setX(30.48*(12-countLines));
		  odometer.setXData(30.48*(12-countLines));
	  }
	  
  }
  
}

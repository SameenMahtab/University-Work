/*

 * OdometryCorrection.java
 */
package ca.mcgill.ecse211.lab5;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class ColorScanner  {
	
	private SampleProvider usDistance;
	private float[] usData;
  
	static EV3ColorSensor light =  
	      new EV3ColorSensor(LocalEV3.get().getPort("S2"));//creating instance of a color sensor  
  
	static SampleProvider lightStatus = light.getMode("RGB"); //creating instance of a sample provider object in the measurement mode RGB
  
	static float [] sampleLight = new float[lightStatus.sampleSize()];	//creating array to receive each r,g,b values from the color sensor
  
	final static EV3MediumRegulatedMotor arm=
	      new EV3MediumRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
	 * ColorScanner constructor
	 */
	public ColorScanner (SampleProvider usDistance) {
		
		this.usDistance = usDistance;
		this.usData = new float[usDistance.sampleSize()];
				
	}
      
      public static float getRegMean(float[] colorArray) {
    	  float sum=0;
          for (float val : colorArray) 
          	sum += val;
          return sum/colorArray.length;
      }
      
      public float getStdDeviation(float[] colorArray, float mean) {
      	float sd=0;
      	for(float val: colorArray) 
      		sd += Math.pow(val - mean, 2);
      	  
      	return (float) Math.sqrt(sd/colorArray.length);            
      }
      
      /**
       * This method scans a can of unknown color and calculates its mean readings for each channel 
       * 
       * @return float[]
       */
      public static float[] scanCan() {
    	  
    	  //return array where the sample can's means will be stored
    	 float[] ret = new float[3]; 
    	 
    	  //Setting rotating arm acceleration and speed
    	  arm.setAcceleration(500);
    	  arm.setSpeed(100);

    	  //Arrays where we'll store each color reading from the sample separated by channel (RGB)
    	  float[] sampleR= new float[15];
          float[] sampleG= new float[15];
          float[] sampleB= new float[15];
          
          //Scan the sample can
          for(int a=0;a<sampleR.length;a++) {
              lightStatus.fetchSample(sampleLight, 0); // acquire data

    			sampleR[a]=sampleLight[0];//store in array based on channel
    			sampleG[a]=sampleLight[1];
    			sampleB[a]=sampleLight[2];
    			arm.rotate(-14);//take a measurement every 10 degrees
    		}
          
          arm.rotate(210);//rotate arm back to original position
          
          //Calculate the sample means for each channel
          float SmeanR = getRegMean( sampleR );
          float SmeanG = getRegMean( sampleG );
          float SmeanB = getRegMean( sampleB );
          
          //Get final standardized means using formula from lab instructions and add them to return array
          ret[0]= (float) (SmeanR/Math.sqrt(SmeanR*SmeanR + SmeanG*SmeanG + SmeanB*SmeanB));
          ret[1]= (float) (SmeanG/Math.sqrt(SmeanR*SmeanR + SmeanG*SmeanG + SmeanB*SmeanB));
          ret[2]= (float) (SmeanB/Math.sqrt(SmeanR*SmeanR + SmeanG*SmeanG + SmeanB*SmeanB));
         
          return ret;
      }
      
           
     
      /**
       *This method is called in the run method to determine a can's color
       * 
       * @param ColorArray
       * @return int
       */
      public static int colorScanner(float[] ColorArray) {
    	            
    	//Yellow Can average mean values for each channel 
          //Determined by experimentation
          float YRmi = (float)0.73289484;
          float YGmi = (float)0.5762072;
          float YBmi = (float)0.35795692; 
          
        //Blue Can average mean values for each channel 
          //Determined by experimentation
          float BRmi = (float)0.349945;
          float BGmi = (float)0.62349963;
          float BBmi = (float)0.69617367;
          
        //Red Can average mean values for each channel 
          //Determined by experimentation
          float RRmi = (float)0.8441728;
          float RGmi = (float)0.40596217;
          float RBmi = (float)0.3448022; 
          
        //Green Can average mean values for each channel 
          //Determined by experimentation
          float GRmi = (float)0.3981819;
          float GGmi = (float)0.8220738;
          float GBmi = (float)0.39593598;
                   
          //Sample Can mean values
          float SRmi = ColorArray[0];
          float SGmi = ColorArray[1];
          float SBmi = ColorArray[2];
          
          //Diff array 
          float[] diff = new float[4];
          
          //Yellow
          diff[0] = (float) Math.sqrt(Math.pow((SRmi-YRmi),2)+Math.pow((SGmi-YGmi),2)+Math.pow((SBmi-YBmi),2));
          //Red
          diff[1] = (float) Math.sqrt(Math.pow((SRmi-RRmi),2)+Math.pow((SGmi-RGmi),2)+Math.pow((SBmi-RBmi),2));

          //Green
          diff[2] = (float) Math.sqrt(Math.pow((SRmi-GRmi),2)+Math.pow((SGmi-GGmi),2)+Math.pow((SBmi-GBmi),2));

          //Blue
          diff[3] = (float) Math.sqrt(Math.pow((SRmi-BRmi),2)+Math.pow((SGmi-BGmi),2)+Math.pow((SBmi-BBmi),2));

          int index = 0;
          float min = diff[index];
          for (int i=1; i<diff.length; i++){
              if (diff[i] < min ){
                  min = diff[i];
                  index = i;
              }           
          }
          
          if(index==0) {
        	  System.out.println("Yellow");
          }
          else if(index==1) {
        	  System.out.println("Red");
          }
          else if(index==2) {
        	  System.out.println("Green");
          }
          else if(index==3) {
        	  System.out.println("Blue");
          }
          
    	  if(index == Lab5.TARGET) {
    		  Sound.beep();
    	  }
    	  else {
    		  Sound.beep();
    		  Sound.beep();
    	  }
    		  
    	  
          return index;
      }
     
      /**
  	 * This method gets Distance readings from the usSensor and casts them to int
  	 * 
  	 * @return integer
  	 */
  	public int getDistance() {
  		usDistance.fetchSample(usData, 0); // acquire data
  		return (int) (usData[0] * 100); // extract from buffer, cast to int
  	}
      
  
  
        
  
}

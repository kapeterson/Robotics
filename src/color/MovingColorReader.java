package color;

import java.util.HashMap;
import java.util.Map;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class MovingColorReader {

	/**
	 * @param args
	 */
	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.B;
	
	public static void initMotors(){
		  leftMotor.resetTachoCount();
		  rightMotor.resetTachoCount();
		    
		  leftMotor.rotateTo(0);
		  rightMotor.rotateTo(0);
		  
		  leftMotor.setSpeed(300);
		  rightMotor.setSpeed(300);
		  
		  leftMotor.setAcceleration(800);
		  rightMotor.setAcceleration(800);
	}
	
	
	public static void moveForward(int degrees){
		  leftMotor.rotate(degrees, true);
		  rightMotor.rotate(degrees);
	}
		
	// Main 
	public static void waitForSensorPress(EV3TouchSensor ts){
		
		  float[] touchsample = new float[1];
		  SampleProvider tProvider = ts.getTouchMode();
		  tProvider.fetchSample(touchsample, 0);

		  int cnt = (int)touchsample[0];
		  
		  System.out.println("Waiting for sensor push to continue......");
		  while ( cnt < 1){
			  
			  Delay.msDelay(250);
			  tProvider.fetchSample(touchsample, 0);

			  cnt = (int)touchsample[0];
		  }
		  
	}
	
	public static int getColorID(EV3ColorSensor cs){
		SampleProvider lightProvider = cs.getColorIDMode();
		
		float[] sample = { 0.0f };
  		lightProvider.fetchSample(sample, 0);
  		return (int)sample[0];
  		
	}
	
	// Main file
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Map<Integer, String> colorMap = new HashMap<Integer, String>();

		colorMap.put(13, "BROWN");
		colorMap.put(0, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		
		Port port = LocalEV3.get().getPort("S4");
		Port touchport = LocalEV3.get().getPort("S2");


		EV3ColorSensor cs = new EV3ColorSensor(port);
		//SampleProvider lightProvider = cs.getColorIDMode();
		
		EV3TouchSensor ts = new EV3TouchSensor(touchport);
		
		
		  System.out.println("Going to take 4 initial readings");
		  Delay.msDelay(1000);
		  
		  for( int j = 0; j < 4; j++){

				int thissample = getColorID(cs);
				
				if ( colorMap.containsKey(thissample)){
					System.out.format("Initial Sample:  Color-%s  Value-%d\n", colorMap.get(thissample), thissample );
				} else {
					System.out.println("Error reading color  " +  thissample);
				}
		  		Delay.msDelay(1000);
		  }
		  

		  waitForSensorPress(ts);
		  
		  System.out.println("Sensor push received\n");
		  
		  int fwd =(int)(360 * 1.5);
	  
		  for ( int i = 0; i < 3; i++){
			  
			  System.out.println("\n\nPerforming movement " + i);

			  
			  moveForward(fwd);
			  
			  Delay.msDelay(1000);
			  System.out.println("Completed movement " + i + " -  read 4 samples \n\n");
			  		
			  
			  Delay.msDelay(500);
			  
			  int nummoves = 4;
			  	for( int j = 0; j < nummoves; j++){

					int thissample = getColorID(cs);

					
					if ( colorMap.containsKey(thissample)){
						System.out.format("SAMPLE Value = %s with value %d\n", colorMap.get(thissample), thissample );
					} else {
						System.out.println("Error reading color  " + thissample);
					}
			  				
			  		Delay.msDelay(500);
			  	}
			  	
			  System.out.println("Completed measurements for movement " + i +"\n\n*************\n\n");
			  

			  if ( i < (nummoves - 2))
				  waitForSensorPress(ts);

			  Delay.msDelay(1000);

		  }
		  
		  System.out.println("\n\n\nDONE\n\n\n");

		  cs.close();
		  ts.close();	
	}

}

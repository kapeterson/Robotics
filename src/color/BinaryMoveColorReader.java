package color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class BinaryMoveColorReader {

	/**
	 * @param args
	 */
	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.B;
	
	public static enum Movement {
		FORWARD, BACKWARD, RIGHT, LEFT
	}
	
	public static void waitForSensorPush(EV3TouchSensor ts){
		float[] touchsample = new float[1];
		SampleProvider sProvider = ts.getTouchMode();

		int quit = 0;
		System.out.println("Waiting for sensor push ......");

		while ( quit < 1){
			sProvider.fetchSample(touchsample, 0);
			quit = (int)touchsample[0];
			Delay.msDelay(250);
		}
	}
	
	public static void MoveLeft(){
    	leftMotor.rotateTo(255, true);
    	rightMotor.rotateTo(-255);
    	
    	Delay.msDelay(2000);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Movement> movements = new ArrayList<Movement>();
		movements.add(Movement.FORWARD);
		movements.add(Movement.FORWARD);
		movements.add(Movement.LEFT);
		movements.add(Movement.FORWARD);

		
		Map<Integer, String> colorMap = new HashMap<Integer, String>();

		colorMap.put(13, "BROWN");
		colorMap.put(0, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		colorMap.put(3, "GREEN");

		Port port = LocalEV3.get().getPort("S4");
		Port touchport = LocalEV3.get().getPort("S2");

		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		lcd.setFont(Font.getSmallFont());

		lcd.clear();
		EV3ColorSensor cs = new EV3ColorSensor(port);
		//SampleProvider lightProvider = cs.getRedMode();
		SampleProvider lightProvider = cs.getColorIDMode();
		
		EV3TouchSensor ts = new EV3TouchSensor(touchport);
		//SampleProvider gProvider = ts.getTouchMode();
		
		//lcd.drawString("Hey dude we good", 0, 20, GraphicsLCD.BASELINE);
		
		  System.out.println("Going to take 4 initial readings");
		  Delay.msDelay(1000);
		  
		  for( int j = 0; j < 4; j++){
		  		float[] sample = new float[1];
			

			
		  		lightProvider.fetchSample(sample, 0);
		  		//lcd.drawString("Sample = " + sample[0], 0, 20, GraphicsLCD.BASELINE);
				int thissample = (int)sample[0];
				
				if ( colorMap.containsKey(thissample)){
					System.out.format("Initial Sample:  Color-%s  Value-%d\n", colorMap.get(thissample), thissample );
				} else {
					System.out.println("Error reading color  " + sample[0]);
				}
		  		Delay.msDelay(1000);
		  }

		  //waitForSensorPush(ts);
		  
		  int fwd =(int)(360 * 1.5);

		  leftMotor.resetTachoCount();
		  rightMotor.resetTachoCount();
		    
		  leftMotor.rotateTo(0);
		  rightMotor.rotateTo(0);
		  
		  leftMotor.setSpeed(300);
		  rightMotor.setSpeed(300);
		  
		  leftMotor.setAcceleration(800);
		  rightMotor.setAcceleration(800);
		  
		  for ( Movement movement : movements){
			  
			  System.out.println("\n\nPerforming movement " );

			  
			  switch ( movement){
			  	
			  	case FORWARD:
			  		System.out.println("Move foward");
					leftMotor.rotate(fwd, true);
					rightMotor.rotate(fwd);			  		
			  		break;
			  	case LEFT:
			  		System.out.println("Left");
			  		MoveLeft();
			  		break;
			  	case RIGHT:
			  		System.out.println("Right");
			  		break;
				  
			  	
			  }

			  
			  Delay.msDelay(1000);
			  System.out.println("Completed movement "  + " -  read 4 samples \n\n");
			  		
			  
			  	Delay.msDelay(1000);
			  
			  	for( int j = 0; j < 4; j++){
			  		float[] sample = new float[1];
				

				
			  		lightProvider.fetchSample(sample, 0);
			  		//lcd.drawString("Sample = " + sample[0], 0, 20, GraphicsLCD.BASELINE);
					int thissample = 0;
					thissample = (int)sample[0];
					
					if ( colorMap.containsKey(thissample)){
						System.out.format("SAMPLE Value = %s with value %d\n", colorMap.get(thissample), thissample );
					} else {
						System.out.println("Error reading color  " + sample[0]);
					}
					
					//lcd.drawString("Sample = " + sample[0], 0, 20, GraphicsLCD.BASELINE);
					//System.out.println("Sample " + sample[0]);					
			  		//System.out.println("Sample " + j + " =  " +sample[0]);
			  				
			  		Delay.msDelay(500);
			  	}
			  	
			  System.out.println("Completed measurements for movement \n\n*************\n\n");
			  Delay.msDelay(1000);
			  waitForSensorPush(ts);

		  }
		  System.out.println("\n\n\nDONE\n\n\n");

		  cs.close();
		  ts.close();
	}
}

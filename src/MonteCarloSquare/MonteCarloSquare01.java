package MonteCarloSquare;

import java.util.HashMap;
import java.util.Map;

import kp.lego.movement.Movement;
import kp.lego.movement.Movements;

import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class MonteCarloSquare01 {

	/**
	 * @param args
	 */
	public static final float track = 5.0f;		// 5 inch between wheel centers
	public static final float wheelD = 1.7f; 		// 1.7 Inch Wheel Diameter = 4.32 cm
	public static final float wheelWidth = .86f; 	// .86 Inch / 2.2 cm
	public static final String ULTRASOUND_PORT = "S1";
	
	public static Port UltrasoundPort;
	public static EV3UltrasonicSensor us;
	public static EV3ColorSensor cs;
	
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    
    public static double[] grid = new double[10];
    public static String[] colors = new String[10];
    
    public static void printGrid(){
    	for ( int i = 0; i < grid.length;i++){
    		System.out.println("["+i+"] = " + grid[i]);
    	}
    }
    
	public static void main(String[] args) {
		
		colors[0] = "WHITE";
		colors[1] = "RED";
		colors[2] = "WHITE";
		colors[3] = "RED";
		colors[4] = "WHITE";
		colors[5] = "RED";
		colors[6] = "RED";
		
		colors[7] = "WHITE";
		colors[8] = "WHITE";
		colors[9] = "RED";

		
		double mError = .00001f;
		
		double initP = 1.0f/grid.length;
		
		for( int i = 0; i < grid.length; i++){
			grid[i] = initP;
		}
		
		
		Map<Integer, String> colorMap = new HashMap<Integer, String>();
		
		colorMap.put(0, "RED");
		colorMap.put(13, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		
		Sound.setVolume(100);
		//Sound.buzz();
		//Delay.msDelay(3000);
		//Sound.playTone(400, 5000);
		
		try {
			Port colorPort = LocalEV3.get().getPort("S4");
			cs = new EV3ColorSensor(colorPort);
			SampleProvider colorProvider = cs.getColorIDMode();
	
			
			UltrasoundPort =  LocalEV3.get().getPort(ULTRASOUND_PORT);
			us = new EV3UltrasonicSensor(UltrasoundPort);
			SampleProvider ultrasonicSampler = us.getDistanceMode();
			
			float[] colorsample = new float[1];
			colorProvider.fetchSample(colorsample, 0);
			System.out.println("Sample " + colorsample[0]);
			int colorKey = (int) colorsample[0];
			
			
			// Update probabilities based on first Color Sample
			String initSampleColor = colorMap.get(colorKey);
			
			
			System.out.println("INIT SAMPLE: " + initSampleColor);
			
			double sum = 0.0d;
			
			for ( int i = 0; i < grid.length; i++){
				
				if ( colors[i].equals(initSampleColor)){
					grid[i] = grid[i] * ( 1.00f - mError);
				} else {
					grid[i] = grid[i] * ( mError);
				}
				sum += grid[i];
	
			}
			
			//System.out.println("Total sum = " + sum);
			
			
			// normalize now
			for ( int i = 0; i < grid.length; i++)
				grid[i] = grid[i]/sum;
			
			//Delay.msDelay(5000);
			
			System.out.println("******");
			
			
			printGrid();
			Delay.msDelay(5000);

			// TODO Auto-generated method stub
			leftMotor.setSpeed(200);
			rightMotor.setSpeed(200);
			
			float desiredDistance = 6.0f;
			
			double oneRotationMovement = Math.PI * wheelD;
			//System.out.println("One rotation = " + oneRotationMovement);
			
			double turns =  desiredDistance / oneRotationMovement;
			//System.out.println("Need to do " + turns + " rotations");
			
			double degrees = turns * 360;
			int turndegrees = 230;
	
			float[] distancesample = new float[1];
			ultrasonicSampler.fetchSample(distancesample, 0);
			float dis = distancesample[0];
			//System.out.println("DISTANCE: " + dis);		
	
			if ( dis < .1){
				//System.out.println("Rotate left son");
				Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
				Delay.msDelay(2000);
			}
			
			
			
			boolean localized = false;
			
			for ( int iterations = 0; iterations < 20; iterations++){
				
				// Update the grid probabilities first.
				
				double tempP[] = new double[grid.length];
				
				for ( int j = 0; j<grid.length;j++){
					int newIndex = (j + 1) % ( grid.length);
					tempP[newIndex] = grid[j];
					//System.out.println("Moving from " + j + " to " + newIndex);
					
				}
				
				// now update original
				for ( int j = 0; j<grid.length;j++){
					grid[j] = tempP[j];
					//System.out.println("Moving from " + j + " to " + newIndex);
					
				}
				
				//System.out.println("*****MOVED GRID*****");
				//printGrid();
				
				Movements.MoveForward(leftMotor, rightMotor, (int)degrees);
				Delay.msDelay(2000);
				
				
				
				colorProvider.fetchSample(colorsample, 0);
				//System.out.println("Sample " + colorsample[0]);
				int thissample = (int)colorsample[0];
				
				
				// Update probabilities based on first Color Sample
				String sampleColor = colorMap.get(thissample);
				
				sum = 0.0d;
				System.out.println("NEW SAMPLE: " + sampleColor);
				
				for ( int j = 0; j < grid.length; j++){
					
					if ( colors[j].equals(sampleColor)){
						grid[j] = grid[j] * ( 1 - mError);
					} else {
						grid[j] = grid[j] * ( mError);
					}
					sum += grid[j];
	
				}
				

				
				// normalize now
				double maxP = 0.0d;
				int localIndex = -1;
				for ( int j = 0; j < grid.length; j++) {
					grid[j] = grid[j]/sum;
					if (grid[j] > maxP) {
						maxP = grid[j];
						localIndex = j;
					}
					
				}
				
				if ( maxP > 0.825d && !localized){
					System.out.println("LOCALIZED TO " + localIndex + " at index " + localIndex);
					Sound.playTone(900, 2000);
					//Delay.msDelay(4000);
					localized = true;
				} else {
					System.out.println("Highst p = " + maxP + " at index " + localIndex);
					
				}
				
				if ( localized && localIndex == 0 && grid[0] > .825d){
					System.out.println("We home brah!!!");
					Sound.playTone(1200, 1000);
					Delay.msDelay(1000);
					Sound.playTone(1200, 1000);
					Delay.msDelay(1000);
					Sound.playTone(1200, 1000);
					iterations = 1000;
					Movements.RotateLeft(leftMotor, rightMotor, turndegrees*10);
						
				}
				
				System.out.println("*********UPDATE P******");
				printGrid();
				Delay.msDelay(5000);

			
				distancesample = new float[1];
				ultrasonicSampler.fetchSample(distancesample, 0);
				dis = distancesample[0];
				
				if ( dis < .1){
					Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
					Delay.msDelay(500);
				}
				
				System.out.println("**************");
			}
	
			cs.close();
			us.close();
			leftMotor.close();
			rightMotor.close();
			System.out.println("************");
			printGrid();
			
		} catch ( Exception err ) {
			cs.close();
			us.close();
			leftMotor.close();
			rightMotor.close();
		}
	}

}

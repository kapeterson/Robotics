package MonteCarloSquare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kp.lego.movement.Movement;
import kp.lego.movement.Movements;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class MonteCarlo02 {
	
	public static final float track = 5.0f;		// 5 inch between wheel centers
	public static final float wheelD = 1.7f; 		// 1.7 Inch Wheel Diameter = 4.32 cm
	public static final float wheelWidth = .86f; 	// .86 Inch / 2.2 cm
	
	public static final String TOUCH_PORT = "S2";
	public static final String ULTRASOUND_PORT = "S1";
	
	public static Port UltrasoundPort;
	public static Port touchport;

	public static EV3TouchSensor ts;
	public static EV3UltrasonicSensor us;
	public static EV3ColorSensor cs;
	public static SampleProvider ultrasonicSampler;
	
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    
    public static double[] grid = new double[10];
    public static String[] colors = new String[10];
    
    public static SampleProvider colorProvider;
	static Map<Integer, String> colorMap = new HashMap<Integer, String>();
	static double mError = .00001f;
	static int turndegrees = 230;
	static double forwardDegrees;
	
	public static int GOAL = 0;
	public static double confidence = 0.9f;
	
	public static final int NUMBER_OF_LOCALIZATIONS = 1;
	
    public static void printGrid(){
    	for ( int i = 0; i < grid.length;i++){
    		System.out.println("["+i+"] = " + grid[i]);
    	}
    }
    
    
    public static void moveGrid(){
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
    }
    
    public static double reSample(){
    	
    	float[] colorsample = new float[1];
		colorProvider.fetchSample(colorsample, 0);
		//System.out.println("Sample " + colorsample[0]);
		int thissample = (int)colorsample[0];
		
		
		// Update probabilities based on first Color Sample
		String sampleColor = colorMap.get(thissample);
		
		double sum = 0.0d;
		System.out.println("NEW SAMPLE: " + sampleColor);
		
		for ( int j = 0; j < grid.length; j++){
			
			if ( colors[j].equals(sampleColor)){
				grid[j] = grid[j] * ( 1 - mError);
			} else {
				grid[j] = grid[j] * ( mError);
			}
			sum += grid[j];

		}
		
		return sum;

    }
    
    public static void playComplete(){
		System.out.println("We home brah!!!");
		printGrid();
		Sound.playTone(1200, 1000);
		Delay.msDelay(1000);
		Sound.playTone(1200, 1000);
		Delay.msDelay(1000);
		Sound.playTone(1200, 1000);
		Movements.RotateLeft(leftMotor, rightMotor, turndegrees*11);
    }
    
    public static float getUltrasoundSample(){
		float[] distancesample = new float[1];
		ultrasonicSampler.fetchSample(distancesample, 0);
		return distancesample[0];
		
    }
    
    public static void closePorts(){
		cs.close();
		us.close();
		ts.close();
		leftMotor.close();
		rightMotor.close();
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
		
		Delay.msDelay(1000);
	}
	
	
	public static void doMonteCarlo(){
		boolean localized = false;
		float dis = 0.0f;
		double lastMaxP = 0.0f;
		System.out.flush();
		for ( int iterations = 0; iterations < 20; iterations++){
			
			moveGrid();

			System.out.flush();
			System.out.println("**************");

			Movements.MoveForward(leftMotor, rightMotor, (int)forwardDegrees);
			Delay.msDelay(2000);
			
			
			// resample, update, and normalize
			double sum = reSample();
			double maxP = 0.0d;
			int localIndex = -1;
			
			for ( int j = 0; j < grid.length; j++) {
				grid[j] = grid[j]/sum;
				if (grid[j] > maxP) {
					maxP = grid[j];
					localIndex = j;
				}
				
			}
	
			
			
			if ( maxP > confidence && !localized){
				System.out.println("LOCALIZED TO " + localIndex + " at index " + localIndex);
				Sound.playTone(900, 2000);
				//Delay.msDelay(4000);
				localized = true;
			}
			
			if ( maxP < confidence && localized){
				System.out.println("We lost confidence and Localization... what happened bro??!??");
				localized = false;
			}
			

	

			
			dis = getUltrasoundSample();
			if ( dis < .1){
				Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
				Delay.msDelay(500);
			}
			
			if ( maxP < lastMaxP){
				System.out.println("We lost some confidence");
				Sound.playTone(1300, 500);
				Delay.msDelay(500);
				Sound.playTone(1300, 500);
				Delay.msDelay(500);
				Sound.playTone(1300, 500);
				localized = false;

			}
			
			if ( localized && localIndex == GOAL && grid[GOAL] > confidence  ){
				playComplete();
				iterations = 1000;
				
			}
			
			
			lastMaxP = maxP;
			System.out.println("MAXP: " + maxP);
			
			if ( maxP == 0.0){
				System.out.println("ERROR: Cannot recover from 0 probability");
				iterations = 1000;
			}
			
			printGrid();
			Delay.msDelay(1500);
		}
	}
	
	
	public static void initGrid(){
		double initP = 1.0f/grid.length;
		
		for( int i = 0; i < grid.length; i++){
			grid[i] = initP;
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

		confidence = 0.98f;		// CONFIDENCE FOR LOCALIZATION
		GOAL = 0;				// GOAL SQUARE
		mError = 0.0;			// MEASUREMENT ERROR
		
	
		
		colorMap.put(0, "RED");
		colorMap.put(13, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		
		Sound.setVolume(100);

		
		try {
			Port colorPort = LocalEV3.get().getPort("S4");
			cs = new EV3ColorSensor(colorPort);
			colorProvider = cs.getColorIDMode();
	
			touchport = LocalEV3.get().getPort(TOUCH_PORT);
			UltrasoundPort =  LocalEV3.get().getPort(ULTRASOUND_PORT);
			
			us = new EV3UltrasonicSensor(UltrasoundPort);
			ts =  new EV3TouchSensor(touchport);
			ultrasonicSampler = us.getDistanceMode();
			
			float[] colorsample = new float[1];
			colorProvider.fetchSample(colorsample, 0);
			System.out.println("Sample " + colorsample[0]);
			
			
			
			// Update probabilities based on first Color Sample

			


			// TODO Auto-generated method stub
			leftMotor.setSpeed(200);
			rightMotor.setSpeed(200);
			
			float desiredDistance = 6.0f;									// Distance we want the robot to move forward
			double oneRotationMovement = Math.PI * wheelD;					// Distance moved in 1 rotation
			double turns =  desiredDistance / oneRotationMovement;			// number of turns needed for desired distance
			forwardDegrees = turns * 360;									// Degrees needed to move desired distance
	
			float[] distancesample = new float[1];
			ultrasonicSampler.fetchSample(distancesample, 0);
			float dis = distancesample[0];
			//System.out.println("DISTANCE: " + dis);		
	
			if ( dis < .1){
				//System.out.println("Rotate left son");
				Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
				Delay.msDelay(2000);
			}
			
			
			
			for ( int x = 0; x < NUMBER_OF_LOCALIZATIONS; x++){
				
				initGrid();
				System.out.println("INITIAL P:");
				printGrid();
				
				waitForSensorPush(ts);
				double sum = reSample();
				
				System.out.println("AFTER INITIAL SAMPLE P:");
				printGrid();
				Delay.msDelay(2000);
				
				// normalize now
				for ( int i = 0; i < grid.length; i++)
					grid[i] = grid[i]/sum;
			
				
				doMonteCarlo();
			}
			
	

			closePorts();
			
		} catch ( Exception err ) {
			
			closePorts();

		}
	}
}

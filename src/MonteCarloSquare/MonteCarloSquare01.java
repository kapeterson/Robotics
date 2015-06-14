package MonteCarloSquare;

import java.util.HashMap;
import java.util.Map;

import kp.lego.movement.Movement;
import kp.lego.movement.Movements;

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

    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    
    
	public static void main(String[] args) {
		Map<Integer, String> colorMap = new HashMap<Integer, String>();
		
		colorMap.put(13, "BROWN");
		colorMap.put(0, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		colorMap.put(3, "GREEN");
		colorMap.put(2, "BLUE");
		
		Port colorPort = LocalEV3.get().getPort("S4");
		EV3ColorSensor cs = new EV3ColorSensor(colorPort);
		SampleProvider colorProvider = cs.getColorIDMode();

		
		UltrasoundPort =  LocalEV3.get().getPort(ULTRASOUND_PORT);
		us = new EV3UltrasonicSensor(UltrasoundPort);
		SampleProvider ultrasonicSampler = us.getDistanceMode();
		
		float[] colorsample = new float[1];
		colorProvider.fetchSample(colorsample, 0);
		System.out.println("Cample " + colorsample[0]);
		
		

		// TODO Auto-generated method stub
	    leftMotor.setSpeed(200);
	    rightMotor.setSpeed(200);
	    
		float desiredDistance = 6.0f;
		
		double oneRotationMovement = Math.PI * wheelD;
		System.out.println("One rotation = " + oneRotationMovement);
		
		double turns =  desiredDistance / oneRotationMovement;
		System.out.println("Need to do " + turns + " rotations");
		
		double degrees = turns * 360;
		int turndegrees = 255;

		float[] distancesample = new float[1];
		ultrasonicSampler.fetchSample(distancesample, 0);
		float dis = distancesample[0];
		System.out.println("DISTANCE: " + dis);		

		if ( dis < .1){
			System.out.println("Rotate left son");
			Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
			Delay.msDelay(2000);
		}
		
		
		
		
		for ( int i = 0; i < 10; i++){
			Movements.MoveForward(leftMotor, rightMotor, (int)degrees);
			Delay.msDelay(2000);
			colorProvider.fetchSample(colorsample, 0);
			//System.out.println("Sample " + colorsample[0]);
			int thissample = (int)colorsample[0];
			if ( colorMap.containsKey(thissample)){
				System.out.format("SAMPLE Value = %s with value %d\n", colorMap.get(thissample), thissample );
			} else {
				System.out.println("Raw VAlue: " +thissample );

			}
			
			distancesample = new float[1];
			ultrasonicSampler.fetchSample(distancesample, 0);
			dis = distancesample[0];
			System.out.println("DISTANCE: " + dis);
			
			if ( dis < .1){
				System.out.println("Rotate left son");
				Movements.RotateLeft(leftMotor, rightMotor, turndegrees);
				Delay.msDelay(2000);
			}
			System.out.println("**************");
			Delay.msDelay(5000);
		}

		cs.close();
		us.close();
		leftMotor.close();
		rightMotor.close();
	}

}

package motor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;

public class NInetyDegreeTurn {

	public static final float track = 5.0f;
	public static final float wheelD = 1.7f;
	public static final float wheelC = (float)Math.PI * wheelD;
    static EV3LargeRegulatedMotor leftMotor;
    static EV3LargeRegulatedMotor rightMotor;
	
	public static int leftSpeed = 400;
	public static int rightSpeed = 400;
	public static int ACCEL = 800;
	
	public static void main(String[] args) {

		System.out.println("Starting up");
		
		leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
		
		

		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.rotateTo(0);
		rightMotor.rotateTo(0);
		
		leftMotor.setSpeed(leftSpeed);
		rightMotor.setSpeed(rightSpeed);
		
		leftMotor.setAcceleration(ACCEL);
		rightMotor.setAcceleration(ACCEL);
		
		int degrees = (int)((( track * Math.PI / 4) / (wheelC )) *360);
		System.out.println("degrees = " + degrees);
		
		for ( int i = 0; i < 4; i++){
			rightMotor.rotate(360*8,true);
			leftMotor.rotate(360*8);
		
			System.out.println("MOved forward waiting");
			Delay.msDelay(3000);
		
			rightMotor.rotate(degrees, true);
			leftMotor.rotate(-degrees);
		
		
			Delay.msDelay(3000);
		}
		
		System.out.println("We done");
		if ( leftMotor != null)
			leftMotor.close();
		
		if ( rightMotor != null)
			rightMotor.close();
		
	}
}

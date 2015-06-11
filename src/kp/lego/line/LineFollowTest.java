package kp.lego.line;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollowTest {

	/**
	 * @param args
	 */
    static EV3LargeRegulatedMotor leftMotor;
    static EV3LargeRegulatedMotor rightMotor;
	public static float whiteval = 92.0f;
	public static float blackval = 7.0f;
	public static float mid = ( whiteval + blackval ) / 2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
		leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		Port port = LocalEV3.get().getPort("S4");

		EV3ColorSensor cs = new EV3ColorSensor(port);
		SampleProvider lightProvider = cs.getRedMode();

		
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.rotateTo(0);
		rightMotor.rotateTo(0);
		
		int ACCEL = 800;
		leftMotor.setAcceleration(ACCEL);
		rightMotor.setAcceleration(ACCEL);
		

		
		float readval = 0;
		for ( int i = 0; i < 1; i++){
			System.out.println("Weeeee");
			float[] sample = new float[1];
			lightProvider.fetchSample(sample, 0);
			float thissample =  sample[0];
			System.out.println("sample : " + thissample);
			Delay.msDelay(500);
			readval = thissample;
			
		}
		float coefficient = 7.0f;

		float baseSpeed = 400;
		float leftSpeed = mid + coefficient * ( readval * 100 - mid);
		float rightSpeed = mid + coefficient * ( mid - (readval * 100));
		
		System.out.println("Left speed = " + leftSpeed + " right speed = " + rightSpeed);
		Delay.msDelay(250);
		leftMotor.setSpeed(leftSpeed);
		rightMotor.setSpeed(rightSpeed);
		
		
		int delta = 35;
		
		System.out.println("MOving");
		leftMotor.forward();
		rightMotor.forward();
		Delay.msDelay(delta);
		
		leftMotor.flt(true);
		rightMotor.flt(true);
		
		for ( int i = 0; i < 200; i++){
			//System.out.println("Weeeee");
			
			float[] sample = new float[1];
			lightProvider.fetchSample(sample, 0);
			float thissample =  sample[0];
			System.out.println("sample : " + thissample);
			leftSpeed = mid + coefficient * ((thissample * 100) - mid);
			rightSpeed = mid + coefficient * (mid - (thissample*100));
			rightMotor.setSpeed(rightSpeed);
			leftMotor.setSpeed(leftSpeed);
			System.out.println("leftSpeed  " + leftSpeed + " right speed = " + rightSpeed);
			
			rightMotor.forward();
			leftMotor.forward();
		
			Delay.msDelay(delta);
			rightMotor.flt(true);
			leftMotor.flt(true);
			//Delay.msDelay(1000);
			//Delay.msDelay(5000);
		}
		
		leftMotor.close();
		rightMotor.close();
	}

}

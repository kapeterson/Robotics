package kp.lego.line;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorTurnTest {

	/**
	 * @param args
	 */
   // static EV3LargeRegulatedMotor leftMotor;
   // static EV3LargeRegulatedMotor rightMotor;
    static EV3LargeRegulatedMotor leftMotor;
    static EV3LargeRegulatedMotor rightMotor;
	public static float whiteval = .92f;
	public static float blackval = .07f;
	public static float mid = ( whiteval + blackval ) / 2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
		rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
		
		int leftSpeed = 400;
		int rightSpeed = 400;
		int ACCEL = 800;
		
		
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.rotateTo(0);
		rightMotor.rotateTo(0);
		
		leftMotor.setSpeed(leftSpeed);
		rightMotor.setSpeed(rightSpeed);
		
		leftMotor.setAcceleration(ACCEL);
		rightMotor.setAcceleration(ACCEL);
		
		leftMotor.forward();
		rightMotor.forward();
		
		
		Delay.msDelay(2000);
		leftMotor.flt(true);
		rightMotor.flt(true);

		
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(rightSpeed);
		rightMotor.forward();
		leftMotor.forward();
		
		
		
		System.out.println("dropping speed of left motor");
		Delay.msDelay(2000);
		
		
		leftMotor.flt(true);
		rightMotor.flt(true);
		rightMotor.setSpeed(300);
		rightMotor.forward();
		leftMotor.forward();
		
		System.out.println("dropping speed of right motor");
		
		
		Delay.msDelay(2000);
		
		leftMotor.flt(true);
		rightMotor.flt(true);
		leftMotor.setSpeed(leftSpeed);
		System.out.println("raising speed of left motor");
		rightMotor.forward();
		leftMotor.forward();
		
		Delay.msDelay(2000);
		leftMotor.flt(true);
		rightMotor.flt(true);
		rightMotor.setSpeed(rightSpeed);
		rightMotor.forward();
		leftMotor.forward();
		
		Delay.msDelay(2000);
		
		leftMotor.stop(true);
		rightMotor.stop();
		
		
		System.out.println("we done");
		
		if ( leftMotor != null)
			leftMotor.close();
		
		if ( rightMotor != null)
			rightMotor.close();
	}

}

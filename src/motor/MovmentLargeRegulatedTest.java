package motor;

import javax.sound.sampled.Port;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MovmentLargeRegulatedTest {

	/**
	 * @param args
	 */
    static EV3LargeRegulatedMotor leftMotor;
    static EV3LargeRegulatedMotor rightMotor;
	
    //static RegulatedMotor rightMotor = Motor.B;
    //static RegulatedMotor leftMotor = Motor.A;
    
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
		
		
		int fwdAngle = 383;
		for ( int i = 0; i < 2; i ++){
			System.out.println("****************\n");
		
			
			rightMotor.rotate(fwdAngle,true);
			leftMotor.rotate(fwdAngle);

			if ( rightMotor.isMoving())
				System.out.println("Right wasn't done yo");
			
			while(rightMotor.isMoving()){
				System.out.println("Waiting");
				Delay.msDelay(250);
			}
			
			System.out.println("finished pt 1");
			Delay.msDelay(5000);
			
			
			//leftMotor.resetTachoCount();
		
			
			
			rightMotor.rotate(fwdAngle, true);
			leftMotor.rotate(fwdAngle);
			
			if ( rightMotor.isMoving())
				System.out.println("Right wasn't done yo");
			
			while(rightMotor.isMoving()){
				System.out.println("Waiting");

				Delay.msDelay(250);
			}
			System.out.println("finished pt 1");

			Delay.msDelay(5000);

		}
		
		leftMotor.close();
		rightMotor.close();
	}

}

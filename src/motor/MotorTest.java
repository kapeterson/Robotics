package motor;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorTest {

	/**
	 * @param args
	 */
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    
	    leftMotor.rotateTo(0);
	    rightMotor.rotateTo(0);
	    leftMotor.setSpeed(200);
	    rightMotor.setSpeed(200);
	    leftMotor.setAcceleration(100);
	    rightMotor.setAcceleration(100);

	    int fwd = 360 * 1;

	    
	    leftMotor.rotateTo(fwd, true);
	    rightMotor.rotateTo(fwd);


	    

	    Delay.msDelay(2000);
	    
	    /*
	    leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    
	    leftMotor.rotateTo(0);
	    rightMotor.rotateTo(0);
	    
	    rightMotor.rotateTo(-fwd,true);
	    leftMotor.rotateTo(-fwd);
	    
	    */
	    System.out.println("LEFT TACHO: " + leftMotor.getTachoCount() + " Right : " + rightMotor.getTachoCount());
	    
	    Delay.msDelay(2000);
	    
	    leftMotor.rotate(fwd, true);
	    rightMotor.rotate(fwd);

	    System.out.println("LEFT TACHO: " + leftMotor.getTachoCount() + " Right : " + rightMotor.getTachoCount());

	    

	    Delay.msDelay(2000);

	}

}

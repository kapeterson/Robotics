package motor;

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
	    leftMotor.setSpeed(300);
	    rightMotor.setSpeed(300);
	    leftMotor.setAcceleration(800);
	    rightMotor.setAcceleration(800);

	    int fwd = 360 * 5;

	    
	    leftMotor.rotateTo(fwd, true);
	    rightMotor.rotateTo(fwd);


	    

	    Delay.msDelay(2000);
	    
	    leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    
	    leftMotor.rotateTo(0);
	    rightMotor.rotateTo(0);
	    
	    rightMotor.rotateTo(-fwd,true);
	    leftMotor.rotateTo(-fwd);
	    
	    
	    Delay.msDelay(2000);

	}

}

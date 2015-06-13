package motor;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class LeftOnly {

	/**
	 * @param args
	 */
    static RegulatedMotor leftMotor = Motor.A;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    leftMotor.resetTachoCount();
	    
	    leftMotor.rotateTo(0);
	    leftMotor.setSpeed(200);
	    leftMotor.setAcceleration(200);

	    int fwd = 360 * 10;

	    System.out.println("Sending it bro");
	    leftMotor.rotateTo(fwd);
	    Delay.msDelay(2000);
	    System.out.println("We done");
	    leftMotor.close();
	    
	    
	}

}

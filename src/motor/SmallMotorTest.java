package motor;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class SmallMotorTest {

	/**
	 * @param args
	 */
    static RegulatedMotor visionMotor = Motor.D;

    public static void resetTacho(){
		visionMotor.resetTachoCount();
	    visionMotor.rotateTo(0);
	   
	    visionMotor.setSpeed(100);
	    visionMotor.setAcceleration(100);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	    visionMotor.setSpeed(100);
	    visionMotor.setAcceleration(300);

	    int fwd = 180;
	    
	    
	    for ( int i = 0; i < 2; i++){
	    	System.out.println("Iteration " + i);
	    	resetTacho();
	    	
	    	System.out.println("doing to 180");
		    visionMotor.rotateTo(fwd);
	    	Delay.msDelay(2000);
	    	System.out.println("going back to 0");
			visionMotor.rotateTo(0);
	    	Delay.msDelay(2000);
	    	System.out.println("Going to -180");
			visionMotor.rotateTo(-180);
			Delay.msDelay(2000);
			System.out.println("going back to 0");
			visionMotor.rotateTo(0);


	    }
	    System.out.println("We done\n");
	}

}

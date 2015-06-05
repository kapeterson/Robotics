package motor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class RotateTest {

	/**
	 * @param args
	 */
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
	static Port port;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    leftMotor.resetTachoCount();
	    rightMotor.resetTachoCount();
	    port =  LocalEV3.get().getPort("S4");
	    
		EV3ColorSensor cs = new EV3ColorSensor(port);
		SampleProvider lightProvider = cs.getColorIDMode();

	    leftMotor.rotateTo(0);
	    rightMotor.rotateTo(0);
	    leftMotor.setSpeed(200);
	    rightMotor.setSpeed(200);
	    leftMotor.setAcceleration(800);
	    rightMotor.setAcceleration(800);

	    int fwd = 360;

	    for ( int i = 0; i < 3; i ++){
	    	leftMotor.rotateTo(fwd, true);
	    	rightMotor.rotateTo(-fwd);
	    	
	    	Delay.msDelay(2000);
	    	leftMotor.rotateTo(0, true);
	    	rightMotor.rotateTo(0);
	    	
	    	Delay.msDelay(2000);
	    	
	    	leftMotor.rotateTo(-fwd, true);
	    	rightMotor.rotateTo(fwd);
	    	
	    	Delay.msDelay(2000);
	    	
	    	leftMotor.rotateTo(0, true);
	    	rightMotor.rotateTo(0);
	    	
	    	Delay.msDelay(2000);

	    }
	    
	    cs.close();
	    leftMotor.close();
	    rightMotor.close();
	    

	    

	    Delay.msDelay(2000);

	}

}

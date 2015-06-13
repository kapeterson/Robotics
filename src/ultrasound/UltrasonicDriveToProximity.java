package ultrasound;

import kp.lego.movement.Movements;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class UltrasonicDriveToProximity {

	/**
	 * @param args
	 */
	public static final String ULTRASOUND_PORT = "S1";
	public static final String TOUCH_PORT = "S2";
	public static EV3UltrasonicSensor us;
	public static EV3TouchSensor ts;
	
	public static Port TouchPort;
	public static Port UltrasoundPort;
	
	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.B;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UltrasoundPort =  LocalEV3.get().getPort(ULTRASOUND_PORT);
		TouchPort  = LocalEV3.get().getPort("S2");
	
		us = new EV3UltrasonicSensor(UltrasoundPort);
		SampleProvider ultrasonicSampler = us.getDistanceMode();
	
		
		ts = new EV3TouchSensor(TouchPort);
		SampleProvider touchSampler = ts.getTouchMode();
		
		System.out.println("Waiting for input.....");
		int quit = 0;
		while ( quit < 1){
			
			float[] touchsample = new float[1];
			touchSampler.fetchSample(touchsample, 0);
			quit = (int)touchsample[0];
			
			float[] distancesample = new float[1];
			ultrasonicSampler.fetchSample(distancesample, 0);
			float dis = distancesample[0];
			System.out.println("DISTANCE: " + dis);
			
			Delay.msDelay(250);
		}
		
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.setSpeed(400);
		rightMotor.setSpeed(400);
		
		//leftMotor.setAcceleration(800);
		//rightMotor.setAcceleration(800);
		
		float measure = 5.0f;
		
		leftMotor.synchronizeWith(new RegulatedMotor[]{rightMotor});
		
		leftMotor.forward();
		rightMotor.forward();
		
		while ( measure > 0.1f){
			
			float[] distancesample = new float[1];
			ultrasonicSampler.fetchSample(distancesample, 0);
			float dis = distancesample[0];
			System.out.println("DISTANCE: " + dis);
			measure = dis;
		}
		
		
		leftMotor.stop(true);
		rightMotor.stop();
		
		
		System.out.println("Turning Left");
		Delay.msDelay(2000);
		
		Movements.RotateLeft(leftMotor, rightMotor, 1);
		Delay.msDelay(2000);

		rightMotor.forward();
		leftMotor.forward();

		Delay.msDelay(4000);
		
		leftMotor.stop(true);
		rightMotor.stop();

		leftMotor.endSynchronization();
		ts.close();
		us.close();
		
		leftMotor.close();
		rightMotor.close();
	}

}

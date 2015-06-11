package kp.lego.line;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class UnregulatedLineFollow {

	/**
	 * @param args
	 */
	static UnregulatedMotor lMotor;
	static UnregulatedMotor rMotor;
	public static float whiteval = 1.0f;
	public static float blackval = .09f;
	public static float mid = ( whiteval + blackval ) / 2;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Port port = LocalEV3.get().getPort("S4");


		
		EV3ColorSensor cs = new EV3ColorSensor(port);
		SampleProvider lightProvider = cs.getRedMode();
		
		
		try {
		lMotor = new UnregulatedMotor(LocalEV3.get().getPort("A"));
		rMotor = new UnregulatedMotor(LocalEV3.get().getPort("B"));

		int basePower = 45;
		int tpower = 90;
		float coefficient = 0.5f;
		
		lMotor.setPower(basePower);
		rMotor.setPower(basePower);
		
		lMotor.forward();
		rMotor.forward();
		System.out.println("We go");
		
		/*
		Delay.msDelay(2000);
		lMotor.setPower((int)(basePower*.75f));
		Delay.msDelay(1000);
		rMotor.setPower((int)(basePower*.75f));
		Delay.msDelay(1000);
		
		
		lMotor.setPower(basePower);
		Delay.msDelay(1000);
		rMotor.setPower(basePower);
		*/
		
		for ( int i = 0; i < 100; i++){
			float[] sample = new float[1];
			lightProvider.fetchSample(sample, 0);
			float thissample =  sample[0];
			int correction = (int)(coefficient*(100* (thissample - mid)));
			if ( correction < 0 ){
				System.out.println("reduce power to go to white");
				lMotor.setPower(basePower - Math.abs(correction));
				rMotor.setPower(basePower);
			} else {
				System.out.println("reduce power to to go black");
				lMotor.setPower(basePower);
				rMotor.setPower(basePower- Math.abs(correction));
			}
			System.out.println("sample : " + thissample + " correction = " +correction +  " mid = " + mid);

			Delay.msDelay(50);
		}
		lMotor.flt();
		rMotor.flt();
		} catch (Exception err){
			lMotor.close();
			rMotor.close();
			cs.close();
		}
		
		lMotor.close();
		rMotor.close();
		cs.close();
	}

}

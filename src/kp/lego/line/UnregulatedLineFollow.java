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
	public static float whiteval = 95.0f;
	public static float blackval = 8.0f;
	public static float mid = (( whiteval - blackval ) / 2 )+ (blackval);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Port port = LocalEV3.get().getPort("S4");


		
		EV3ColorSensor cs = new EV3ColorSensor(port);
		SampleProvider lightProvider = cs.getRedMode();
		
		
		try {
		lMotor = new UnregulatedMotor(LocalEV3.get().getPort("A"));
		rMotor = new UnregulatedMotor(LocalEV3.get().getPort("B"));

		int basePower = 100;
		int tpower = 90;
		float coefficient = .6f;
		
		//lMotor.setPower(basePower);
		//rMotor.setPower(basePower);
		
		//lMotor.forward();
		//rMotor.forward();
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
		
		
		// sensor should sit on left side of line
		int maxp = 0;
		int minp = 1000;
		
		for ( int i = 0; i < 2500; i++){
			float[] sample = new float[1];
			lightProvider.fetchSample(sample, 0);
			float thissample =  sample[0];
			thissample *= 100;
			
			int lp = (int)(coefficient*(whiteval -thissample));
			int rp = (int)(coefficient*(thissample - blackval));

			if ( lp < minp)
				minp = lp;
			if ( rp < minp)
				minp = rp;
			
			if ( lp > maxp)
				maxp = lp;
			if ( rp > maxp)
				maxp = rp;
			lMotor.setPower(lp);
			rMotor.setPower(rp);
			//System.out.println("rp = " + rp + " lp = " + lp);
			int correction = (int)(whiteval - thissample);
			/*
			int correction = (int)(coefficient*(thissample - mid));
			if ( correction < 0 ){
				System.out.println("reduce power to  mother to go to left to white");
				lMotor.setPower(basePower - Math.abs(correction));
				rMotor.setPower(basePower);
			} else {
				System.out.println("reduce power to right motor to go right to black");
				lMotor.setPower(basePower);
				rMotor.setPower(basePower- Math.abs(correction));
			}
			*/
			//System.out.println("sample : " + thissample + " correction = " +correction +  " mid = " + mid);
			//System.out.println("*********");
			Delay.msDelay(20);
		}
		lMotor.flt();
		rMotor.flt();
		System.out.println("maxp = " + maxp);
		System.out.println("minp = " + minp);
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

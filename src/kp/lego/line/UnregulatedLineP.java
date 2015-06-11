package kp.lego.line;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class UnregulatedLineP {

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
		float coefficient = 1.0f;
		

		
		
		// sensor should sit on left side of line
		int maxp = 0;
		int minp = 1000;
		
		for ( int i = 0; i < 6000; i++){
			float[] sample = new float[1];
			lightProvider.fetchSample(sample, 0);
			float thissample =  sample[0];
			thissample *= 100;

			
			int correction = (int)(coefficient*(thissample - mid));
			if ( correction < 0 ){
				//System.out.println("reduce power to  left mother to go to left to white");
				//lMotor.setPower((int)mid - Math.abs(correction));
				//rMotor.setPower((int)mid+Math.abs(correction));
				
				lMotor.setPower(basePower-Math.abs(correction));
				rMotor.setPower(basePower);
				

			} else {
				//System.out.println("reduce power to right motor to go right to black");
				//lMotor.setPower((int)mid+Math.abs(correction));
				//rMotor.setPower((int)mid- Math.abs(correction));
				lMotor.setPower(basePower);
				rMotor.setPower(basePower-Math.abs(correction));
				
				if ( mid + Math.abs(correction) > maxp)
					maxp = (int)mid + Math.abs(correction);
			}
			
			//System.out.println("sample : " + thissample + " correction = " +correction +  " mid = " + mid);
			//System.out.println("*********");
			//Delay.msDelay(20);
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

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
	public static float whiteval = 90.0f;
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

			int basePower = 60;
			float coefficient = 1.0f;
		

		
		
			// sensor should sit on left side of line
			int maxp = 0;
			int	 minp = 1000;
			float maxsample = 0.0f;
			float minsample = 1.0f;
		
			for ( int i = 0; i < 10000; i++){
				float[] sample = new float[1];
				lightProvider.fetchSample(sample, 0);
				float thissample =  sample[0];
				if (thissample < minsample)
					minsample= thissample;
			
				if ( thissample > maxsample)
					maxsample = thissample;
			
				thissample *= 100;

				// PLace on left side
			
				int correction = (int)(coefficient*(thissample - mid));
				if ( correction < 0 ){

				
				
					if ( basePower - Math.abs(correction) < minp)
						minp = basePower - Math.abs(correction);
				
					lMotor.setPower(basePower-Math.abs(correction));
					rMotor.setPower(basePower);
				

				} else {

					lMotor.setPower(basePower);
					rMotor.setPower(basePower-Math.abs(correction));
				
					if ( basePower - Math.abs(correction) < minp)
						minp = basePower - Math.abs(correction);
								
					if ( mid + Math.abs(correction) > maxp)
						maxp = (int)mid + Math.abs(correction);
				}	
			

				Delay.msDelay(20);
			}
			lMotor.flt();
			rMotor.flt();
			System.out.println("maxp = " + maxp);
			System.out.println("minp = " + minp);
			System.out.println("max sample = " + maxsample);
			System.out.println("min sample = " + minsample);
		
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

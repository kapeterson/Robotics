package motor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.utility.Delay;

public class UnregulatedTest {

	/**
	 * @param args
	 */
	static UnregulatedMotor lMotor;
	static UnregulatedMotor rMotor;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		lMotor = new UnregulatedMotor(LocalEV3.get().getPort("A"));
		rMotor = new UnregulatedMotor(LocalEV3.get().getPort("B"));

		lMotor.setPower(60);
		rMotor.setPower(60);
		
		lMotor.forward();
		rMotor.forward();
		System.out.println("We go");
		
		Delay.msDelay(3000);
		lMotor.flt();
		rMotor.flt();
		} catch (Exception err){
			lMotor.close();
			rMotor.close();
		}
	}

}

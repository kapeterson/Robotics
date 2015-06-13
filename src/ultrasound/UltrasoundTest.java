package ultrasound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class UltrasoundTest {

	/**
	 * @param args
	 */
	
	public static final String ULTRASOUND_PORT = "S1";
	public static final String TOUCH_PORT = "S2";
	public static EV3UltrasonicSensor us;
	public static EV3TouchSensor ts;
	
	public static Port TouchPort;

	public static Port UltrasoundPort;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Port port = LocalEV3.get().getPort("S4");
		
		try {
			UltrasoundPort =  LocalEV3.get().getPort(ULTRASOUND_PORT);
			TouchPort  = LocalEV3.get().getPort("S2");
		
			us = new EV3UltrasonicSensor(UltrasoundPort);
			SampleProvider ultrasonicSampler = us.getDistanceMode();
		
			
			ts = new EV3TouchSensor(TouchPort);
			SampleProvider touchSampler = ts.getTouchMode();
		
			int quit = 0;

			while ( quit < 1){

				float[] distancesample = new float[1];
				ultrasonicSampler.fetchSample(distancesample, 0);
				float dis = distancesample[0];
				System.out.println("DISTANCE: " + dis);
			
				float[] touchsample = new float[1];
				touchSampler.fetchSample(touchsample, 0);
				quit = (int)touchsample[0];
				
				Delay.msDelay(1000);
			
			}
			
			us.close();
			ts.close();
			
		} catch ( Exception err){
			us.close();
			ts.close();
		}
		

	}

}

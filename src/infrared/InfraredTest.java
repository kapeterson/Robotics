package infrared;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class InfraredTest {

	/**
	 * @param args
	 */
	static EV3IRSensor sensor;
	static Port port;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		Port port = LocalEV3.get().getPort("S3");
		sensor = new EV3IRSensor(port);
		
		SampleProvider IRSensorDist = sensor.getSeekMode();                  // distance
		
		
		float[] IRSensorDistSamples = new float[IRSensorDist.sampleSize()];
		
		for ( int i = 0; i < 40; i++){
			IRSensorDist.fetchSample(IRSensorDistSamples, 0);
		
				for ( float smp :IRSensorDistSamples){
					System.out.println("Sample " + smp);
				}
				
				System.out.println("*************");
				Delay.msDelay(2000);
		}
		
		} catch ( Exception e){
			
			if ( sensor != null)
				sensor.close();
			
				
		}
		
		if ( sensor != null)
			sensor.close();
	}

}

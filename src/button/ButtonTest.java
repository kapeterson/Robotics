package button;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ButtonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Port port = LocalEV3.get().getPort("S2");
		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		lcd.clear();
		EV3TouchSensor ts = new EV3TouchSensor(port);
		SampleProvider gProvider = ts.getTouchMode();
		
		int n = 0;
		int quit = 0;
		
		while (quit < 1){
			lcd.clear();
			float[] sample = new float[1];
			
			gProvider.fetchSample(sample, 0);
			quit = (int)sample[0];

			lcd.drawString("Loop " + n + "Sample = " + quit, 0, 20, GraphicsLCD.BASELINE);
			Delay.msDelay(1000);
			n++;
			lcd.clear();
		}
		
		ts.close();
	}

}

package color;

import java.util.HashMap;
import java.util.Map;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorTestRedMode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, String> colorMap = new HashMap<Integer, String>();
		colorMap.put(13, "BROWN");
		colorMap.put(0, "RED");
		colorMap.put(7, "BLACK");
		colorMap.put(6, "WHITE");
		colorMap.put(3, "GREEN");
		colorMap.put(2, "BLUE");

		Port port = LocalEV3.get().getPort("S4");
		Port touchport = LocalEV3.get().getPort("S2");

		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		lcd.setFont(Font.getSmallFont());

		lcd.clear();
		EV3ColorSensor cs = new EV3ColorSensor(port);
		SampleProvider lightProvider = cs.getRedMode();

		EV3TouchSensor ts = new EV3TouchSensor(touchport);
		SampleProvider gProvider = ts.getTouchMode();
		
		
		int quit = 0;
		
		while ( quit < 1){
			LCD.clear();
			float[] sample = new float[1];
			
			float[] touchsample = new float[1];
			gProvider.fetchSample(touchsample, 0);

			quit = (int)touchsample[0];
			
			lightProvider.fetchSample(sample, 0);
			float thissample = sample[0];
		
			//lcd.drawString("Sample = " + sample[0], 0, 20, GraphicsLCD.BASELINE);
			System.out.println("Sample " + thissample);
			
			Delay.msDelay(1000);
			lcd.clear();
		}
		cs.close();
		ts.close();
	}

}

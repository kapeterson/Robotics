package color;

import java.util.HashMap;
import java.util.Map;

import lejos.robotics.SampleProvider;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class ColorTest {

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
		
		Port port = LocalEV3.get().getPort("S4");
		Port touchport = LocalEV3.get().getPort("S2");

		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		lcd.setFont(Font.getSmallFont());

		lcd.clear();
		EV3ColorSensor cs = new EV3ColorSensor(port);
		//SampleProvider lightProvider = cs.getRedMode();
		SampleProvider lightProvider = cs.getColorIDMode();

		
		// Color ID:
		// 0 - Red ( works ... Orange / Red /
		// 1 - Green ( works sometimes with light green)
		// 2 - Blue ( Works )
		// 3
		// 4
		// 5
		// 6 - White ( Works )
		// 7 - Black ( Works )
		// 13 - Brown
		EV3TouchSensor ts = new EV3TouchSensor(touchport);
		SampleProvider gProvider = ts.getTouchMode();
		
		//lcd.drawString("Hey dude we good", 0, 20, GraphicsLCD.BASELINE);
		
		int quit = 0;
		
		while ( quit < 1){
			LCD.clear();
			float[] sample = new float[1];
			
			float[] touchsample = new float[1];
			gProvider.fetchSample(touchsample, 0);

			quit = (int)touchsample[0];
			
			lightProvider.fetchSample(sample, 0);
			int thissample = (int)sample[0];
			if ( colorMap.containsKey(thissample)){
				System.out.format("SAMPLE Value = %s with value %d\n", colorMap.get(thissample), thissample );
			} else {
				System.out.println("Raw VAlue: " +thissample );
			}
			//lcd.drawString("Sample = " + sample[0], 0, 20, GraphicsLCD.BASELINE);
			//System.out.println("Sample " + sample[0]);
			
			Delay.msDelay(1000);
			lcd.clear();
		}
		cs.close();
		ts.close();
		
	}

}

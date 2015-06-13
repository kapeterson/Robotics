import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphicsLCD lcd = LocalEV3.get().getGraphicsLCD();
		lcd.setFont(Font.getSmallFont());
		
		//final int sw = lcd.getWidth();
		final int sh = lcd.getHeight();
		lcd.drawString("[ .99 .88 .75 .25 .33 .22 .11 ]", 0, sh/5, GraphicsLCD.BASELINE);
		Delay.msDelay(5000);
		LCD.clear();
		lcd.setFont(Font.getDefaultFont());

		lcd.drawString("[ 0 1 1 2 3 3 2 ]", 0, sh/5, GraphicsLCD.BASELINE);
		lcd.drawString("[ .99 .88 .75 .25 .33 .22 .11 ]", 0, 2*(sh/5), GraphicsLCD.BASELINE);

		Delay.msDelay(10000);
	}

}

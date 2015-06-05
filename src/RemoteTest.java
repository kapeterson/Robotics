
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class RemoteTest {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	static RemoteEV3 ev3;
	
	static RMIRegulatedMotor rightMotor;
	static RMIRegulatedMotor leftMotor; 
	static EV3ColorSensor cs;
	static final int N = 5;
	
	public static void moveForward(int degrees) throws RemoteException{
		  leftMotor.rotate(degrees, true);
		  rightMotor.rotate(degrees);
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		// TODO Auto-generated method stub
		
		try {
			
			System.exit(1);
			
			ev3 = new RemoteEV3("172.16.0.7");

			System.out.println("Connected to robot");
		
			rightMotor = ev3.createRegulatedMotor("A", 'L');
			System.out.println("Opened port A");
			leftMotor = ev3.createRegulatedMotor("B", 'L');
			System.out.println("Opened port B");

			Port port = ev3.getPort("S4");
			cs = new EV3ColorSensor(port);
		
			System.out.println("Using color mode " + cs.getColorIDMode().toString());
		
		
			//RMISampleProvider color_sensor = ev3.createSampleProvider("S4",
	    	//           "lejos.hardware.sensor.EV3ColorSensor", );
		
			leftMotor.resetTachoCount();
			rightMotor.resetTachoCount();
		
			leftMotor.setSpeed(300);
			rightMotor.setSpeed(300);
		  
			leftMotor.setAcceleration(800);
			rightMotor.setAcceleration(800);
		
		

			moveForward(540);
			SampleProvider colorProvider = cs.getColorIDMode();

			for ( int i = 0; i < N; i ++){
				
				float[] sample = new float[1];
				colorProvider.fetchSample(sample, 0);
			
				System.out.println("Sample = " + sample[0]);
				System.out.println();
				Delay.msDelay(1000);
			
			}
		
		} catch ( Exception e){
			
			System.out.println(e);
			
			if ( rightMotor != null)
				rightMotor.close();
			
			if ( leftMotor != null)
				leftMotor.close();

			
			if ( cs != null)
				cs.close();
		}
		
		if ( leftMotor != null )
			leftMotor.close();
		
		if ( rightMotor != null)
			rightMotor.close();
		
		if ( cs != null)
			cs.close();
	}

}

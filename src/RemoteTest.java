
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class RemoteTest {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		// TODO Auto-generated method stub
		RemoteEV3 ev3 = new RemoteEV3("172.16.0.7");
		
		RMIRegulatedMotor rightMotor = ev3.createRegulatedMotor("A", 'L');
		RMIRegulatedMotor leftMotor = ev3.createRegulatedMotor("A", 'L');

	}

}

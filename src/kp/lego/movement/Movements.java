package kp.lego.movement;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Movements {

	/**
	 * @param args
	 */
	
	public static void MoveForward(){
		
	}
	
	public static void RotateLeft(RegulatedMotor leftMotor, RegulatedMotor rightMotor, int degrees ){
    	leftMotor.rotate(280, true);
    	rightMotor.rotate(-280);
    	

    	System.out.println("LEFT: " + leftMotor.getTachoCount() + "  RIGHT: " + rightMotor.getTachoCount() );
    	Delay.msDelay(2000);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

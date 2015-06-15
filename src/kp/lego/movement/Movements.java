package kp.lego.movement;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Movements {

	/**
	 * @param args
	 */
	
	public static void MoveForward(RegulatedMotor leftMotor, RegulatedMotor rightMotor, int degrees ){
		leftMotor.rotate(degrees,true);
		rightMotor.rotate(degrees);
	}
	
	public static void RotateLeft(RegulatedMotor leftMotor, RegulatedMotor rightMotor, int degrees ){
		// 280 is the right value for the two wheel education bot
    	leftMotor.rotate(-degrees, true);
    	rightMotor.rotate(degrees);
    	

    	//System.out.println("LEFT: " + leftMotor.getTachoCount() + "  RIGHT: " + rightMotor.getTachoCount() );
    	Delay.msDelay(2000);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

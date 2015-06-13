package kp.lego.movement;

import java.util.ArrayList;
import java.util.List;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MoveSquare {

	/**
	 * @param args
	 */
	static RegulatedMotor leftMotor = Motor.A;
	static RegulatedMotor rightMotor = Motor.B;
	
	public static void MoveLeft(int degrees){
    	leftMotor.rotate(255, true);
    	rightMotor.rotate(-255);
    	
    	Delay.msDelay(2000);
    	leftMotor.rotate(degrees, true);
    	rightMotor.rotate(degrees);
    	
    	Delay.msDelay(1000);
    	System.out.println("LEFT: " + leftMotor.getTachoCount() + "  RIGHT: " + rightMotor.getTachoCount() );
    	Delay.msDelay(2000);
	}
	
	public static void MoveForward(int degrees){
    	leftMotor.rotate(degrees, true);
    	rightMotor.rotate(degrees);
    	
    	Delay.msDelay(1000);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  int fwd =(int)(360*4);

		  leftMotor.resetTachoCount();
		  rightMotor.resetTachoCount();
		    
		  leftMotor.rotateTo(0);
		  rightMotor.rotateTo(0);
		  
		  leftMotor.setSpeed(300);
		  rightMotor.setSpeed(300);
		  
		  leftMotor.setAcceleration(100);
		  rightMotor.setAcceleration(100);
		  
		  
			List<Movement> movements = new ArrayList<Movement>();
			
			
			movements.add(Movement.FORWARD);
			
			for ( int i = 0; i < 5; i ++)
				movements.add(Movement.LEFT);


		for ( Movement m : movements){
			System.out.println("Moving");
			
			switch (m){
			case FORWARD:
				MoveForward(fwd);
				break;
			case LEFT:
				MoveLeft(fwd);
				break;
			}
	  		Delay.msDelay(2000);

		}
		
		leftMotor.close();
		rightMotor.close();
	}

}

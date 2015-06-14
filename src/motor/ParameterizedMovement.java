package motor;

import java.util.ArrayList;
import java.util.List;

import kp.lego.movement.Movement;
import kp.lego.movement.Movements;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class ParameterizedMovement {

	/**
	 * @param args
	 */
	public static final float track = 5.0f;		// 5 inch between wheel centers
	public static final float wheelD = 1.7f; 		// 1.7 Inch Wheel Diameter = 4.32 cm
	public static final float wheelWidth = .86f; 	// .86 Inch / 2.2 cm
	
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<Movement> movements = new ArrayList<Movement>();

		movements.add(Movement.FORWARD);
		movements.add(Movement.FORWARD);
		movements.add(Movement.LEFT);
		movements.add(Movement.FORWARD);
		movements.add(Movement.FORWARD);

		int turnangle = 255;
		
	    leftMotor.setSpeed(200);
	    rightMotor.setSpeed(200);
	    
		float desiredDistance = 6.0f;
		
		double oneRotationMovement = Math.PI * wheelD;
		System.out.println("One rotation = " + oneRotationMovement);
		
		double turns =  desiredDistance / oneRotationMovement;
		System.out.println("Need to do " + turns + " rotations");
		
		double degrees = turns * 360;
		
		RegulatedMotor[] motors = new RegulatedMotor[1];
		motors[0] = rightMotor;
		
		//leftMotor.synchronizeWith(m);
		for ( Movement m : movements){
			System.out.println("Moving");
			
			switch (m){
				case FORWARD:
					Movements.MoveForward(leftMotor, rightMotor, (int)degrees);
					break;
				case LEFT:
					Movements.RotateLeft(leftMotor, rightMotor, (int)turnangle);
					break;
			}
	  		Delay.msDelay(2000);

		}


		rightMotor.close();
		leftMotor.close();
		
	}

}

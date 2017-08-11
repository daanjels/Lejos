package sensors;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

/**
 * Adapted from NXT program. Need to check this out on EV3
 * 
 * @author Arqetype
 *
 */

public class ObstacleUltra {
	static EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.C);
	static EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(MotorPort.B);

	public static void main(String[] args) {
		int distance;
		EV3 wallE = (EV3) BrickFinder.getLocal();	// get EV3 brick

		Wheel wheel1 = WheeledChassis.modelWheel(LEFT_MOTOR, 3.22).offset(-9.2);
		Wheel wheel2 = WheeledChassis.modelWheel(RIGHT_MOTOR, 3.22).offset(9.2);
		// set up the chassis type, i.e. Differential pilot
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2}, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(20);
		pilot.setAngularSpeed(45);
		
		Port portS2 = wallE.getPort("S2");
		EV3UltrasonicSensor ultraSonic = new EV3UltrasonicSensor(portS2);
		SampleProvider ultra = ultraSonic.getDistanceMode();
		float[] sample = new float[ultra.sampleSize()];
		Button.waitForAnyPress();
		
		while (!Button.ESCAPE.isDown()) {
			ultra.fetchSample(sample, 0);
			distance = (int) sample[0];
			while (distance > 25) {
				pilot.forward();
				ultra.fetchSample(sample, 0);
				distance = (int) sample[0];
				if (distance <= 25) {
					while (distance <= 30) {
						pilot.backward();
						ultra.fetchSample(sample, 0);
						distance = (int) sample[0];
					}
					pilot.rotate(90);
				}
			}
		}
		ultraSonic.close();
	}
}

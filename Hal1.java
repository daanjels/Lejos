package be.wimdaniels;

import lejos.nxt.Button;
import lejos.nxt.Motor;

public class Hal1 {

	public static void main(String[] args) {
		System.out.println("NXT wakes up!");
		Button.waitForAnyPress();
		Motor.A.forward();
		Button.waitForAnyPress();
	}
}

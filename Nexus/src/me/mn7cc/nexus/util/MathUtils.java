package me.mn7cc.nexus.util;

public class MathUtils {

	public static double random(double min, double max) {
		return (Math.random() * (max - min + 1)) + min;
	}
	
	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}
	
	public static double shorten(double number) {
		number *= 100;
		number = Math.round(number);
		number /= 100;
		return number;
	}
	
	public static double difference(double number1, double number2) {
		return number1 > number2 ? number1 - number2 : number2 - number1;
	}
	
}

package com.elven.danmaku.core.util;

public final class MathUtils {

	public static int min(int number, int... otherNumbers) {
		if(otherNumbers != null) {
			for(int otherNumber : otherNumbers) {
				number = Math.min(number, otherNumber);
			}
		}
		
		return number;
	}
	
	public static double min(double number, double... otherNumbers) {
		if(otherNumbers != null) {
			for(double otherNumber : otherNumbers) {
				number = Math.min(number, otherNumber);
			}
		}
		
		return number;
	}
	
	public static double max(double number, double... otherNumbers) {
		if(otherNumbers != null) {
			for(double otherNumber : otherNumbers) {
				number = Math.max(number, otherNumber);
			}
		}
		
		return number;
	}
}

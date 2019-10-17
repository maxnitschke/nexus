package me.mn7cc.nexus.util;

import java.util.concurrent.TimeUnit;

import me.mn7cc.nexus.exception.InvalidTimeFormatException;

public class TimeUtils {

	public static String toString(double milliseconds) {
		
		milliseconds /= 1000; milliseconds = Math.round(milliseconds); milliseconds *= 1000;
		
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
		while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
		while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }
		while(milliseconds >= 1000) { seconds += 1; milliseconds -= 1000; }
		
		StringBuilder time = new StringBuilder("");
		
		if(days > 0) time.append(days + " Days ");
		if(hours > 0) time.append(hours + " Hours ");
		if(minutes > 0) time.append(minutes + " Minutes ");
		if(seconds > 0) time.append(seconds + " Seconds ");
		
		if(time.length() > 0) time.delete(time.length() - 1, time.length());
		
		return time.toString();
		
	}
	
	public static String toShortString(double milliseconds) {
		
		milliseconds /= 1000; milliseconds = Math.round(milliseconds); milliseconds *= 1000;
		
		int days = 0;
		int hours = 0;
		int minutes = 0;
		
		while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
		while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
		while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }
		
		String time = "";
		if(days > 0) time = days + " Days ";
		if(hours > 0) time = time + hours + " Hours ";
		if(minutes > 0) time = time + minutes + " Minutes ";
		
		if(time.equals("")) time = "Now";
		
		return time;
		
	}
	
	public static String toShortestString(double milliseconds) {
		
		milliseconds /= 1000; milliseconds = Math.round(milliseconds); milliseconds *= 1000;
		
		int days = 0;
		int hours = 0;
		int minutes = 0;
		
		while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
		while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
		while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }
		
		String time = "";
		if(days > 0) time = days + "d ";
		if(hours > 0) time = time + hours + "h ";
		if(minutes > 0) time = time + minutes + "m ";
		
		return time;
		
	}
	
	public static double toMilliseconds(double time, String unit) {
		
		if(unit.equalsIgnoreCase("days") || unit.equalsIgnoreCase("day") || unit.equalsIgnoreCase("d")) { return time * 1000 * 60 * 60 * 24; }
		else if(unit.equalsIgnoreCase("hours") || unit.equalsIgnoreCase("hour") || unit.equalsIgnoreCase("h")) { return time * 1000 * 60 * 60; }
		else if(unit.equalsIgnoreCase("minutes") || unit.equalsIgnoreCase("minute") || unit.equalsIgnoreCase("m")) { return time * 1000 * 60; }
		else if(unit.equalsIgnoreCase("seconds") || unit.equalsIgnoreCase("second") || unit.equalsIgnoreCase("s")) { return time * 1000; }
		
		return 0;
		
	}
	
	public static double toMilliseconds(double time, TimeUnit unit) {
		
		if(unit.equals(TimeUnit.DAYS)) { return time * 1000 * 60 * 60 * 24; }
		else if(unit.equals(TimeUnit.HOURS)) { return time * 1000 * 60 * 60; }
		else if(unit.equals(TimeUnit.MINUTES)) { return time * 1000 * 60; }
		else if(unit.equals(TimeUnit.SECONDS)) { return time * 1000; }
		
		return 0;
		
	}
	
	public static double parseTime(String time) throws InvalidTimeFormatException {
		
		time = time.toLowerCase();
		
		double milliseconds = 0;
		
		StringBuilder currentTime = new StringBuilder();
		
		for(int i = 0; i < time.length(); i++) {
			String c = Character.toString(time.charAt(i));
			if(c.matches("[0-9]")) currentTime.append(c);
			else {
				
				if(!StringUtils.isInteger(currentTime.toString())) throw new InvalidTimeFormatException();
				int duration = StringUtils.parseInteger(currentTime.toString());
				if(duration <= 0) throw new InvalidTimeFormatException();
				
				if(c.equals("s")) milliseconds += duration * 1000;
				else if(c.equals("m")) milliseconds += duration * 1000 * 60;
				else if(c.equals("h")) milliseconds += duration * 1000 * 60 * 60;
				else if(c.equals("d")) milliseconds += duration * 1000 * 60 * 60 * 24;
				else throw new InvalidTimeFormatException();
				
				currentTime.setLength(0);
				
			}
		}
		
		return milliseconds;
		
	}
	
}
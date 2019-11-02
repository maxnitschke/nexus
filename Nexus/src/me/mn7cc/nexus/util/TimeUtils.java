package me.mn7cc.nexus.util;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static double parseTime(String time) {
		
		Pattern pattern = Pattern.compile("([0-9]+y)|([0-9]+mo)|([0-9]+w)|([0-9]+d)|([0-9]+h)|([0-9]+m)|([0-9]+s)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(time);

		int years = 0;
		int months = 0;
		int weeks = 0;
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		while(matcher.find()) {
			
			if(matcher.group(1) != null && years == 0) years = StringUtils.parseInteger(matcher.group(1).replaceAll("\\D+", ""));
			if(matcher.group(2) != null && months == 0) months = StringUtils.parseInteger(matcher.group(2).replaceAll("\\D+", ""));
			if(matcher.group(3) != null && weeks == 0) weeks = StringUtils.parseInteger(matcher.group(3).replaceAll("\\D+", ""));
			if(matcher.group(4) != null && days == 0) days = StringUtils.parseInteger(matcher.group(4).replaceAll("\\D+", ""));
			if(matcher.group(5) != null && hours == 0) hours = StringUtils.parseInteger(matcher.group(5).replaceAll("\\D+", ""));
			if(matcher.group(6) != null && minutes == 0) minutes = StringUtils.parseInteger(matcher.group(6).replaceAll("\\D+", ""));
			if(matcher.group(7) != null && seconds == 0) seconds = StringUtils.parseInteger(matcher.group(7).replaceAll("\\D+", ""));
			
		}
		
		double milliseconds = 0;
		
		milliseconds += years * 1000 * 60 * 60 * 24 * 365;
		milliseconds += months * 1000 * 60 * 60 * 24 * 30;
		milliseconds += weeks * 1000 * 60 * 60 * 24 * 7;
		milliseconds += days * 1000 * 60 * 60 * 24;
		milliseconds += hours * 1000 * 60 * 60;
		milliseconds += minutes * 1000 * 60;
		milliseconds += seconds * 1000;
		
		return milliseconds;
		
	}
	
}
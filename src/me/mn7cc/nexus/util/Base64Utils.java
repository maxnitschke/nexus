package me.mn7cc.nexus.util;

import java.util.Base64;

public class Base64Utils {

	public static String encodeString(String s) {
		
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encoded = encoder.encode(s.getBytes());
		return new String(encoded);
		
	}
	
	public static String decodeString(String s) {
		
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decoded = decoder.decode(s.getBytes());
		return new String(decoded);
		
	}
	
	public static String encodeInteger(int i) {
		
		return encodeString(String.valueOf(i));
		
	}
	
	public static int decodeInteger(String i) {
		
		return StringUtils.parseInteger(decodeString(i));
		
	}
	
}

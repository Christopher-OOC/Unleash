package com.example.utilities;

import java.security.SecureRandom;
import java.util.Random;

public class PublicIdGeneratorUtils {

	private static Random random = new SecureRandom();

	private static String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";

	public static String generateId(int length) {
		StringBuilder builder = new StringBuilder(length);
		
		for (int i = 0; i < length; i++) {
			builder.append(characters.charAt(random.nextInt(characters.length())));
		}
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(generateId(30));
	}
	
}

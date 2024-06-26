package com.example.utilities;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

public class PinDecoderUtils {
	
	public static String decodePin(String encoderPin) {
		String salt = KeyGenerators.string().generateKey();
		String secret = "christopher";
		TextEncryptor encryptor = Encryptors.text(secret, salt);
		
		return encryptor.decrypt(encoderPin);
	}
}

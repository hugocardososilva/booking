package com.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
public class CriptografarSenha {
	 private static final Charset UTF_8 = StandardCharsets.UTF_8;
	
	public static void main(String[] argv) {

		 String text = "123456";

	        byte[] encodedBytes = Base64.encodeBase64(text.getBytes(UTF_8));
	        System.out.println("encodedBytes: " + new String(encodedBytes, UTF_8));
	        
	        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
	        System.out.println("decodedBytes: " + new String(decodedBytes, UTF_8));	        
	}
}

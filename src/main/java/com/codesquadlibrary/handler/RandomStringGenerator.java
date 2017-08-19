package com.codesquadlibrary.handler;

import java.util.Random;

public class RandomStringGenerator {

	public static String randomStringFactory(int length) {
		Random rnd = new Random();
		StringBuffer sbf = new StringBuffer();

		for (int i = 0; i < length; i++) {
			if (rnd.nextBoolean()) {
				sbf.append((char) ((int) (rnd.nextInt(26)) + 97));
			} else {
				sbf.append((rnd.nextInt(10)));
			}
		}
		return sbf.toString();
	}

}

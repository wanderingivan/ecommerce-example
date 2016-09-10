package com.ecommerce.util;

public class LuhnTestUtil {

	/**
	 * Validate a number string using the Luhn algorithm
	 * @param numberString the number to validate in string form
	 * @return does the string pass the Luhn Test
	 */
	public static boolean validate(String numberString) {
		int checkString = checkSum(numberString);
		return checkString == 0;
	}
	
	private static int checkSum(String numberString) {
		int sum = 0, checkDigit = 0;
		boolean isDouble = false;
	    numberString = numberString.substring(0, numberString.length());
			
		for (int i = numberString.length() - 1; i >= 0; i--) {
			int k = Integer.parseInt(String.valueOf(numberString.charAt(i)));
			int processed = sumToSingleDigit((k * (isDouble ? 2 : 1)));
			sum +=processed;
			isDouble = !isDouble;
		}

		if ((sum % 10) > 0)
			checkDigit = (10 - (sum % 10));

		return checkDigit;
	}

	private static int sumToSingleDigit(int k) {
		if (k < 10) { return k; }
		
		return sumToSingleDigit(k / 10) + (k % 10);
	}

}
package com.vtg.auto.utilities;

import org.testng.Assert;



public class TestVerification {
	private StringBuffer verificationErrors;

	public TestVerification() {
		verificationErrors = new StringBuffer();
	}

	public void verifyEquals(String expected, String actual) {
		Assert.assertEquals(expected, actual);
	}

	public void verifyTrue(String message, boolean result) {
		Assert.assertTrue(result, message);
	}

	public void verifyFalse(String message, boolean result) {
		Assert.assertFalse(result, message);
	}

	public void verifyTrue(boolean result) {
		Assert.assertTrue(result);
	}

	public void verifyFalse(boolean result) {
		Assert.assertFalse(result);
	}

	public void clearVerificationErrors() {
		verificationErrors = new StringBuffer();
	}

	public void checkVerificationError() {
		String verificationError = verificationErrors.toString();
		clearVerificationErrors();
		if (!"".equals(verificationError)) {
			Assert.fail(verificationError);
		}

	}
}

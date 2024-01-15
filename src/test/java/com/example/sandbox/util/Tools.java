package com.example.sandbox.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.StringSubstitutor;

public class Tools {
	
	private static String variableName;
	private static String variableValue;
	
	public static int generateRandomNumber() {
		Random random = new Random();
		return random.nextInt(100);
	}
    
	public static String generateRandomString() {
		int length = 10;
	    boolean useLetters = true;
	    boolean useNumbers = false;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
	    return generatedString;
	}
	
    public static Tools replaceVariable(String variableName) {
		Tools.variableName= variableName;
		return new Tools();
    }
    
    public Tools withValue(String value) {
    	Tools.variableValue= value;
    	return new Tools();
    }
    
    public String inText(String text) {
    	Map<String,String> textParameters = new TreeMap<String, String>();
    	textParameters.put(variableName, variableValue);
    	StringSubstitutor substitutor = new StringSubstitutor(textParameters);
    	String replacedText = substitutor.replace(text);
    	return replacedText;
    }
    
    public static String actualTimeToString() {
    	LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    	return String.format("%s+0000", now);
    }
}

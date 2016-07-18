package com.richard.utils;

import org.junit.Assert;
import org.junit.Test;

import com.richard.exception.LanguageDetectorException;

public class ConfigFileReaderTest {

	ConfigFileReader reader;
	
	@Test
	public void testConfigFilePath(){
		boolean exceptionOccured = false;
		
		try {
			reader = new ConfigFileReader();
		} catch (LanguageDetectorException e) {
			// TODO: handle exception
			exceptionOccured = true;
		}
		Assert.assertFalse(exceptionOccured);
		
		String value = null;
		value = reader.getProperty("ngram.limit");
		
		Assert.assertNotNull(value);
		
	}
	
	@Test
	public void testGetNgramLimit(){
		boolean exceptionOccured = false;
		int value = 0;
		try {
			reader = new ConfigFileReader();
			value = reader.getNGramLimit();
		} catch (Exception e) {
			// TODO: handle exception
			exceptionOccured = true;
		}
		Assert.assertFalse(exceptionOccured);
		
		Assert.assertNotSame(0, value);;
	}
	
	@Test
	public void testGetMinNgram(){
		boolean exceptionOccured = false;
		int value = 0;
		try {
			reader = new ConfigFileReader();
			value = reader.getNGramMin();
		} catch (Exception e) {
			// TODO: handle exception
			exceptionOccured = true;
		}
		Assert.assertFalse(exceptionOccured);
		
		Assert.assertNotSame(0, value);;
	}
	
	@Test
	public void testGetMaxNgram(){
		boolean exceptionOccured = false;
		int value = 0;
		try {
			reader = new ConfigFileReader();
			value = reader.getNGramMax();
		} catch (Exception e) {
			// TODO: handle exception
			exceptionOccured = true;
		}
		Assert.assertFalse(exceptionOccured);
		
		Assert.assertNotSame(0, value);
		
	}
}

package com.richard.utils;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.richard.utils.ConfigFileReader;
import com.richard.utils.NGramExtractor;

@RunWith(MockitoJUnitRunner.class)
public class NGramExtractorTest {

	@Autowired
	private ConfigFileReader configReader;

	@InjectMocks
	private NGramExtractor extractor;

	@Test
	public void createNGramsTest(){
	
		List<String> nGramList = extractor.createNGrams("Crux", 2, 3);

		Assert.assertTrue(nGramList.contains("Cr"));
		Assert.assertTrue(nGramList.contains("ux"));
		Assert.assertTrue(nGramList.contains("x_"));
		Assert.assertTrue(nGramList.contains("_Cr"));
	}
	
	@Test
	public void calculateNGramInstanceTest(){
		
		boolean exceptionOccured = false;
		try {
			extractor.calculateNGramInstance("test");
		} catch (Exception e) {
			exceptionOccured = true;

		}
		Assert.assertTrue(exceptionOccured);
	}
	
	
	
	
	
	
	
}

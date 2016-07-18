package com.richard.language;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.richard.vo.LanguageData;

public class LanguageDetectTest {

	@Autowired
	LanguageDetect detect;
	
	@Autowired
	LanguageData data;
	
	@Test
	public void getLanguageProfilesTest(){
		
		boolean exceptionOccured = false;
		try {
			List<LanguageData> list = detect.getLanguageProfiles();
			list.add(new LanguageData());
			
		} catch (Exception e) {
			// TODO: handle exception
			exceptionOccured = true;
		}
		Assert.assertTrue(exceptionOccured);
	}
}

package com.richard.vo;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


public class LanguageDataTest {

	
	@Test
	public void getNameTest(){
		LanguageData data = new LanguageData();
		data.setName("test");
		
		Assert.assertSame("test", data.getName());
	}
	
	@Test
	public void getNgramInstanceTest(){
		LanguageData data = new LanguageData();
		Map<String,Integer> nGramInstance = new HashMap<String,Integer>();
		nGramInstance.put("a", 1);
		data.setnGramInstance(nGramInstance);
		Assert.assertNotNull(nGramInstance);
	}
}

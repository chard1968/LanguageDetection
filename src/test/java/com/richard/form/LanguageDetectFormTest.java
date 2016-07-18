package com.richard.form;

import org.junit.Assert;
import org.junit.Test;

public class LanguageDetectFormTest {

	@Test
	public void getLanguageNameTest(){
		LanguageDetectForm form = new LanguageDetectForm();
		form.setLanguageName("test");
		
		Assert.assertSame("test", form.getLanguageName());
	}
	
	@Test
	public void getTextTest(){
		LanguageDetectForm form = new LanguageDetectForm();
		form.setText("test");
		
		Assert.assertSame("test", form.getText());
	}
}

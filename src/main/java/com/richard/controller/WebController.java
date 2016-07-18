package com.richard.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.richard.exception.LanguageDetectorException;
import com.richard.form.LanguageDetectForm;
import com.richard.language.LanguageDetect;
import com.richard.vo.LanguageData;


/**
 * @author richard
 *
 */
@Controller
public class WebController extends WebMvcConfigurerAdapter {

	@Autowired
	private LanguageDetect bo;

	@RequestMapping(value="/form", method=RequestMethod.GET)
    public String showForm(LanguageDetectForm languageForm) {
    	
        return "form";
    }

    @RequestMapping(value="/", method=RequestMethod.POST)
    public String displayLanguageInfo(@Valid LanguageDetectForm languageForm, BindingResult bindingResult,Model model) {
    	try {
			LanguageData detectedLanguage = bo.detectLanguage(languageForm.getText());
			languageForm.setLanguageName(detectedLanguage.getName());
			model.addAttribute("languageName","Language is in "+detectedLanguage.getName());
		} catch (LanguageDetectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "form";
    }
}

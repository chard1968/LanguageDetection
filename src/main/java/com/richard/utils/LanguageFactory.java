package com.richard.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.richard.vo.LanguageData;

@Component
public class LanguageFactory {

	@Autowired
	private NGramExtractor nGramExtractor;
	
	public void updateLanguageProfile(LanguageData langData, Map<String, Integer> newMap) {
		Map<String, Integer> existingMap = langData.getnGramInstance();
		if (null == existingMap) {
			existingMap = new HashMap<String, Integer>();
			langData.setnGramInstance(existingMap);
		}
		for (Map.Entry<String, Integer> entry : newMap.entrySet()) {
			nGramExtractor.countNgramInstances(entry.getKey(), entry.getValue(), existingMap);
		}

	}
	
	public LanguageData getLanguageProfile(String langName, List<LanguageData> languageData) {
		LanguageData langData;
		Optional<LanguageData> langProfileOptional = languageData.stream()
				.filter(l -> langName.equalsIgnoreCase(l.getName())).findFirst();
		if (langProfileOptional.isPresent()) {
			langData = langProfileOptional.get();
		} else {
			langData = new LanguageData();
			langData.setName(langName);
			languageData.add(langData);
		}
		return langData;
	}

}

package com.richard.language;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.richard.exception.LanguageDetectorException;
import com.richard.utils.ConfigFileReader;
import com.richard.utils.LanguageFactory;
import com.richard.utils.NGramExtractor;
import com.richard.vo.LanguageData;

/**
 * @author richard
 *
 */
@Component
public class LanguageDetect {
	
	@Autowired
	private NGramExtractor nGramExtractor;
	
	@Autowired
	private LanguageFactory languageFactory;

	@Autowired
	ConfigFileReader configReader;

	private List<LanguageData> languageProfiles;
	
	@PostConstruct
	public List<LanguageData> learnLanguages() throws LanguageDetectorException {
		languageProfiles = new ArrayList<LanguageData>();
		List<Path> filePaths = configReader.getFilePaths(configReader.getPath());

		for (Path filePath : filePaths) {
			String langName = configReader.getLanguageName(filePath);
			LanguageData langProfile = languageFactory.getLanguageProfile(langName, languageProfiles);
			Map<String, Integer> nGramFreq = nGramExtractor.calculateNGramInstance(filePath);
			languageFactory.updateLanguageProfile(langProfile, nGramFreq);
		}

		languageProfiles.forEach(l -> l.setnGramInstance(nGramExtractor.sortNGramInstance(l.getnGramInstance())));
		return languageProfiles;
	}
	
	public List<LanguageData> getLanguageProfiles() {
		return languageProfiles;
	}

	public void setLanguageProfiles(List<LanguageData> languageProfiles) {
		this.languageProfiles = languageProfiles;
	}
	public LanguageData detectLanguage(String inputText) throws LanguageDetectorException {
		List<LanguageData> languagesData = getLanguageProfiles();
		LanguageData languageData = this.chooseLanguageData(inputText, languagesData);
		return languageData;
	}

	private LanguageData chooseLanguageData(String inputStr, List<LanguageData> languagesData)
			throws LanguageDetectorException {
		Map<LanguageData, Long> languageProfilesDistanceMap = new HashMap<LanguageData, Long>();
		Map<String, Integer> input = nGramExtractor.calculateNGramInstance(inputStr);
		
		input = nGramExtractor.sortNGramInstance(input);
		
		for (LanguageData langData : languagesData) {
			Map<String, Integer> langNGramInstance = langData.getnGramInstance();
			Long difference = nGramExtractor.measureDifference(input, langNGramInstance);
			languageProfilesDistanceMap.put(langData, difference);
		}
		
		return languageProfilesDistanceMap.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
	}
	
	

}

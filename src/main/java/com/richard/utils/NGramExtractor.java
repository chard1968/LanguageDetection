package com.richard.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.richard.exception.LanguageDetectorException;

@Component
public class NGramExtractor {
	
	@Autowired
	private ConfigFileReader configReader;

	public HashMap<String, Integer> calculateNGramInstance(Path filePath) throws LanguageDetectorException {
		List<String> words = configReader.readFile(filePath);
		return this.calculateNGramInstance(words);
	}

	
	public HashMap<String, Integer> calculateNGramInstance(String inputStr) throws LanguageDetectorException {
		List<String> words = configReader.getWords(inputStr);
		return this.calculateNGramInstance(words);
	}

	
	public HashMap<String, Integer> calculateNGramInstance(List<String> words) {
		HashMap<String, Integer> nGramInstance = new HashMap<String, Integer>();
		for (String w : words) {
			List<String> nGrams = this.createNGrams(w, configReader.getNGramMin(),
					configReader.getNGramMax());
			nGrams.forEach(n -> this.countNgramInstances(n, 1, nGramInstance));
		}
		return nGramInstance;
	}
	
	
	
	public List<String> createNGrams(String word, int min, int max) {
		List<String> nGramList = new ArrayList<>();
		for (int size = min; size <= max; size++){
			word = "_".concat(word).concat("_");
			size = size > word.length() ? word.length() : size;
			for (int i = 0; i < word.length() && i + size <= word.length(); i++) {
				nGramList.add(word.substring(i, i + size));
			}
		}
		return nGramList;
	}
	
	public Long measureDifference(Map<String, Integer> inputNGramInstance, Map<String, Integer> languageNGramInstance) {

		Long diffCount = 0l;
		List<String> ngramList = new LinkedList<String>();
		inputNGramInstance.forEach((key, value) -> ngramList.add(key));
		List<String> langNGramList = new LinkedList<String>();
		languageNGramInstance.forEach((key, value) -> langNGramList.add(key));

		for (String input : ngramList) {
			Integer diff = 0;
			if (langNGramList.contains(input)) {
				diff = langNGramList.indexOf(input) - ngramList.indexOf(input);
			} else {
				diff = langNGramList.size() < configReader.getNGramLimit() ? langNGramList.size()
						: configReader.getNGramLimit();
			}
			diffCount += diff;
		}

		return diffCount;
	}
	
	
	public Map<String, Integer> sortNGramInstance(Map<String, Integer> nGramInstanceMap) {
		return nGramInstanceMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(configReader.getNGramLimit())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entity1, entity2) -> entity1, LinkedHashMap::new));
	}
	
	
	public void countNgramInstances(String nGram, Integer count, Map<String, Integer> nGramInstance) {
		if (nGramInstance.containsKey(nGram)) {
			Integer freq = (Integer) nGramInstance.get(nGram);
			freq += count;
			nGramInstance.put(nGram, freq);
		} else {
			nGramInstance.put(nGram, count);
		}
	}
}

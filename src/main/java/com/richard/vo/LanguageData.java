package com.richard.vo;

import java.util.Map;


public class LanguageData {
	private String name;

	private Map<String, Integer> nGramInstance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Integer> getnGramInstance() {
		return nGramInstance;
	}

	public void setnGramInstance(Map<String, Integer> nGramInstance) {
		this.nGramInstance = nGramInstance;
	}

	
}

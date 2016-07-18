package com.richard.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.richard.exception.LanguageDetectorException;

/**
 * @author richard
 *
 */
@Component
public class ConfigFileReader {
	
	private static final String CONFIG_FILE_NAME = "ngram.properties";
	private static final String NGRAM_MIN = "ngram.min";
	private static final String NGRAM_MAX = "ngram.max";
	private static final String NGRAM_LIMIT = "ngram.limit";
	private static final String PATH = "path";

	private final Properties configProp = new Properties();
	
	private static final String TEXT_REGEX = "[^ 'A-Za-z]+";
	private static final String SPACE = "\\s+";
	private static final String DOT = "\\.";
	
	public List<String> readFile (Path filePath) throws LanguageDetectorException{
		List<String> words;
		try (Stream<String> lines = Files.lines(filePath)) {
			words = lines.map(line -> line.replaceAll(TEXT_REGEX, "").split(SPACE) ).flatMap(Arrays::stream).collect(Collectors.toList());
		} catch (IOException e) {
			throw new LanguageDetectorException(e.getMessage());
		}
		return words;
	}
	
	
	public List<Path> getFilePaths (String directoryPath) throws LanguageDetectorException{
		List<Path> filePaths = null;
		try {
			filePaths = Files.walk(Paths.get(directoryPath)).filter(f -> Files.isRegularFile(f)).collect(Collectors.toList());
		} catch (IOException e) {
			throw new LanguageDetectorException(e.getMessage());
		}
		return filePaths;
	}
	
	
	public List<String> getWords(String inputStr){
		return Arrays.asList(inputStr.replaceAll(TEXT_REGEX, "").split(SPACE));
	}
	
	
	public String getLanguageName(Path filePath) {
		String fileName = filePath.getFileName().toString();
		String languageName = fileName.split(DOT)[0];
		return languageName;
	}

	public ConfigFileReader() throws LanguageDetectorException {
		File file = new File(CONFIG_FILE_NAME);
		try (FileInputStream fileInput = new FileInputStream(file)) {

			configProp.load(fileInput);

		} catch (IOException e) {
			throw new LanguageDetectorException(e.getMessage());
		}
	}

	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	
	public String getPath() {
		return this.getProperty(PATH);
	}
	
	public int getNGramMin() {
		return Integer.parseInt(this.getProperty(NGRAM_MIN));
	}
	
	public int getNGramMax() {
		return Integer.parseInt(this.getProperty(NGRAM_MAX));
	}
	
	public int getNGramLimit() {
		return Integer.parseInt(this.getProperty(NGRAM_LIMIT));
	}
}

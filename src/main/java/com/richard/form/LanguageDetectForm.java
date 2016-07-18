package com.richard.form;


/**
 * @author richard
 *
 */
public class LanguageDetectForm {

    private String text;
    private String languageName;
    
    public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * @return text
	 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
        return "Detect(Text: " + this.text + ")";
    }
}

package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class SpellChecker {
	
	private volatile static SpellChecker instance;
	private Map<String, String> dictionary = new HashMap<String, String>();
	
	public static SpellChecker getInstance(){
		if (instance == null){
			synchronized (SpellChecker.class) {
				if (instance == null){
					instance = new SpellChecker();
				}
			}
		}
		
		return instance;
	}
	
	public void LoadDictionary(String dictionaryPath){
		BufferedReader reader =  null;
		try {
			String word;
			reader = new BufferedReader(new FileReader(dictionaryPath));
			while ((word = reader.readLine()) != null) {
				this.dictionary.put(word, word);
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public Boolean isMisspelled(String word){
		return !this.dictionary.containsKey(word);
	}
}

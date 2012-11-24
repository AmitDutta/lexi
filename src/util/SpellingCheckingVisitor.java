package util;

import java.util.ArrayList;
import java.util.List;

import viewmodel.UiGlyph;

import model.*;

public class SpellingCheckingVisitor extends IVisitor{

	private StringBuffer currentWord;
	private List<String> misspellings;
	
	public SpellingCheckingVisitor(){
		this.currentWord = new StringBuffer();
		this.misspellings = new ArrayList<String>();
	}
	
	@Override
	public void visitChar(Char character) {
		if (Character.isAlphabetic(character.getCharaCode())){
			currentWord.append(character.getChar());
		}
		else {
			String word = this.currentWord.toString();
			if (!word.equals("") && SpellChecker.getInstance().isMisspelled(word)){
				this.misspellings.add(word);
				System.out.println(word + " is misspelled!");
			}
			
			currentWord = new StringBuffer();
		}
	}

	@Override
	public void visitPicture(Picture picture) {
	}

	@Override
	public void visitRow(Row row) {
		List<UiGlyph> glyphs = row.getUiGlyphs();
		for (UiGlyph uiGlyph : glyphs){
			uiGlyph.getGlyph().accept(this);
		}
		
		/*This chekcing is required for last word. */
		if (this.currentWord.length() > 0){
			String word = this.currentWord.toString();
			if (!word.equals("") && SpellChecker.getInstance().isMisspelled(word)){
				this.misspellings.add(word);
				System.out.println(word + " is misspelled!");
			}
			
			currentWord = new StringBuffer();
		}
	}
	
	public List<String> getMisspellings(){
		return this.misspellings;
	}
}

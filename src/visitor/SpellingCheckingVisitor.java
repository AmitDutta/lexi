package visitor;

import java.util.ArrayList;
import java.util.List;

import util.ISplleingErrorHandler;
import util.SpellChecker;
import viewmodel.UiGlyph;

import model.*;

public class SpellingCheckingVisitor extends IVisitor{

	private StringBuffer currentWord;
	private List<Glyph> currentGlyphs;
	private List<String> misspellings;
	private List<UiGlyph> uiGlyphs;
	private ISplleingErrorHandler spllingErrorHandler;
	
	public SpellingCheckingVisitor(){
		this.currentWord = new StringBuffer();
		this.misspellings = new ArrayList<String>();
		this.currentGlyphs = new ArrayList<Glyph>();
		this.uiGlyphs = new ArrayList<UiGlyph>();
	}
	
	public SpellingCheckingVisitor(ISplleingErrorHandler spllingErrorHandler){
		this();
		this.spllingErrorHandler = spllingErrorHandler;
	}
	
	@Override
	public void visitChar(Char character) {
		if (Character.isAlphabetic(character.getCharaCode()) || Character.isDigit(character.getCharaCode())){
			this.currentWord.append(character.getChar());
			this.currentGlyphs.add(character);
		}
		else {
			String word = this.currentWord.toString();
			if (!word.equals("") && SpellChecker.getInstance().isMisspelled(word)){
				this.misspellings.add(word);
				if (this.spllingErrorHandler != null){
					this.spllingErrorHandler.handleSpellingError(this.currentWord.toString(), this.uiGlyphs.toArray(new UiGlyph[this.uiGlyphs.size()]));
				}
			}
			
			this.currentWord = new StringBuffer();
			this.currentGlyphs.clear();
			this.uiGlyphs.clear();
		}
	}

	@Override
	public void visitPicture(Picture picture) {
	}	
	
	@Override
	public void visitRow(Row row) {
		List<UiGlyph> glyphs = row.getUiGlyphs();
		for (UiGlyph uiGlyph : glyphs){
			this.uiGlyphs.add(uiGlyph);
			uiGlyph.getGlyph().accept(this);
		}
		
		/*This checking is required for last word. */
		if (this.currentWord.length() > 0){
			String word = this.currentWord.toString();
			if (!word.equals("") && SpellChecker.getInstance().isMisspelled(word)){
				this.misspellings.add(word);
				if (this.spllingErrorHandler != null){
					this.spllingErrorHandler.handleSpellingError(this.currentWord.toString(), this.uiGlyphs.toArray(new UiGlyph[this.uiGlyphs.size()]));
				}
			}
			
			this.currentWord = new StringBuffer();
			this.currentGlyphs.clear();
		}
	}
	
	public List<String> getMisspellings(){
		return this.misspellings;
	}
}

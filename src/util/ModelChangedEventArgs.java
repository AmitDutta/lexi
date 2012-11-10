package util;

import java.util.*;
import model.*;;

public class ModelChangedEventArgs {
	
	private List<Glyph> glyphs;	
	
	public ModelChangedEventArgs(List<Glyph> glyphs){
		this.glyphs = glyphs;
	}
	
	public List<Glyph> getGlyphs(){
		return this.glyphs;
	}
}

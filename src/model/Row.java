package model;

import java.awt.Graphics;
import java.util.*;
import viewmodel.UiGlyph;

public class Row extends Glyph{
		
	private int startIndex;
	private int endIndex;
	private int left;
	private int top;	
	// private List<Glyph> items;
	private List<UiGlyph> uiGlyphs;
	
	public Row(){
		/* this.items = new ArrayList<Glyph>(); */
		this.uiGlyphs = new ArrayList<UiGlyph>();
	}
	
	/*public List<Glyph> getItems(){
		return this.items;
	}*/
	
	public List<UiGlyph> getUiGlyphs(){
		return this.uiGlyphs;
	}
	
	@Override
	public int getHeight(){
		int height = 0;
		/*for (Glyph glyph : this.items){
			if (height < glyph.getHeight()){
				height = glyph.getHeight();
			}
		}*/
		
		for (UiGlyph uiGlyph : this.uiGlyphs){
			if (height < uiGlyph.getGlyph().getHeight()){
				height = uiGlyph.getGlyph().getHeight();
			}			
		}
		
		return height + util.Constants.newLineGap;
	}
	
	@Override
	public int getWidth(){
		int width = 2;
		/*for (Glyph glyph : this.items){
			width += glyph.getWidth();
		}*/
		
		for (UiGlyph uiGlyph : this.uiGlyphs){
			width += uiGlyph.getGlyph().getWidth();
		}
		
		return width;
	}	
	
	@Override
	public void draw(Graphics graphics, int x, int y){		
		int currentLeft = x;
		/*for(Glyph glyph: items){			
			glyph.draw(graphics, currentLeft, y);
			currentLeft += glyph.getWidth() + 2;
		}*/
		for (UiGlyph uiGlyph : this.uiGlyphs){
			uiGlyph.getGlyph().draw(graphics, currentLeft, y);
			currentLeft += uiGlyph.getGlyph().getWidth() + 2;
		}
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
}
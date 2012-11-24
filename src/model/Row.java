package model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import viewmodel.UiGlyph;
import visitor.IVisitor;

public class Row extends Glyph{
		
	private int startIndex;
	private int endIndex;
	private int left;
	private int top;
	private List<UiGlyph> uiGlyphs;
	
	public Row(){		
		this.uiGlyphs = new ArrayList<UiGlyph>();
	}
	
	public List<UiGlyph> getUiGlyphs(){
		return this.uiGlyphs;
	}
	
	@Override
	public int getHeight(){
		int height = 0;
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
		for (UiGlyph uiGlyph : this.uiGlyphs){
			width += uiGlyph.getGlyph().getWidth();
		}
		
		return width;
	}	
	
	@Override
	public void draw(Graphics graphics, int x, int y){		
		int currentLeft = x;
		for (UiGlyph uiGlyph : this.uiGlyphs){
			uiGlyph.getGlyph().draw(graphics, currentLeft, y);
			currentLeft += uiGlyph.getGlyph().getWidth() + 2;
		}
	}
	
	@Override
	public void select(Graphics graphics, int x, int y){
		this.select(graphics, x, y, 0, this.getUiGlyphs().size() - 1);
	}
	
	public void select(Graphics graphics, int x, int y, int start, int end){
		for (int i = start; i <= end; i++){
			UiGlyph uiGlyph = this.getUiGlyphs().get(i);				
			uiGlyph.getGlyph().select(graphics, uiGlyph.getPosition().x, uiGlyph.getPosition().y);
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

	@Override
	public void accept(IVisitor visitor) {
		visitor.visitRow(this);
	}
}
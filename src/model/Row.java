package model;

import java.util.*;

import ui.IGraphics;

public class Row extends Glyph{
		
	private List<Glyph> items;
	
	public Row(){
		this.items = new ArrayList<Glyph>();
	}
	
	public List<Glyph> getItems(){
		return this.items;
	}
	
	@Override
	public int getHeight(){
		int height = 0;
		for (Glyph glyph : this.items){
			if (height < glyph.getHeight()){
				height = glyph.getHeight();
			}
		}
		
		return height + 10;
	}
	
	@Override
	public int getWidth(){
		int width = 2;
		for (Glyph glyph : this.items){
			width += glyph.getWidth() + 2;
		}
		
		return width;
	}	
	
	@Override
	public void draw(IGraphics graphics, int x, int y){		
		int currentLeft = x;
		for(Glyph glyph: items){			
			glyph.draw(graphics, currentLeft, y);
			currentLeft += glyph.getWidth() + 2;
		}
	}
}
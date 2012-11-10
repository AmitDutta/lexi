package model;

import java.awt.Font;
import java.awt.FontMetrics;

import ui.*;

public class Char extends Glyph {
	
	private char ch;
	private Font font;
	private FontMetrics fontmetrics;
	
	public Char(char ch, Font font){
		this.ch = ch;
		this.font = font;
	}
	
	@Override
	public void draw(IGraphics graphics, int x, int y){
		if (this.fontmetrics == null){
			this.fontmetrics = graphics.getFontMetrics(this.font);
		}
		
		//TODO set the font before drawing		
		graphics.drawChar(ch, x, y);
	}
	
	@Override
	public int getWidth(){
		int width = 0;
		if (this.fontmetrics != null){
			width = this.fontmetrics.stringWidth(Character.toString(this.ch));
		}
		
		return width;
	}
	
	@Override
	public int getHeight(){
		int height = 0;
		if (this.fontmetrics != null){
			height = this.fontmetrics.getHeight();
		}
		
		return height;
	}
	
	@Override
	public String toString(){
		return "Character Glyph: ["+ this.ch + "]";
	}
}

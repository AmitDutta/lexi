package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import ui.IGraphics;

public class Picture extends Glyph {
	
	private BufferedImage image;
	
	public Picture(BufferedImage image){
		if (image == null){
			throw new NullPointerException("Image is null");
		}
		
		this.image = image;
	}
	
	@Override
	public void draw(Graphics graphics, int x, int y){
		graphics.drawImage(image, x, y, null);
	}	
	
	@Override
	public int getWidth(){
		return this.image.getWidth();
	}
	
	@Override
	public int getHeight(){
		return this.image.getHeight();
	}
}

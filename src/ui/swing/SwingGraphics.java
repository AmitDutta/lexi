package ui.swing;

import java.awt.*;
import java.awt.image.ImageObserver;

public class SwingGraphics implements ui.IGraphics {

	private Graphics graphics;
	
	public SwingGraphics(Graphics graphics){
		if (graphics == null){
			throw new NullPointerException("Graphics object can not be null.");
		}
		
		this.graphics = graphics;
	}
	
	@Override
	public void drawChar(char ch, int x, int y){
		this.graphics.drawString(Character.toString(ch), x, y);
	}
	
	@Override
	public void drawImage(Image img, int x, int y, ImageObserver observer){
		this.graphics.drawImage(img, x, y, null);
	}

	@Override
	public FontMetrics getFontMetrics(Font font) {
		return this.graphics.getFontMetrics(font);
	}
	
	@Override
	public void drawString(String str, int x, int y){
		this.graphics.drawString(str, x, y);
	}
}

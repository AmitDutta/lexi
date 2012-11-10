package util;

import java.awt.image.BufferedImage;

import ui.IGraphics;

public class InsertImageEventArgs extends ViewEventArgs{

	private BufferedImage image;
	
	public InsertImageEventArgs(IGraphics graphics, int top, int left, int frameWidth, int frameHeight, BufferedImage image){
		super(graphics, top, left, frameWidth, frameHeight);
		this.image = image;
	}
	
	public BufferedImage getImage(){
		return this.image;
	}
}

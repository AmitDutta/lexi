package util;

import ui.*;

public class ViewEventArgs {
	
	private IGraphics graphics;
	private int top;
	private int left;
	private int frameWidth;
	private int frameHeight;
	
	public ViewEventArgs(IGraphics graphics, int top, int left, int frameWidth, int frameHeight){
		this.graphics = graphics;
		this.top = top;
		this.left = left;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	public IGraphics getGraphics(){
		return this.graphics;
	}
	
	public int getTop(){
		return this.top;
	}
	
	public int getLeft(){
		return this.left;
	}
	
	public int getFrameWidth(){
		return this.frameWidth;
	}
	
	public int getFrameHeight(){
		return this.frameHeight;
	}
}

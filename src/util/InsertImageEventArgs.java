package util;

import java.awt.Graphics;

public class InsertImageEventArgs extends ViewEventArgs{

	private String filePath;
	
	public InsertImageEventArgs(Graphics graphics, int top, int left, int frameWidth, int frameHeight, String filePath){
		super(graphics, top, left, frameWidth, frameHeight);
		this.filePath = filePath;
	}
	
	public String getFilePath(){
		return this.filePath;
	}
}

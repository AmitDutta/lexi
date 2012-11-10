package util;

import java.awt.Font;
import java.awt.event.KeyEvent;

import ui.IGraphics;

public class KeyPressedEventArgs extends ViewEventArgs{
	
	private KeyEvent keyEvent;
	private Font font;
	
	public KeyPressedEventArgs(IGraphics graphics, int top, int left, int frameWidth, int frameHeight, KeyEvent keyEvent, Font font) {
		super(graphics, top, left, frameWidth, frameHeight);
		this.keyEvent = keyEvent;
		this.font = font;
	}
	
	public KeyEvent getKeyEvent(){
		return this.keyEvent;
	}
	
	public Font getFont(){
		return this.font;
	}
}

package model;

import java.awt.Graphics;

import util.IVisitor;

public abstract class Glyph {

	public abstract void draw(Graphics graphics, int x, int y);
	
	public abstract void select(Graphics graphics, int x, int y); 
	
	public abstract int getWidth();	
	
	public abstract int getHeight();
	
	public abstract void accept(IVisitor visitor);
}

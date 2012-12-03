package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import visitor.IVisitor;

public abstract class Glyph {

	public abstract void draw(Graphics graphics, int x, int y);

	public abstract void select(Graphics graphics, Color hightlightColor,
			Color fontColor, int x, int y);

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract Font getFont();
	
	public abstract void setFont(Font font);

	public abstract void accept(IVisitor visitor);
}

package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import visitor.IVisitor;

public class Char extends Glyph {

	private char ch;
	private Font font;
	private FontMetrics fontmetrics;

	public Char(char ch, Font font) {
		this.ch = ch;
		this.font = font;
	}

	@Override
	public void draw(Graphics graphics, int x, int y) {		
		this.fontmetrics = graphics.getFontMetrics(this.font);
		graphics.setFont(this.font);
		graphics.drawString(Character.toString(ch), x, y);
	}

	@Override
	public void select(Graphics graphics, Color hightlightColor,
			Color fontColor, int x, int y) {
		Color previousColor = graphics.getColor();
		graphics.setColor(hightlightColor);
		Rectangle2D rect = this.getFontrMetrics(graphics).getStringBounds(
				Character.toString(ch), graphics);
		graphics.drawRect(x, y - (int) rect.getHeight(), (int) rect.getWidth(),
				(int) rect.getHeight());
		graphics.fillRect(x, y - (int) rect.getHeight(), (int) rect.getWidth(),
				(int) rect.getHeight());
		graphics.setColor(fontColor);
		this.draw(graphics, x, y);
		graphics.setColor(previousColor);
	}

	@Override
	public int getWidth() {
		int width = 0;
		if (this.fontmetrics != null) {
			width = this.fontmetrics.stringWidth(Character.toString(this.ch));
		}

		return width;
	}

	@Override
	public int getHeight() {
		int height = 0;
		if (this.fontmetrics != null) {
			height = this.fontmetrics.getHeight();
		}

		return height;
	}

	@Override
	public String toString() {
		return "Character Glyph: [" + this.ch + "]";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visitChar(this);
	}

	@Override
	public void setFont(Font font) {
		this.font = font;		
	}

	@Override
	public Font getFont() {
		return this.font;
	}

	public int getCharacterCode() {
		return (int) this.ch;
	}

	public char getChar() {
		return this.ch;
	}

	private FontMetrics getFontrMetrics(Graphics graphics) {
		return graphics.getFontMetrics();
	}
}

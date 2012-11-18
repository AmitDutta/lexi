package viewmodel;

import java.awt.Point;

import model.*;

public class UiGlyph {
	
	private Glyph glyph;
	private Point position;
	private int physicalIndex;
	
	public UiGlyph(Glyph glyph, Point position, int physicalIndex){
		this.glyph = glyph;
		this.position = position;
		this.physicalIndex = physicalIndex;
	}
	
	public Glyph getGlyph(){
		return this.glyph;
	}
	
	public Point getPosition(){
		return this.position;
	}
	
	public int getPhysicalIndex(){
		return this.physicalIndex;
	}
}

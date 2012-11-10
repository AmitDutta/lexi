package model;

import ui.*;
import util.*;

// abstract class ? think
public class Glyph {

	public void draw(IGraphics graphics, int x, int y){		
	}
	
	public void insert(Glyph glyph, int i, ViewEventArgs param){		
	}
	
	// abstract ?
	public int getWidth(){
		return 0;
	}
	
	// abstract?
	public int getHeight(){
		return 0;
	}	
}

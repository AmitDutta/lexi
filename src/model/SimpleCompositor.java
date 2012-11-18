package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.*;
import viewmodel.UiGlyph;

public class SimpleCompositor implements ICompositor{
	
	public SimpleCompositor(){		
	}	

	@Override
	public List<Row> compose(List<Glyph> glyphs, ViewEventArgs args) {
		List<Row> rows = new ArrayList<Row>();		
		
		if (glyphs == null || glyphs.size() == 0){
			return rows;
		}
		
		Row currentRow = new Row();
		int currentTop = args.getTop();
		currentRow.setStartIndex(0); // glyph start position
		currentRow.setLeft(args.getLeft());
		currentRow.setTop(currentTop);
		rows.add(currentRow);
		int currentLeft = args.getLeft();		
		for (int i = 0; i < glyphs.size(); i++){
			Glyph glyph = glyphs.get(i);
			Point position = new Point(currentLeft, currentTop);
			UiGlyph uiGlyph = new UiGlyph(glyph, position, i);
			currentRow.getUiGlyphs().add(uiGlyph);
			// currentRow.getItems().add(glyph);			
			if ((currentLeft + 20) >= args.getFrameWidth()){
				currentRow.setEndIndex(i);
				if (i == glyphs.size() - 1){
					/*No need to add a new Row*/
					break;
				}
								
				currentTop += currentRow.getHeight();
				currentRow = new Row();				
				currentRow.setLeft(args.getLeft());
				currentRow.setTop(currentTop);
				currentRow.setStartIndex(i + 1);
				rows.add(currentRow);
				currentLeft = args.getLeft();												
			}
			else{				
				currentLeft += glyph.getWidth() + 2;
			}
		}
				
		return rows;
	}
}

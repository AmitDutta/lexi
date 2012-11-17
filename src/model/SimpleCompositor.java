package model;

import java.util.ArrayList;
import java.util.List;

import util.*;

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
		rows.add(currentRow);
		int currentLeft = args.getLeft();		
		for (Glyph glyph : glyphs){
			currentRow.getItems().add(glyph);			
			if ((currentLeft + 20) >= args.getFrameWidth()){
				currentRow = new Row();				
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

package model;

import java.util.ArrayList;
import java.util.List;

import util.*;

public class SimpleCompositor implements ICompositor{

	private Composition document;
	
	public SimpleCompositor(){		
	}

	@Override
	public void setComposition(Composition composition) {		
		this.document = composition; 
	}

	@Override
	public List<Row> compose(List<Glyph> glyphs, ViewEventArgs args) {
		/* TODO How to identify a row? When we hit the right boundary, we end a row. Keep a running total of maximum
		 * height and use that as row gap. Thus we will capture the maximum height with different font sized glyph.
		 * First we need to find a way to select and change font or fontsize in gui, then we can code this part. We have
		 * the font information in glyphs already.
		 * */
		
		List<Row> rows = new ArrayList<Row>();
		if (glyphs == null || glyphs.size() == 0){
			return rows;
		}
		
		Row currentRow = new Row();
		rows.add(currentRow);
		int currentLeft = args.getLeft();
		//int currentTop = args.getTop();
		//int maxHeightOfRow = Integer.MIN_VALUE;
		for (Glyph glyph : glyphs){
			currentRow.getItems().add(glyph);
			/*if (glyph.getHeight() > maxHeightOfRow){
				maxHeightOfRow = glyph.getHeight();
			}*/
			
			if ((currentLeft + 20) >= args.getFrameWidth()){
				//currentRow.setHeight(maxHeightOfRow + 10);
				//currentTop += maxHeightOfRow + 10;
				currentRow = new Row();				
				rows.add(currentRow);
				currentLeft = args.getLeft();
				//maxHeightOfRow = Integer.MIN_VALUE;
			}
			else{				
				currentLeft += glyph.getWidth() + 2;
			}
		}
				
		return rows;
	}
}

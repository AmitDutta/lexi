package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.List;

import model.Arrow;
import model.BackArrow;
import model.Char;
import model.Composition;
import model.Glyph;
import model.Picture;
import model.Row;
import util.Constants;
import util.InsertImageEventArgs;
import util.KeyPressedEventArgs;
import util.MenuPressedEventArgs;
import util.ViewEventArgs;
import viewmodel.ConcreteDocument;
import viewmodel.Document;
import viewmodel.ScrollableDocument;
import viewmodel.SelectionRange;
import viewmodel.UiGlyph;

import command.ICommand;
import command.InsertCommand;
import command.CommandManager;
import command.DeleteCommand;

public class EditorController implements IEditorController{
	
	private Composition document;
	private Document logicalDocument;
	private int index;
	public SelectionRange selectionRange;
	
	public EditorController(Composition document){
		this.index = 0;		
		this.document = document;
		this.logicalDocument = new ConcreteDocument();
	}
	
	public int getIndex(){
		return this.index;
	}

	@Override
	public void onKeyPressed(KeyPressedEventArgs param) {
		Glyph glyph = null;
		ICommand cmd = null;
		if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_ESCAPE){
			this.selectionRange = null;
		}
		else if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_DELETE){			 			
			if (this.selectionRange != null){								
				int startFrom = this.logicalDocument.getRows().get(this.selectionRange.getStartRow()).getUiGlyphs()
						.get(this.selectionRange.getStartCol()).getPhysicalIndex();				
				int endAt = this.logicalDocument.getRows().get(this.selectionRange.getEndRow()).getUiGlyphs()
						.get(this.selectionRange.getEndCol()).getPhysicalIndex();
				cmd = new DeleteCommand(document, startFrom, endAt);
				CommandManager.getInstance().execute(cmd);
				this.selectionRange = null;
			}
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'a'  && param.getKeyEvent().getKeyCode() == 65){
			glyph = new Arrow(param.getFont());			
			this.document.insert(glyph, this.document.getChildren().size());
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'b'  && param.getKeyEvent().getKeyCode() == 66){
			glyph = new BackArrow(param.getFont());			
			this.document.insert(glyph, this.document.getChildren().size());
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'z'  && param.getKeyEvent().getKeyCode() == 90){
			CommandManager.getInstance().undo();
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'y'  && param.getKeyEvent().getKeyCode() == 89){
			CommandManager.getInstance().redo();
		}
		else if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_PAGE_UP){			
			if (this.logicalDocument.needScrolling(param)){			
				if (index > 0){
					index -= 1;
				}
			}
		}
		else if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_PAGE_DOWN){			
			if (this.logicalDocument.needScrolling(param)){
				if (index < (this.logicalDocument.getRows().size() - 1)){
					index += 1;					
				}
			}			
		}
		else {
			if (!param.getKeyEvent().isControlDown()){				
					glyph = new Char(param.getKeyEvent().getKeyChar(), param.getFont());
					this.InsertGlyph(glyph);
					this.selectionRange = null;
			}
		}
	}

	@Override
	public void onImageInserted(InsertImageEventArgs param) {
		Glyph glyph = new Picture(param.getFilePath());
		this.InsertGlyph(glyph);
		this.selectionRange = null;
	}
	
	@Override
	public void onMenuItemPressed(MenuPressedEventArgs param){
		if (param.getMenuItem().getText() == Constants.ScrollOnText){
			// turn on scrolling
			List<Row> rows = this.logicalDocument.getRows();
			this.logicalDocument = new ScrollableDocument(this.logicalDocument);
			//this.logicalDocument = new BorderedDocument(new ScrollableDocument(this.logicalDocument));
			this.logicalDocument.setRows(rows);			
		}
		else{
			// turn scrolling off
			List<Row> rows = this.logicalDocument.getRows();
			this.logicalDocument = new ConcreteDocument();
			this.logicalDocument.setRows(rows);
			this.index = 0;
		}
	}
	
	@Override
	public void handleDrawing(List<Row> rows, ViewEventArgs args){
		this.logicalDocument.draw(rows, args, this.index);
		this.updateLogicalLocations(args);
		if (this.selectionRange != null){
			this.selectGlyphs(args);
		}		
	}
	
	public void selectGlyphs(ViewEventArgs args){
		int start, end;		
		for (int i = this.selectionRange.getStartRow(); i <= this.selectionRange.getEndRow(); i++){
			Row row = this.logicalDocument.getRows().get(i);
			start = 0;
			end = row.getUiGlyphs().size() - 1;
			if (i == this.selectionRange.getStartRow()){
				start = this.selectionRange.getStartCol();
			}
			
			if (i == this.selectionRange.getEndRow()){
				end = this.selectionRange.getEndCol();
			}
			
			row.select(args.getGraphics(), row.getTop(), row.getLeft(), start, end);
		}
	}
	
	/*update locations of Uiglyphs. required for selection to work in scrolling environment*/
	public void updateLogicalLocations(ViewEventArgs args){
		int i, j;
		Point dummyPoint = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
		for (i = 0; i < this.index; i++){
			Row currentRow = this.logicalDocument.getRows().get(i);
			currentRow.setTop(Integer.MIN_VALUE);
			currentRow.setLeft(Integer.MIN_VALUE);
			for (UiGlyph uiGlyph : currentRow.getUiGlyphs()){
				uiGlyph.setPosition(dummyPoint);
			}
		}
				
		/* calculate points exactly like the compositor */
		int currentTop = args.getTop();
		int currentLeft = args.getLeft();
		for (j = i; j < this.logicalDocument.getRows().size(); j++){
			Row currentRow = this.logicalDocument.getRows().get(j);
			currentRow.setTop(currentTop);
			currentRow.setLeft(currentLeft);
			for (UiGlyph uiGlyph : currentRow.getUiGlyphs()){
				Point position = new Point(currentLeft, currentTop);
				uiGlyph.setPosition(position);
				currentLeft += uiGlyph.getGlyph().getWidth() + 2;
			}
			
			currentTop += currentRow.getHeight();
			currentLeft = args.getLeft();
		}
	}
	
	@Override
	public Document getLogicalDocument(){		
		return this.logicalDocument;
	}
	
	@Override
	public void handleResize(){
		this.selectionRange = null;
		this.index = 0;
	}
	
	private void InsertGlyph(Glyph glyph){
		ICommand cmd = null;
		int physicalIndex = Integer.MIN_VALUE;
		if (this.selectionRange == null){
			/* Add at the end of document*/
			physicalIndex = this.document.getChildren().size();
		}
		else if (this.selectionRange != null && this.selectionRange.isSingleGlyphSelection()){
			physicalIndex = this.logicalDocument.getRows().get(this.selectionRange.getStartRow()).getUiGlyphs()
					.get(this.selectionRange.getStartCol()).getPhysicalIndex() + 1 ;
			
		}
		
		if (physicalIndex != Integer.MIN_VALUE){			
			cmd = new InsertCommand(this.document, glyph, physicalIndex);				
			CommandManager.getInstance().execute(cmd);
			this.selectionRange = null;
		}
	}
}
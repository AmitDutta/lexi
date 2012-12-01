package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;

import model.Arrow;
import model.Char;
import model.Composition;
import model.Glyph;
import model.Picture;
import model.Row;
import util.Constants;
import util.ISplleingErrorHandler;
import util.InsertImageEventArgs;
import util.KeyPressedEventArgs;
import util.MenuPressedEventArgs;
import util.ViewEventArgs;
import viewmodel.BorderedDocument;
import viewmodel.ConcreteDocument;
import viewmodel.Document;
import viewmodel.ScrollableDocument;
import viewmodel.SelectionRange;
import viewmodel.UiGlyph;
import visitor.IVisitor;
import visitor.SpellingCheckingVisitor;

import command.CommandManager;
import command.DeleteCommand;
import command.ICommand;
import command.InsertCommand;
import command.ToggleBoldCommand;
import command.ToggleItalicCommand;

public class EditorController implements IEditorController, ISplleingErrorHandler{
	
	private Composition document;
	private Document logicalDocument;
	private int index;
	private Boolean spellCheckEnabled;
	private SelectionRange selectionRange;
	private List<UiGlyph[]> misspelledGlyphs;
	private Graphics graphics;
	
	public EditorController(Composition document){
		this.index = 0;		
		this.document = document;
		this.logicalDocument = new ConcreteDocument();
		this.spellCheckEnabled = false;
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
				int startFrom = this.getStartFrom();
				int endAt = this.getEndAt(); 
				cmd = new DeleteCommand(document, startFrom, endAt);
				CommandManager.getInstance().execute(cmd);
				this.selectionRange = null;
			}
		}
		/*else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'w'  && param.getKeyEvent().getKeyCode() == 87){
			//Temporary spell checking by Control + W
			IVisitor visitor = new SpellingCheckingVisitor(this);
			this.misspelledGlyphs = new ArrayList<UiGlyph[]>();
			for(Row row : this.logicalDocument.getRows()){
				row.accept(visitor);
			}
		}*/
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'a'  && param.getKeyEvent().getKeyCode() == 65){
			glyph = new Arrow(param.getFont());
			this.document.insert(glyph, this.document.getChildren().size());
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'b'  && param.getKeyEvent().getKeyCode() == 66){
			int startFrom = this.getStartFrom();
			int endAt = this.getEndAt();
			cmd = new ToggleBoldCommand(param.getGraphics(), this.document, startFrom, endAt);
			CommandManager.getInstance().execute(cmd);
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'i'  && param.getKeyEvent().getKeyCode() == 73){
			int startFrom = this.getStartFrom();
			int endAt = this.getEndAt();
			cmd = new ToggleItalicCommand(param.getGraphics(), this.document, startFrom, endAt);
			CommandManager.getInstance().execute(cmd);
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
			// this.logicalDocument = new BorderedDocument(new ScrollableDocument(this.logicalDocument));
			this.logicalDocument.setRows(rows);			
		}
		else if (param.getMenuItem().getText() == Constants.ScrollOffText) {
			// turn scrolling off
			List<Row> rows = this.logicalDocument.getRows();
			this.logicalDocument = new ConcreteDocument();
			this.logicalDocument.setRows(rows);
			this.index = 0;
		}
		else if (param.getMenuItem().getText() == Constants.SpellCheckOnText){
			this.spellCheckEnabled = true;
		}
		else if (param.getMenuItem().getText() == Constants.SpellCheckOffText){
			this.spellCheckEnabled = false;
			this.misspelledGlyphs = null;
		}
	}
	
	@Override
	public void handleDrawing(List<Row> rows, ViewEventArgs args){
		this.logicalDocument.draw(rows, args, this.index);
		this.updateLogicalLocations(args);
		if (this.selectionRange != null){
			this.selectGlyphs(args);
		}
		
		if (this.spellCheckEnabled){
			IVisitor visitor = new SpellingCheckingVisitor(this);
			this.misspelledGlyphs = new ArrayList<UiGlyph[]>();
			for(Row row : rows){
				row.accept(visitor);
			}
		}
		
		if (this.misspelledGlyphs!= null && this.misspelledGlyphs.size() > 0){
			for (UiGlyph[] uiGlyphs : this.misspelledGlyphs){
				for (UiGlyph uiGlyph : uiGlyphs){
					uiGlyph.getGlyph().select(graphics, Color.RED, Color.WHITE, uiGlyph.getPosition().x, uiGlyph.getPosition().y);
				}				
			}
		}
	}
	
	@Override
	public void handleSpellingError(String word, UiGlyph[] glyphs) {
		System.out.println(glyphs.length);
		for (UiGlyph uiGlyph : glyphs){
			this.misspelledGlyphs.add(glyphs);
		}
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public int getStartFrom(){
		return this.logicalDocument.getRows().get(this.selectionRange.getStartRow()).getUiGlyphs()
				.get(this.selectionRange.getStartCol()).getPhysicalIndex();
	}
	
	public int getEndAt(){
		return this.logicalDocument.getRows().get(this.selectionRange.getEndRow()).getUiGlyphs()
		.get(this.selectionRange.getEndCol()).getPhysicalIndex();
	}
	
	public void setSelectionRange(SelectionRange selectionRange){
		if (selectionRange != null){
			this.selectionRange = selectionRange;
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
			
			row.select(args.getGraphics(), Color.BLACK, Color.WHITE, row.getTop(), row.getLeft(), start, end);
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
	
	public void setGraphics(Graphics graphics){
		this.graphics = graphics;
	}
}
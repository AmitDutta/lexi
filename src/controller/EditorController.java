package controller;

import java.awt.event.KeyEvent;
import java.util.List;

import model.*;
import viewmodel.*;
import util.*;

public class EditorController implements IEditorController{
	
	private Composition document;
	private Document logicalDocument;
	private int index;
	
	public EditorController(Composition document){
		this.index = 0;
		this.document = document;
		this.logicalDocument = new ConcreteDocument();
	}

	@Override
	public void onKeyPressed(KeyPressedEventArgs param) {
		Glyph glyph = null;		
		if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'a'  && param.getKeyEvent().getKeyCode() == 65){
			glyph = new Arrow(param.getFont());			
			this.document.insert(glyph, this.document.getChildren().size(), param);
		}
		else if (param.getKeyEvent().isControlDown() && param.getKeyEvent().getKeyChar() != 'b'  && param.getKeyEvent().getKeyCode() == 66){
			glyph = new BackArrow(param.getFont());			
			this.document.insert(glyph, this.document.getChildren().size(), param);
		}
		else if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_PAGE_UP){
			System.out.println(this.logicalDocument.needScrolling(param));
			if (this.logicalDocument.needScrolling(param)){
				if (index > 0){
					index -= 1;
				}
				
				this.logicalDocument = new ScrollableDocument(this.logicalDocument, this.index);
			}
		}
		else if (param.getKeyEvent().getKeyCode() == KeyEvent.VK_PAGE_DOWN){
			System.out.println(this.logicalDocument.needScrolling(param));
			if (this.logicalDocument.needScrolling(param)){
				if (index < (this.logicalDocument.getRows().size() - 1)){
					index += 1;
				}
				
				this.logicalDocument = new ScrollableDocument(this.logicalDocument, this.index);
			}			
		}
		else{			
			if (!param.getKeyEvent().isControlDown()){
				glyph = new Char(param.getKeyEvent().getKeyChar(), param.getFont());
				this.document.insert(glyph, this.document.getChildren().size(), param);
			}			
		}
		
		//Glyph glyph = new Char(param.getKeyEvent().getKeyChar(), param.getFont());
		//this.document.insert(glyph, this.document.getChildren().size(), param);
	}

	@Override
	public void onImageInserted(InsertImageEventArgs param) {
		Glyph glyph = new Picture(param.getImage());
		this.document.insert(glyph, this.document.getChildren().size(), param);
	}
	
	@Override
	public void onMenuItemPressed(MenuPressedEventArgs param){
		if (param.getMenuItem().getText() == Constants.ScrollOnText){
			// turn on scrolling
			this.logicalDocument = new ScrollableDocument(this.logicalDocument, 0);
		}
		else{
			// turn scrolling off
			List<Row> rows = this.logicalDocument.getRows();
			this.logicalDocument = new ConcreteDocument();
			this.logicalDocument.setRows(rows);
		}
	}
	
	@Override
	public Document getLogicalDocument(){		
		return this.logicalDocument;
	}
}
package viewmodel;

import java.util.List;

import util.ViewEventArgs;
import model.*;

public class ScrollableDocument extends DocumentDecorator{
	
	private Document document;
	private int from;
	private List<Row> rows;
	
	public ScrollableDocument(Document document, int from){
		this.document = document;
		this.from = from;
		this.rows = this.document.getRows();
	}
	
	public List<Row> getRows(){
		return this.rows;
	}
	
	public void setRows(List<Row> rows){
		this.rows = rows;
	}
	
	@Override
	public void draw(List<Row> rows, ViewEventArgs args){		
		if (this.from >= this.rows.size()){
			this.from = 0;
		}
		
		if (this.needScrolling(args)){
			this.draw(rows, args, from);
		}
		else{
			this.draw(rows, args, 0);
		}
	}
	
	@Override
	public void draw(List<Row> rows, ViewEventArgs args, int from){
		this.setRows(rows);
		this.document.draw(rows, args, from);
	}
	
	@Override
	public Boolean needScrolling(ViewEventArgs args){		
		Boolean needScrolling = false;
		int totalHeight = 0;
		for (Row row : this.document.getRows()){
			totalHeight += row.getHeight();
		}
		
		if (totalHeight > args.getFrameHeight()){
			needScrolling = true;
		}
		
		return needScrolling;
	}
}

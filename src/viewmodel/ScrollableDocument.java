package viewmodel;

import java.util.List;

import util.ViewEventArgs;
import model.*;

public class ScrollableDocument extends DocumentDecorator{
	
	private Document document;	
	private List<Row> rows;
	
	public ScrollableDocument(Document document){
		this.document = document;		
		this.rows = this.document.getRows();
	}
	
	public List<Row> getRows(){
		return this.rows;
	}
	
	public void setRows(List<Row> rows){
		this.rows = rows;
	}
	
	@Override
	public void draw(List<Row> rows, ViewEventArgs args, int from){
		if (from >= rows.size()){
			from = 0;
		}
		
		this.setRows(rows);		
		//System.out.println("at decorator: from: " + from + " rows: " + rows.size());
		this.document.draw(rows, args, from);
		//System.out.println("AFTER: at decorator: from: " + from + " rows: " + rows.size());
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

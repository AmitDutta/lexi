package viewmodel;

import java.util.ArrayList;
import java.util.List;

import model.Row;
import util.ViewEventArgs;

public abstract class Document {
	
	private List<Row> rows;
	
	public Document(){
		this.rows = new ArrayList<Row>();
	}
	
	public Document(List<Row> rows){
		this.rows = rows;
	}
	
	public List<Row> getRows(){
		return this.rows;
	}
	
	public void setRows(List<Row> rows){
		this.rows = rows;
	}
	
	public abstract void draw(List<Row> rows, ViewEventArgs args, int from);
	
	public abstract Boolean needScrolling(ViewEventArgs args);
}

package viewmodel;

import java.util.List;

import util.ViewEventArgs;

import model.Row;

public class BorderedDocument extends Document{
	
	private Document document;	
	//private List<Row> rows;
	
	public BorderedDocument(Document document){
		this.document = document;		
		//this.rows = this.document.getRows();
	}
	
	/*public List<Row> getRows(){
		return this.rows;
	}
	
	public void setRows(List<Row> rows){
		this.rows = rows;
	}*/

	@Override
	public void draw(List<Row> rows, ViewEventArgs args, int from) {
		//this.setRows(rows);
		args.getGraphics().drawRect(args.getLeft() - 2, args.getTop() - 10, args.getFrameWidth() - 10, args.getFrameHeight() - 65);
		this.document.draw(rows, args, from);
	}

	@Override
	public Boolean needScrolling(ViewEventArgs args) {		
		return this.document.needScrolling(args);	
		/*Boolean needScrolling = false;
		int totalHeight = 0;
		for (Row row : this.document.getRows()){
			totalHeight += row.getHeight();
		}
		
		if (totalHeight > args.getFrameHeight()){
			needScrolling = true;
		}
		
		return needScrolling;*/
	}
}

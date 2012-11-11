package viewmodel;

import java.util.List;

import model.Row;
import util.ViewEventArgs;

public class ConcreteDocument extends Document{
	
	@Override
	public void draw(List<Row> rows, ViewEventArgs args){		
		this.draw(rows, args, 0);
	}
	
	@Override
	public void draw(List<Row> rows, ViewEventArgs args, int from){		
		this.setRows(rows);
		//System.out.println(rows.size() + " " + this.getRows().size());
		if (this.getRows() != null && this.getRows().size() > 0){
			int currentTop = args.getTop();
			System.out.println("at main draw: from: "  + from + " row size: " + this.getRows().size()  );
			for (int i = from; i < this.getRows().size(); i++){				
				Row row = this.getRows().get(i);
				row.draw(args.getGraphics(), args.getLeft(), currentTop);				
				currentTop += row.getHeight();
			}
		}
	}
	
	@Override
	public Boolean needScrolling(ViewEventArgs args){
		return false;
	}

	@Override
	public void setIndex(int index) {
		// TODO Auto-generated method stub		
		System.out.println("here at concrete setindex!!");
	}
}

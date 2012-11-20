package viewmodel;

public class SelectionRange {
	
	private int startRow;
	private int startCol;
	private int endRow;
	private int endCol;	
	
	public SelectionRange(){		
	}
	
	public SelectionRange(int startRow, int startCol, int endRow, int endCol){
		this.setStartRow(startRow);
		this.setStartCol(startCol);
		this.setEndRow(endRow);
		this.setEndCol(endCol);
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}
	
	public String toString(){
		return "StartRow: " + this.startRow + ", StartCol: " + this.startCol + " EndRow: " + this.endRow + " EndCol: " + this.endCol;
	}
}

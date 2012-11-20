package command;

import model.Composition;
import model.Glyph;

public class InsertCommand implements ICommand{

	private int physicalIndex;
	private Composition document;
	private Glyph glyph;
	
	public InsertCommand(Composition document, Glyph glyph, int from){
		this.document = document;		
		this.glyph = glyph;
		this.physicalIndex = from;
	}
	
	@Override
	public boolean execute() {
		this.document.insert(this.glyph, this.physicalIndex);
		return true;
	}

	@Override
	public void unExecute() {
		this.document.remove(this.physicalIndex);
	}

	@Override
	public boolean canUndo() {
		return true;
	}
}

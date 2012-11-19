package command;

import util.KeyPressedEventArgs;
import model.*;

public class AppendCharCommand implements ICommand{

	private int physicalIndex;
	private Composition document;
	private Glyph glyph;
	
	public AppendCharCommand(Composition document, KeyPressedEventArgs param){
		this.document = document;		
		this.glyph = new Char(param.getKeyEvent().getKeyChar(), param.getFont());
	}
	
	@Override
	public boolean execute() {
		this.physicalIndex = this.document.getChildren().size();
		this.document.insert(glyph, this.physicalIndex);
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

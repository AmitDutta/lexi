package command;

import model.Composition;

public class LoadCommand implements ICommand{

	private String filePath;
	private Composition document;
	
	public LoadCommand(Composition document, String filePath) {
		this.document = document;
		this.filePath = filePath;
	}
	
	@Override
	public boolean execute() {
		Boolean val = true;
		try {			
			this.document.loadFromFile(filePath);			
		} catch (Exception ex) {
			ex.printStackTrace();
			val = false;
		}		
		
		return val;
	}

	@Override
	public void unExecute() {
	}

	@Override
	public boolean canUndo() {
		return false;
	}

}

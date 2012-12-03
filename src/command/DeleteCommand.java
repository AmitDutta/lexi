package command;

import java.util.ArrayList;
import java.util.List;

import model.Composition;
import model.Glyph;

public class DeleteCommand implements ICommand {

	private Composition document;
	private int startFrom;
	private int endAt;
	private List<Glyph> buffer;

	public DeleteCommand(Composition document, int startFrom, int endAt) {
		this.document = document;
		this.startFrom = startFrom;
		this.endAt = endAt;
		this.buffer = new ArrayList<Glyph>();
		for (int i = this.startFrom; i <= this.endAt; i++) {
			this.buffer.add(this.document.getChildren().get(i));
		}
	}

	@Override
	public boolean execute() {
		try {
			/* Always delete in reverse order */
			int i = this.endAt;
			while (i >= this.startFrom) {
				this.document.remove(i);
				i--;
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void unExecute() {
		try {
			int i, j;
			for (i = startFrom, j = 0; i <= this.endAt; i++, j++) {
				this.document.insert(this.buffer.get(j), i);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean canUndo() {
		return true;
	}
}

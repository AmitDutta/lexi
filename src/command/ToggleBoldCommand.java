package command;

import java.awt.Font;
import java.awt.Graphics;

import model.Composition;

public class ToggleBoldCommand implements ICommand {

	private Graphics graphics;
	private Composition document;
	private int startFrom;
	private int endAt;
	private Font previousFont;

	public ToggleBoldCommand(Graphics graphics, Composition document,
			int startFrom, int endAt) {
		this.graphics = graphics;
		this.document = document;
		this.startFrom = startFrom;
		this.endAt = endAt;
		this.previousFont = this.graphics.getFont();
	}

	@Override
	public boolean execute() {
		Boolean val = true;
		try {
			Font newFont = new Font(this.previousFont.getName(),
					this.previousFont.getStyle() | Font.BOLD,
					this.previousFont.getSize());
			this.document.updateFont(newFont, startFrom, endAt);
		} catch (Exception ex) {
			ex.printStackTrace();
			val = false;
		}

		return val;
	}

	@Override
	public void unExecute() {
		this.document.updateFont(this.previousFont, startFrom, endAt);
	}

	@Override
	public boolean canUndo() {
		return true;
	}
}

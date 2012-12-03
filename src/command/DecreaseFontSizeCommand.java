package command;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import model.Composition;

public class DecreaseFontSizeCommand implements ICommand {
	private Graphics graphics;
	private Composition document;
	private int startFrom;
	private int endAt;	
	private List<Font> previousFonts;

	public DecreaseFontSizeCommand(Graphics graphics, Composition document,
			int startFrom, int endAt) {
		this.graphics = graphics;
		this.document = document;
		this.startFrom = startFrom;
		this.endAt = endAt;
		this.loadPreviousFonts();
	}

	@Override
	public boolean execute() {
		Boolean val = true;
		try {
			List<Font> fonts = new ArrayList<Font>();
			for (int i = this.startFrom; i <= this.endAt; i++) {
				Font previousFont = this.document.getChildren().get(i)
						.getFont();
				Font newFont = new Font(previousFont.getName(),
						previousFont.getStyle(), previousFont.getSize() - 1);
				fonts.add(newFont);
			}

			this.document.updateFont(fonts, startFrom, endAt);
		} catch (Exception ex) {
			ex.printStackTrace();
			val = false;
		}

		return val;
	}

	@Override
	public void unExecute() {
		this.document
		.updateFont(this.previousFonts, this.startFrom, this.endAt);
	}

	@Override
	public boolean canUndo() {
		return true;
	}
	
	private void loadPreviousFonts() {
		this.previousFonts = new ArrayList<Font>();
		for (int i = this.startFrom; i <= this.endAt; i++) {
			this.previousFonts
					.add(this.document.getChildren().get(i).getFont());
		}
	}	
}

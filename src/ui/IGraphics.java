package ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public interface IGraphics {
	void drawChar(char ch, int x, int y);
	void drawImage(Image img, int x, int y, ImageObserver observer);
	void drawString(String str, int x, int y);
	FontMetrics getFontMetrics(Font font);
}

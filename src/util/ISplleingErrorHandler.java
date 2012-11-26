package util;

import java.util.List;

import viewmodel.UiGlyph;

import model.Glyph;

public interface ISplleingErrorHandler {
	void handleSpellingError(String word, UiGlyph[] glyphs);
}

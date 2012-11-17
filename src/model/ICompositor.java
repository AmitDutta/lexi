package model;

import java.util.List;

import util.*;

public interface ICompositor {	
	List<Row> compose(List<Glyph> glyphs, ViewEventArgs args);
	
}

package controller;

import java.util.List;

import util.*;
import model.*;
import viewmodel.Document;

public interface IEditorController {	
	void onKeyPressed(KeyPressedEventArgs param);	
	void onImageInserted(InsertImageEventArgs param);
	void onMenuItemPressed(MenuPressedEventArgs param);
	void handleDrawing(List<Row> rows, ViewEventArgs param);
	Document getLogicalDocument();
}
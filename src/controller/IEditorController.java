package controller;

import util.*;
import viewmodel.Document;

public interface IEditorController {	
	void onKeyPressed(KeyPressedEventArgs param);	
	void onImageInserted(InsertImageEventArgs param);
	void onMenuItemPressed(MenuPressedEventArgs param);
	Document getLogicalDocument();
}
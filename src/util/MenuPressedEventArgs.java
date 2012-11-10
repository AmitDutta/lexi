package util;

import javax.swing.JMenuItem;

public class MenuPressedEventArgs {

	private JMenuItem menuItem;
	
	public MenuPressedEventArgs(JMenuItem item){
		this.menuItem = item;
	}
	
	public JMenuItem getMenuItem(){
		return this.menuItem;
	}
	
	public void setJMenuItem(JMenuItem item){
		this.menuItem = item;
	}
}

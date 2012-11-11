package model;

import java.util.*;

import util.*;

public class Composition implements ISubject{

	private List<Glyph> children;
	private List<IObserver> observers;
	private List<Row> rows;
	private ICompositor compositor;
	
	public Composition(){		
		this.children = new ArrayList<Glyph>();
		this.compositor = new SimpleCompositor();
		this.compositor.setComposition(this);
		this.observers = new ArrayList<IObserver>();
	}
	
	public void insert(Glyph glyph, int i, ViewEventArgs args){
		this.children.add(i, glyph);		
		this.modelChanged(rows);
	}
	
	public void refresh(ViewEventArgs args){		
		this.modelChanged(rows);
	}
	
	public List<Glyph> getChildren(){
		return this.children;
	}

	@Override
	public void registerObserver(IObserver observer) {		
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		int index = this.observers.indexOf(observer);
		if (index >= 0){
			this.observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		ModelChangedEventArgs args = new ModelChangedEventArgs(this.getChildren());
		for(IObserver observer : this.observers){
			observer.updateObserver(args);
		}
	}
	
	public void	modelChanged(List<Row> rows){
		this.notifyObservers();
	}
}

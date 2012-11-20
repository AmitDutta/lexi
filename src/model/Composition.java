package model;

import java.util.*;

import util.*;

public class Composition implements ISubject{

	private List<Glyph> children;
	private List<IObserver> observers;		
	
	public Composition(){		
		this.children = new ArrayList<Glyph>();				
		this.observers = new ArrayList<IObserver>();
	}
	
	public void insert(Glyph glyph, int i){
		this.children.add(i, glyph);
		this.modelChanged();
	}
	
	public void remove(int i){
		this.children.remove(i);
		this.modelChanged();
	}
	
	public void refresh(ViewEventArgs args){		
		this.modelChanged();
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
	
	public void	modelChanged(){
		this.notifyObservers();
	}
}

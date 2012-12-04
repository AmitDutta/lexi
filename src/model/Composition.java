package model;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import util.IObserver;
import util.ISubject;
import util.ModelChangedEventArgs;
import util.ViewEventArgs;

public class Composition implements ISubject {

	private List<Glyph> children;
	private List<IObserver> observers;

	public Composition() {
		this.children = new ArrayList<Glyph>();
		this.observers = new ArrayList<IObserver>();
	}

	public void insert(Glyph glyph, int i) {
		this.children.add(i, glyph);
		this.modelChanged();
	}

	public void remove(int i) {
		this.children.remove(i);
		this.modelChanged();
	}

	public void refresh(ViewEventArgs args) {
		this.modelChanged();
	}

	public List<Glyph> getChildren() {
		return this.children;
	}

	public void updateFont(List<Font> fonts, int startFrom, int endAt) {
		int i, j;
		for (i = startFrom, j = 0; i <= endAt; i++, j++) {
			this.children.get(i).setFont(fonts.get(j));
		}

		this.modelChanged();
	}

	@Override
	public void registerObserver(IObserver observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		int index = this.observers.indexOf(observer);
		if (index >= 0) {
			this.observers.remove(index);
		}
	}

	@Override
	public void notifyObservers() {
		ModelChangedEventArgs args = new ModelChangedEventArgs(
				this.getChildren());
		for (IObserver observer : this.observers) {
			observer.updateObserver(args);
		}
	}

	public void modelChanged() {
		this.notifyObservers();
	}
}

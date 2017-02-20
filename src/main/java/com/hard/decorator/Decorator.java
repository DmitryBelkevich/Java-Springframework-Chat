package com.hard.decorator;

public abstract class Decorator implements Component {
	protected Component component;
	
	public Decorator(Component component) {
		this.component = component;
	}
	
	public String getStr() {
		return component.getStr();
	}
	
	@Override
	public String toString() {
		return getStr();
	}
}
package com.hard.decorator;

public class BracketsDecorator extends Decorator {
	public BracketsDecorator(Component component) {
		super(component);
	}
	
	public String getStr() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder
			.append("[")
			.append(component.getStr())
			.append("]")
			;
		
		return stringBuilder.toString();
	}
}
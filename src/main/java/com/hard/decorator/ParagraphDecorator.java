package com.hard.decorator;

public class ParagraphDecorator extends Decorator {
	public ParagraphDecorator(Component component) {
		super(component);
	}
	
	public String getStr() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder
			.append("<p>")
			.append(component.getStr())
			.append("</p>")
			;
		
		return stringBuilder.toString();
	}
}
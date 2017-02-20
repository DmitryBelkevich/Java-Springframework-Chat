package com.hard.decorator;

public class StringComponent implements Component {
	private String str;
	
	public StringComponent(String str) {
		this.str = str;
	}
	
	public void setStr(String str) {
		this.str = str;
	}
	
	@Override
	public String getStr() {
		return str;
	}
}
package com.hard.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class Client {
	private String name;
	private TextChat textChat;
	private HttpSession session;
	private List<Message> messages = new ArrayList<>();
	private boolean isTyping = false;
	
	public Client(String name, TextChat textChat, HttpSession session) {
		this.name = name;
		this.textChat = textChat;
		this.session = session;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTextChat(TextChat textChat) {
		this.textChat = textChat;
	}
	
	public TextChat getTextChat() {
		return textChat;
	}
	
	public void setSession(HttpSession session) {
		this.session = session;
	}
	
	public HttpSession getSession() {
		return session;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public void setTyping(boolean isTyping) {
		this.isTyping = isTyping;
	}
	
	public boolean isTyping() {
		return isTyping;
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	public void removeMessage(Message message) {
		messages.remove(message);
	}
	
	public void clearMessages() {
		messages.clear();
	}
	
	public void sendMessage(String message) {
		textChat.notifyClients(message, this);
	}
	
	public void getMessage(String message) {
		// logic
		System.out.println("Client " + getName() + " got message: " + message);
		
		addMessage(new Message(message));
	}
	
	@Override
	public String toString() {
		return "Client["
				+ "name=" + name
				//+ ", session=" + session
				+ ", messages=" + messages
				+ "]";
	}
}
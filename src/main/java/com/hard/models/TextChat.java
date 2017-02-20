package com.hard.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.hard.decorator.BracketsDecorator;
import com.hard.decorator.ParagraphDecorator;
import com.hard.decorator.StringComponent;

public class TextChat {
	private List<Client> clients = new ArrayList<>();
	
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	public List<Client> getClients() {
		return clients;
	}
	
	public void addClient(Client client) {
		clients.add(client);
	}
	
	public void deleteClient(Client client) {
		clients.remove(client);
	}
	
	public int getCountClients() {
		return clients.size();
	}
	
	public Client getClientBySession(HttpSession session) {
		for (Client client : clients)
			if (client.getSession().equals(session))
				return client;
		
		return null;
	}
	
	public void notifyClients(String message, Client client) {
		for (Client c : clients) {
			StringBuilder messageBuilder = new StringBuilder();
			
			messageBuilder
				.append(new BracketsDecorator(new StringComponent(client.getName())).getStr())
				.append(": ")
				.append(message)
				;
			
			c.getMessage(new ParagraphDecorator(new StringComponent(messageBuilder.toString())).getStr());
		}
	}
	
	@Override
	public String toString() {
		return "TextChat["
				+ "clients=" + clients
				+ "]";
	}
}
package com.hard.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hard.decorator.ParagraphDecorator;
import com.hard.decorator.StringComponent;
import com.hard.models.Client;
import com.hard.models.Message;
import com.hard.models.TextChat;
import com.hard.utils.NameGenerator;

@Controller
@RequestMapping("/polling")
public class PollingController {
	private TextChat textChat = new TextChat();
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String main(HttpSession session) {
		if (textChat.getClientBySession(session) == null) {
			Client client = new Client(NameGenerator.getName(session), textChat, session);
			
			textChat.addClient(client);
			
			client.sendMessage("*** " + client.getName() + " has joined room ***");
		}
		
		return "polling/main";
	}
	
	@RequestMapping(value = "/sendMessage", method = { RequestMethod.GET, RequestMethod.POST }, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public String sendMessage(HttpSession session, @RequestBody String message) {
		Client client = textChat.getClientBySession(session);
		
		if (client != null) {
			client.sendMessage(message);
			
			return "client has sended message: " + message;
		}
		
		return null;
	}
	
	@RequestMapping(value = "/getMessages", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getMessages(HttpSession session) {
		Client client = textChat.getClientBySession(session);
		
		if (client != null) {
			List<Message> messages = client.getMessages();
			
			StringBuilder messageBuilder = new StringBuilder();
			
			for (Message message : messages)
				messageBuilder.append(message.getText());
			
			client.clearMessages();
			
			return messageBuilder.toString();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/showOnline", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String showOnline() {
		List<Client> clients = textChat.getClients();
		
		StringBuilder clientsBuilder = new StringBuilder();
		
		if (clients != null) {
			for (Client client : clients) {
				clientsBuilder
					.append(new ParagraphDecorator(new StringComponent(client.getName())).getStr())
					;
			}
			
			return clientsBuilder.toString();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/showTyping", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String showTyping() {
		List<Client> clients = new ArrayList<>();
		
		for (Client client : textChat.getClients())
			if (client.isTyping())
				clients.add(client);
		
		StringBuilder clientsBuilder = new StringBuilder();
		
		if (clients.size() > 0) {
			for (Client client : clients) {
				clientsBuilder
					.append(new ParagraphDecorator(new StringComponent(client.getName())).getStr())
					;
			}
			
			return clientsBuilder.toString();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/close", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String close(HttpSession session) {
		Client client = textChat.getClientBySession(session);
		
		if (client != null) {
			client.sendMessage("*** " + client.getName() + " has left room ***");
			textChat.deleteClient(client);
			
			return "client has deleted";
		}
		
		return null;
	}
}
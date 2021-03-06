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
@RequestMapping("/long_polling")
public class LongPollingController {
	private TextChat textChat = new TextChat();
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String main(HttpSession session) {
		if (textChat.getClientBySession(session) == null) {
			Client client = new Client(NameGenerator.getName(session), textChat, session);
			
			textChat.addClient(client);
			
			client.sendMessage("*** " + client.getName() + " has joined room ***");
		}
		
		return "long_polling/main";
	}
	
	@RequestMapping(
		value = "/sendMessage",
		method = { RequestMethod.GET, RequestMethod.POST },
		consumes = "application/json; charset=UTF-8"
	)
	@ResponseBody
	public String sendMessage(HttpSession session, @RequestBody String message) {
		Client client = textChat.getClientBySession(session);
		
		if (client != null) {
			client.sendMessage(message);
			
			return "client has sended message: " + message;
		}
		
		return null;
	}
	
	@RequestMapping(
		value = "/getMessages",
		method = { RequestMethod.GET, RequestMethod.POST },
		produces = "text/plain; charset=Windows-1251"
	)
	@ResponseBody
	public String getMessages(HttpSession session) {
		Client client = textChat.getClientBySession(session);
		
		if (client != null) {
			while (client.getMessages().isEmpty())
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			List<Message> messages = client.getMessages();
			
			StringBuilder messageBuilder = new StringBuilder();
			
			for (Message message : messages)
				messageBuilder.append(message.getText());
			
			client.clearMessages();
			
			return messageBuilder.toString();
		}
		
		return null;
	}
	
	@RequestMapping(
		value = "/getActiveClients",
		method = { RequestMethod.GET, RequestMethod.POST },
		produces = "text/plain; charset=Windows-1251"
	)
	@ResponseBody
	public String getActiveClients() {
		List<Client> clients = textChat.getClients();
		
		StringBuilder clientsBuilder = new StringBuilder();
		
		if (clients != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (Client client : clients) {
				clientsBuilder
					.append(new ParagraphDecorator(new StringComponent(client.getName())).getStr())
					;
			}
			
			return clientsBuilder.toString();
		}
		
		return null;
	}
	
	@RequestMapping(
		value = "/getTypingClients",
		method = { RequestMethod.GET, RequestMethod.POST },
		produces = "text/plain; charset=Windows-1251"
	)
	@ResponseBody
	public String getTypingClients() {
		List<Client> clients = textChat.getClients();
		
		if (clients != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			List<Client> typingClients = new ArrayList<>();
			
			for (Client client : clients)
				if (client.isTyping())
					typingClients.add(client);
			
			StringBuilder clientsBuilder = new StringBuilder();
			
			if (typingClients.size() > 0) {
				for (Client client : typingClients) {
					clientsBuilder
						.append(new ParagraphDecorator(new StringComponent("<i class='fa fa-spinner fa-pulse fa-1g fa-fw'></i>" + client.getName())).getStr())
						;
				}
				
				return clientsBuilder.toString();
			}
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
	
	@RequestMapping(
		value = "/setTyping",
		method = { RequestMethod.GET, RequestMethod.POST },
		consumes = "application/json; charset=UTF-8"
	)
	@ResponseBody
	public String setTyping(HttpSession session, @RequestBody String b) {
		Client client = textChat.getClientBySession(session);
		client.setTyping(Boolean.valueOf(b));
		
		return null;
	}
}
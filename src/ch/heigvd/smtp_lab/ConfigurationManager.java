package ch.heigvd.smtp_lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import ch.heigvd.smtp_lab.model.Message;
import ch.heigvd.smtp_lab.model.Person;
import ch.heigvd.smtp_lab.smtp.SmtpClient;

public class ConfigurationManager {
	private static final Logger LOG = Logger.getLogger(ConfigurationManager.class.getName());

	private static ConfigurationManager instance;
	
	private int numberOfGroups;
	private String hostAddress;
	private int hostPort;
	private List<Person> victims;
	private List<Message> messages;
	
	private ConfigurationManager() {}
	
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		
		return instance;
	}
	
	public void readConfiguration(String filename) throws IOException {
		Files.lines(Paths.get(filename))
			.map(line->line.split("="))
			.forEach(param -> readParam(param[0], param[1]));
	}
	
	public void readVictims(String filename) throws IOException {
		victims = new ArrayList<Person>();
		Files.lines(Paths.get(filename)).forEach(line -> victims.add(new Person(line)));
	}
	
	public void readMessages(String filename) throws IOException {
		StringBuilder builder = new StringBuilder();
		messages = new LinkedList<Message>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			String line = br.readLine();
			Message message = new Message();
			message.setSubject(line);
		    
		    while ((line = br.readLine()) != null) {
		       if (line.startsWith("---")) {
		    	   message.setContent(builder.toString());
		    	   builder.setLength(0);
		    	   messages.add(message);
		    	   message = new Message();
		    	   message.setSubject(br.readLine());
		       } else {
		    	   builder.append(line+System.lineSeparator());
		       }
		    }
		}
	}
	
	private void readParam(String name, String value) {
		switch (name) {
		case "numberOfGroups":
			numberOfGroups = Integer.valueOf(value);
			break;
		case "hostAddress":
			hostAddress = value;
			break;
		case "hostPort":
			hostPort = Integer.valueOf(value);
			break;
		default:
			LOG.info("Unknown parameter read in config file : "+name+"="+value);
		}
	}
	
	public String getHostAddress() {
		return hostAddress;
	}
	
	public int getNumberOfGroups() {
		return numberOfGroups;
	}
	
	public int getHostPort() {
		return hostPort;
	}
	
	public List<Person> getVictims() {
		return victims;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
}

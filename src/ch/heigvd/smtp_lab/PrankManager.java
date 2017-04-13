package ch.heigvd.smtp_lab;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.heigvd.smtp_lab.model.Group;
import ch.heigvd.smtp_lab.model.Message;
import ch.heigvd.smtp_lab.model.Person;
import ch.heigvd.smtp_lab.model.Prank;
import ch.heigvd.smtp_lab.smtp.SmtpClient;

public class PrankManager {
	private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
	
	private static List<Group> createGroups() throws Exception {
		int nGroups = ConfigurationManager.getInstance().getNumberOfGroups();
		List<Person> victims = ConfigurationManager.getInstance().getVictims();
		
		if (victims.size() < nGroups * 3) {
			throw new Exception("There must be at least " + nGroups * 3 + " victims to form " + nGroups + " groups.");
		}
		
		int nVictims = victims.size();
		
		List<Group> groups = new LinkedList<Group>();
		
		for (int i = 0; i < nGroups; i++) {
			int nbMembers = i < nVictims % nGroups ? nVictims / nGroups + 1 : nVictims / nGroups;

			Group group = new Group();
			for (int j = 0; j < nbMembers; j++) {
				group.addMember(victims.remove(0));
			}
			groups.add(group);
		}
		
		return groups;
	}

	public static void main(String[] args) {
		try {
			Random random = new Random();
			
			// Read configuration
			ConfigurationManager.getInstance().readConfiguration("config/config.properties");
			ConfigurationManager.getInstance().readVictims("config/victims.utf8");
			ConfigurationManager.getInstance().readMessages("config/messages.utf8");
			
			SmtpClient client = new SmtpClient(ConfigurationManager.getInstance().getHostAddress(), ConfigurationManager.getInstance().getHostPort());
			List<Message> messages = ConfigurationManager.getInstance().getMessages();
			List<Group >groups = createGroups();
			
			for (Group g : groups) {
				Prank p = new Prank();
				p.setSender(g.getMembers().remove(0));
				p.setVictims(g.getMembers());
				Message m = messages.get(random.nextInt(messages.size()));
				p.setSubject(m.getSubject());
				p.setMessage(m.getContent());
				p.send(client);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage());
		}
	}
}

package ch.heigvd.smtp_lab.model;

import java.io.IOException;
import java.util.List;

import ch.heigvd.smtp_lab.smtp.SmtpClient;

public class Prank {
	private Person sender;
	private List<Person> victims;
	private String subject;
	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	public void setSender(Person sender) {
		this.sender = sender;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setVictims(List<Person> victims) {
		this.victims = victims;
	}
	public void send(SmtpClient client) throws IOException {
		Mail mail = new Mail(sender, victims, subject, message);
		client.sendMail(mail);
	}
}

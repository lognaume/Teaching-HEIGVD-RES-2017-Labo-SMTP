package ch.heigvd.smtp_lab.model;

import java.util.List;

public class Mail {
	private Person sender;
	private List<Person> receivers;
	private String subject;
	private String content;

	public Mail(Person sender, List<Person> receivers, String subject, String content) {
		this.sender = sender;
		this.receivers = receivers;
		this.subject = subject;
		this.content = content;
	}

	public Person getSender() {
		return sender;
	}

	public List<Person> getReceivers() {
		return receivers;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}

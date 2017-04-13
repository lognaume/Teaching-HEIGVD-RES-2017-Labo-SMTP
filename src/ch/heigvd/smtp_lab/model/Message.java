package ch.heigvd.smtp_lab.model;

public class Message {
	private String subject;
	private String content;
	
	public Message(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
	
	public Message() {
	}

	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
}

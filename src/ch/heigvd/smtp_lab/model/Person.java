package ch.heigvd.smtp_lab.model;

public class Person {
	private String email;
	
	public Person(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}

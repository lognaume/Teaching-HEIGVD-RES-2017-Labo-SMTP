package ch.heigvd.smtp_lab.model;

import java.util.LinkedList;
import java.util.List;

public class Group {
	private List<Person> members;
	
	public Group() {
		members = new LinkedList<Person>();
	}
	
	public Group(List<Person> members) {
		this.members = members;
	}
	
	public void addMember(Person person) {
		members.add(person);
	}
	
	public List<Person> getMembers() {
		return members;
	}
}

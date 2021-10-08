package ca.mcgill.ecse321.coop.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class CoopSystem {
	private Set<EventNotification> eventNotifications;

	@OneToMany(mappedBy = "coopSystem", cascade = { CascadeType.ALL })
	public Set<EventNotification> getEventNotifications() {
		return this.eventNotifications;
	}

	public void setEventNotifications(Set<EventNotification> eventNotificationss) {
		this.eventNotifications = eventNotificationss;
	}

	private Set<CoopJob> coopJobs;

	@OneToMany(mappedBy = "coopSystem", cascade = { CascadeType.ALL })
	public Set<CoopJob> getCoopJobs() {
		return this.coopJobs;
	}

	public void setCoopJobs(Set<CoopJob> coopJobss) {
		this.coopJobs = coopJobss;
	}

	private Set<Message> messages;

	@OneToMany(mappedBy = "coopSystem", cascade = { CascadeType.ALL })
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messagess) {
		this.messages = messagess;
	}

	private Set<CoopUser> coopUsers;

	@OneToMany(mappedBy = "coopSystem", cascade = { CascadeType.ALL })
	public Set<CoopUser> getCoopUsers() {
		return this.coopUsers;
	}

	public void setCoopUsers(Set<CoopUser> coopUserss) {
		this.coopUsers = coopUserss;
	}

	private Set<Document> documents;

	@OneToMany(mappedBy = "coopSystem", cascade = { CascadeType.ALL })
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documentss) {
		this.documents = documentss;
	}

	private String id;

	public void setId(String value) {
		this.id = value;
	}

	@Id
	public String getId() {
		return this.id;
	}

}

package ca.mcgill.ecse321.coop.model;

import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class CoopUser {
	private CoopSystem coopSystem;

	@ManyToOne(optional = false)
	public CoopSystem getCoopSystem() {
		return this.coopSystem;
	}

	public void setCoopSystem(CoopSystem coopSystem) {
		this.coopSystem = coopSystem;
	}

	private Set<Document> authoredDocuments;

	@OneToMany(mappedBy = "author")
	public Set<Document> getAuthoredDocuments() {
		return this.authoredDocuments;
	}

	public void setAuthoredDocuments(Set<Document> documents) {
		this.authoredDocuments = documents;
	}

	private String password;

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}
	
	private String email;

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	private String username;

	public void setUsername(String value) {
		this.username = value;
	}

	@Id
	public String getUsername() {
		return this.username;
	}

	private Set<Message> sentMessages;

	@OneToMany(mappedBy = "sender")
	public Set<Message> getSentMessages() {
		return this.sentMessages;
	}

	public void setSentMessages(Set<Message> sentMessagess) {
		this.sentMessages = sentMessagess;
	}

	private Set<Message> receivedMessages;

	@OneToMany(mappedBy = "receiver")
	public Set<Message> getReceivedMessages() {
		return this.receivedMessages;
	}

	public void setReceivedMessages(Set<Message> receivedMessagess) {
		this.receivedMessages = receivedMessagess;
	}

}

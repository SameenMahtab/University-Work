package ca.mcgill.ecse321.coop.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Date;

@Entity
public class Message {
	private CoopSystem coopSystem;

	@ManyToOne(optional = false)
	public CoopSystem getCoopSystem() {
		return this.coopSystem;
	}

	public void setCoopSystem(CoopSystem coopSystem) {
		this.coopSystem = coopSystem;
	}

	private String content;

	public void setContent(String value) {
		this.content = value;
	}

	public String getContent() {
		return this.content;
	}

	private CoopUser sender;

	@ManyToOne(optional = false)
	public CoopUser getSender() {
		return this.sender;
	}

	public void setSender(CoopUser sender) {
		this.sender = sender;
	}

	private CoopUser receiver;

	@ManyToOne(optional = false)
	public CoopUser getReceiver() {
		return this.receiver;
	}

	public void setReceiver(CoopUser reciever) {
		this.receiver = reciever;
	}

	private Set<Document> attachements;

	@OneToMany
	public Set<Document> getAttachements() {
		return this.attachements;
	}

	public void setAttachements(Set<Document> attachementss) {
		this.attachements = attachementss;
	}

	private String messageId;

	public void setMessageId(String value) {
		this.messageId = value;
	}

	@Id
	public String getMessageId() {
		return this.messageId;
	}
	
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}

	private Time time;

	public void setTime(Time value) {
		this.time = value;
	}

	public Time getTime() {
		return this.time;
	}
}

package ca.mcgill.ecse321.coop.model;

import javax.persistence.ManyToOne;

import javax.persistence.ManyToMany;
import java.util.Set;

import java.sql.Time;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Document {

	private DocumentType type;

	public void setType(DocumentType value) {
		this.type = value;
	}

	public DocumentType getType() {
		return this.type;
	}

	private CoopSystem coopSystem;

	@ManyToOne(optional = false)
	public CoopSystem getCoopSystem() {
		return this.coopSystem;
	}

	public void setCoopSystem(CoopSystem coopSystem) {
		this.coopSystem = coopSystem;
	}

	private Date submissionDate;

	public void setSubmissionDate(Date value) {
		this.submissionDate = value;
	}

	public Date getSubmissionDate() {
		return this.submissionDate;
	}

	private Time submissionTime;

	public void setSubmissionTime(Time value) {
		this.submissionTime = value;
	}

	public Time getSubmissionTime() {
		return this.submissionTime;
	}

	private CoopUser author;

	@ManyToOne(optional = false)
	public CoopUser getAuthor() {
		return this.author;
	}

	public void setAuthor(CoopUser author) {
		this.author = author;
	}

	private int size;

	public void setSize(int value) {
		this.size = value;
	}

	public int getSize() {
		return this.size;
	}

	private String documentId;

	public void setDocumentId(String value) {
		this.documentId = value;
	}

	@Id
	public String getDocumentId() {
		return this.documentId;
	}
}

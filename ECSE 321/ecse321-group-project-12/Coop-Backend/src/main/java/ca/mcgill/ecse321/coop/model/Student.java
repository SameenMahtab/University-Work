package ca.mcgill.ecse321.coop.model;

import ca.mcgill.ecse321.coop.model.CoopUser;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
public class Student extends CoopUser {
	private Set<Document> personalDocuments;

	@OneToMany
	public Set<Document> getPersonalDocuments() {
		return this.personalDocuments;
	}

	public void setPersonalDocuments(Set<Document> personalDocumentss) {
		this.personalDocuments = personalDocumentss;
	}

	private Set<CoopJob> coopJobs;

	@OneToMany(mappedBy = "intern")
	public Set<CoopJob> getCoopJobs() {
		return this.coopJobs;
	}

	public void setCoopJobs(Set<CoopJob> coOpJobs) {
		this.coopJobs = coOpJobs;
	}

	private boolean allowCV = false;

	public void setAllowCV(boolean value) {
		this.allowCV = value;
	}

	public boolean isAllowCV() {
		return this.allowCV;
	}

	private boolean allowTranscript = false;

	public void setAllowTranscript(boolean value) {
		this.allowTranscript = value;
	}

	public boolean isAllowTranscript() {
		return this.allowTranscript;
	}
	
	private String mcgillid;

	public void setMcgillid(String value) {
		this.mcgillid = value;
	}

	public String getMcgillid() {
		return this.mcgillid;
	}

}

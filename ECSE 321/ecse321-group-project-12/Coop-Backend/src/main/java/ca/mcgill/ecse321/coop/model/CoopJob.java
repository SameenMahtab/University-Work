package ca.mcgill.ecse321.coop.model;

import javax.persistence.ManyToMany;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class CoopJob {
	private Set<Document> coopJobDocuments;

	@OneToMany
	public Set<Document> getCoopJobDocuments() {
		return this.coopJobDocuments;
	}

	public void setCoopJobDocuments(Set<Document> documents) {
		this.coopJobDocuments = documents;
	}

	private CoopSystem coopSystem;

	@ManyToOne(optional = false)
	public CoopSystem getCoopSystem() {
		return this.coopSystem;
	}

	public void setCoopSystem(CoopSystem coopSystem) {
		this.coopSystem = coopSystem;
	}

	private Student intern;

	@ManyToOne(optional = false)
	public Student getIntern() {
		return this.intern;
	}

	public void setIntern(Student student) {
		this.intern = student;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	private Date startDate;

	public void setStartDate(Date value) {
		this.startDate = value;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	private Date endDate;

	public void setEndDate(Date value) {
		this.endDate = value;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	private Employer employer;

	@ManyToOne(optional = false)
	public Employer getEmployer() {
		return this.employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	private CoopState state;

	public void setState(CoopState value) {
		this.state = value;
	}

	public CoopState getState() {
		return this.state;
	}

	private String jobId;

	public void setJobId(String value) {
		this.jobId = value;
	}

	@Id
	public String getJobId() {
		return this.jobId;
	}
	
	private String description;

	public void setDescription(String value) {
		this.description = value;
	}

	public String getDescription() {
		return this.description;
	}

}

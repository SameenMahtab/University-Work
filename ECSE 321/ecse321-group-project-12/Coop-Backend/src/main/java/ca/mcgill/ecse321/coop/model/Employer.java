package ca.mcgill.ecse321.coop.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.OneToMany;

@Entity
public class Employer extends CoopUser {
	private Set<EventNotification> eventNotifications;

	@OneToMany
	public Set<EventNotification> getEventNotifications() {
		return this.eventNotifications;
	}

	public void setEventNotifications(Set<EventNotification> eventNotificationss) {
		this.eventNotifications = eventNotificationss;
	}

	private Set<Student> archivedInterns;

	@OneToMany
	public Set<Student> getArchivedInterns() {
		return this.archivedInterns;
	}

	public void setArchivedInterns(Set<Student> archivedInterns) {
		this.archivedInterns = archivedInterns;
	}

	private Set<CoopJob> coopJobs;

	@OneToMany(mappedBy = "employer")
	public Set<CoopJob> getCoopJobs() {
		return this.coopJobs;
	}

	public void setCoopJobs(Set<CoopJob> coOpJobss) {
		this.coopJobs = coOpJobss;
	}

}

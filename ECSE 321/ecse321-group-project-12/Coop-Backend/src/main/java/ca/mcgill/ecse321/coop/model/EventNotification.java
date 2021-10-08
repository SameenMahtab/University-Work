package ca.mcgill.ecse321.coop.model;

import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Id;
import ca.mcgill.ecse321.coop.model.Event;
import java.sql.Date;
import java.sql.Time;

@Entity
public class EventNotification {
	private CoopSystem coopSystem;

	@ManyToOne(optional = false)
	public CoopSystem getCoopSystem() {
		return this.coopSystem;
	}

	public void setCoopSystem(CoopSystem coopSystem) {
		this.coopSystem = coopSystem;
	}

	private String name;

	public void setName(String value) {
		this.name = value;
	}

	@Id
	public String getName() {
		return this.name;
	}

	private Event type;

	public void setType(Event value) {
		this.type = value;
	}

	public Event getType() {
		return this.type;
	}

	private String location;

	public void setLocation(String value) {
		this.location = value;
	}

	public String getLocation() {
		return this.location;
	}

	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}

	public Date getDate() {
		return this.date;
	}

	private Time startTime;

	public void setStartTime(Time value) {
		this.startTime = value;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	private Time endTime;

	public void setEndTime(Time value) {
		this.endTime = value;
	}

	public Time getEndTime() {
		return this.endTime;
	}
}

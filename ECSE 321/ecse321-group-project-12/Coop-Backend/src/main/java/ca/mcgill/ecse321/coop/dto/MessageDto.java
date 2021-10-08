package ca.mcgill.ecse321.coop.dto;

import java.util.Set;
import java.sql.Time;
import java.sql.Date;

public class MessageDto {
	

	public String coopSystemName;

	public String content;

	public String senderName;

	public String receiverName;

	
	public Set<String> attachementsIds;


	public String messageId;

	
	public Date date;

	public Time time;

	
}

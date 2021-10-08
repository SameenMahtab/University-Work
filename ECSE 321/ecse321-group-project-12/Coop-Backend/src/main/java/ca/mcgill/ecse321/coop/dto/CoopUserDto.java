package ca.mcgill.ecse321.coop.dto;


import java.util.Set;

public abstract class CoopUserDto {
	
	
	public String coopSystemName;

	
	public Set<String> authoredDocumentsIds;

	public String email;

	public String password;

	

	public String username;
	
	
	public Set<String> sentMessagesIds;

	
	public Set<String> receivedMessagesIds;

	

}

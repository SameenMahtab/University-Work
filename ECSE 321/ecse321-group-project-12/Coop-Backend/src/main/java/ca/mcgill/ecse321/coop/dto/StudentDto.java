package ca.mcgill.ecse321.coop.dto;


import java.util.Set;



public class StudentDto extends CoopUserDto {
	
	public Set<String> personalDocumentsIds;

	
	public Set<String> coopJobsIds;

	public boolean allowCV = false;

	public boolean allowTranscript = false;
	public String mcgillid;

}

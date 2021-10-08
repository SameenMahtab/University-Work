package ca.mcgill.ecse321.coop.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.*;
import org.junit.jupiter.api.Test;

public class interfaceTest {
	
	
	@Test 
	void testCreateSystem() {
		testCreate("https://ecse321-group12.herokuapp.com/coopsystem"); //Create a coopsystem
	}
	
	@Test
	void testCreateStudent() {
		testCreate("https://ecse321-group12.herokuapp.com/students/Mahtab"); //Create a student
		testDelete("https://ecse321-group12.herokuapp.com/students/Mahtab"); //Delete that student
	}
	
	
	@Test
	void testCreateEmployer() {
		testCreate("https://ecse321-group12.herokuapp.com/employers/Brown"); //Create an employer
		testDelete("https://ecse321-group12.herokuapp.com/employers/Brown"); //Delete that employer
	}	
	

	
	@Test
	void testSetPassword() {
		testCreate("https://ecse321-group12.herokuapp.com/students/Richard"); //Create a student
		testCreate("https://ecse321-group12.herokuapp.com/setPassword?"
				+ "Username=Richard&Password=1234"); //Create password for that student
		testDelete("https://ecse321-group12.herokuapp.com/students/Richard"); //Delete that student
	}	
	@Test
	void testCreateDocument() {
		testCreate("https://ecse321-group12.herokuapp.com/coopsystem"); //Create a CoopSystem
		testCreate("https://ecse321-group12.herokuapp.com/students/Cat"); //Create a student
		testCreate("https://ecse321-group12.herokuapp.com/createDocument?"
				+ "DocumentId=moon&UserName=Cat&Type=CV"); //Create a document for that student
		testDelete("https://ecse321-group12.herokuapp.com/students/Cat"); //Delete the student
	}	
	
	@Test
	void testDeleteDocument() {
		testDelete("https://ecse321-group12.herokuapp.com/deleteDocument?"
				+ "DocumentId=moon"); //Delete the document created in the previous test
	}
	@Test
	void testSetPreferencesStudentPreferences() {
		testCreate("https://ecse321-group12.herokuapp.com/students/John1"); //Create a student
		testCreate("https://ecse321-group12.herokuapp.com/studentPreferences?"
				+ "StudentName=John1&AllowCV=false&AllowTranscript=true"); //set the preferences for that student
        testDelete("https://ecse321-group12.herokuapp.com/students/John1");//Delete that student
	}
	@Test
	void testSetPreferencesStudentPersonal() {
		testCreate("https://ecse321-group12.herokuapp.com/students/Johnny1");// Create a student
		testCreate("https://ecse321-group12.herokuapp.com/studentPersonal?"
				+ "StudentName=Johnny1&PersonalDocumentsIds=moon"); //Set personal preferences for that student
        testDelete("https://ecse321-group12.herokuapp.com/students/Johnny1");//Delete that student
	}	
	
	@Test
	void testGetCoopuser() {
		testCreate("https://ecse321-group12.herokuapp.com/employers/blue"); //Create a coopuser
		testGet("https://ecse321-group12.herokuapp.com/coopusers/blue");  //Get that coopuser
		
	}
	
	
	@Test
	void testCreateAndSendMessage() {
		testCreate("https://ecse321-group12.herokuapp.com/students/John2");  //Create sender
		testCreate("https://ecse321-group12.herokuapp.com/employers/Johnny2"); //Create receiver		
		testCreate("https://ecse321-group12.herokuapp.com/createDocument?UserName=Blah"
				+ "&DocumentId=sun&Type=CV"); //Create a document
    	testCreate("https://ecse321-group12.herokuapp.com/newMessage?MessageId=Sam1&"
    			+ "SenderName=John2&ReceiverName=Johnny2&Content=Yolo&ListofAttachementsIds=[moon]");//Send a message
	}
	
	@Test
	void testGetMessage() {
		testGet("https://ecse321-group12.herokuapp.com/Message?MessageId=Hello"); //Get a message
		
		}
	
	@Test
	void testDeleteMessage() {
		testCreate("https://ecse321-group12.herokuapp.com/newMessage?MessageId=Hellow&SenderName=Cat"
				+ "&ReceiverName=Bloch&Content=Yolo&ListofAttachementsIds=[moon]"); //Create a message
		testDelete("https://ecse321-group12.herokuapp.com/Messafe?MessageId=Hellow");//Delete that message
	}
	
	@Test
	void testSentMessages() {
		testGet("https://ecse321-group12.herokuapp.com/SentMessages?SenderName=John2"); //get list of sent messages
	}
	
	
	@Test
	void testDeleteSystem() {	
		testDelete("https://ecse321-group12.herokuapp.com/coopsystem");  //Clears the whole database 
	}
	@Test
	void testCreateEventNotification() {

		testCreate("https://ecse321-group12.herokuapp.com/createEventNotification?"
				+ "EventNotificationId=HelloWorld");//Create an event notification
	
	}	
	
	
		
	
	@Test
	void testSetEventNotificationSettings() {
		testCreate("https://ecse321-group12.herokuapp.com/coopsystem");	//create a coopsystem
		testCreate("https://ecse321-group12.herokuapp.com/createEventNotification?"
				+ "EventNotificationId=HelloWorld");                    // Create notification  
		testCreate("https://ecse321-group12.herokuapp.com/setEventSettings?EventNotificationId=HelloWorld&"
				+ "Type=conferece&Location="
				+ "Amsterdam&Date=2019-02-21&StartTime=13:10:00&EndTime=18:30:00"); //Set notification settings
	 
	}
	
	@Test
	void testGetEventNotification() {
		
		testGet("https://ecse321-group12.herokuapp.com/getEventNotifications"); //get the notification that was
		                                                                        //created in the previous test
		testGet("https://ecse321-group12.herokuapp.com/getEventNotifications?EventNotificationId=HelloWorld");
		
	}
	
	@Test
	void testDeleteNotification() {
		
		testDelete("https://ecse321-group12.herokuapp.com/Event?EentId=HelloWorld");//Delete the notification 
		                                                                            //that was created in the previous test
	}
	
	@Test
	void testCreateJob() {
		testCreate("https://ecse321-group12.herokuapp.com/students/Dog"); //Create a new student
		testCreate("https://ecse321-group12.herokuapp.com/employers/SamSam");//Create a new employer
		testCreate("https://ecse321-group12.herokuapp.com/newJob?JobId=manager"//Create a job
				+ "&EmployerName=SamSam&StudentName=Dog");
		
	}
	
	
	//send URL of query
	void queryService(String s) {	
    			
		testGet(s);
	}


	void testDelete(String url) {
		
    try {		
	   		URL targetUrl = new URL(url);			 
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();          
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("DELETE");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + httpConnection.getResponseCode());
            }

            httpConnection.disconnect();            
          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();            
          }		
	}

	void testCreate(String url) {
		
		try {		
			
	   		URL targetUrl = new URL(url);			 
            HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();          
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
 
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + httpConnection.getResponseCode());
            }
            
            InputStreamReader input = new InputStreamReader((httpConnection.getInputStream()));	 
            BufferedReader responseBuffer = new BufferedReader(input);
 
            if(url.contentEquals("https://ecse321-group12.herokuapp.com/coopsystem")) {
            	
            }else {
            	String output;
            System.out.println("Output from Server:\n");
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            }
            input.close();
            httpConnection.disconnect();            
          } catch (MalformedURLException e) { 
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();         
          }		
	}
	

	void testGet(String url) {
		try {
			 
            URL restServiceURL = new URL(url);
 
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
 
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }
 
            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                (httpConnection.getInputStream())));
 
            String output;
            System.out.println("Output from Server:  \n");
 
            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }
            httpConnection.disconnect();
          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }      
	}

}

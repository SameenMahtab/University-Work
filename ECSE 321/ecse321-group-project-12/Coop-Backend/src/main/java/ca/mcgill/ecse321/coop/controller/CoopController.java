package ca.mcgill.ecse321.coop.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.coop.service.*;
import ca.mcgill.ecse321.coop.dao.*;
import ca.mcgill.ecse321.coop.model.*;
import ca.mcgill.ecse321.coop.dto.*;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

@CrossOrigin(origins = "*")
@RestController
public class CoopController {

	@Autowired
	private CoopSystemService service;

	@RequestMapping("/")
	public CoopSystemDto greeting() { // create the coopsystem
		return convertToDto(service.createCoopSystem());
	}

	/*
	 * POST METHODS
	 * 
	 */

	/**
	 * Create a coopsystem
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/coopsystem", "/coopsystem/" })
	public CoopSystemDto createCoopSystem() throws IllegalArgumentException {
		CoopSystem sys = service.createCoopSystem();
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return convertToDto(sys);
	}

	/**
	 * Create a new student
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/students/{userName}", "/students/{userName}/" })
	public StudentDto createStudent(@PathVariable("userName") String userName) throws IllegalArgumentException {
		Student s = service.createStudent(userName);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtob(s);
	}

	/**
	 * Create a new student
	 * 
	 * @param userName
	 * @param mcgillid
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/studentsf", "/studentsf/" })
	public StudentDto createStudentf(@RequestParam(name = "userName") String userName,
			@RequestParam(name = "mcgillid") String mcgillid, @RequestParam(name = "email") String email)
			throws IllegalArgumentException {
		Student s = service.createStudentf(userName, mcgillid, email);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtob(s);
	}

	/**
	 * Create a new employer
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/employers/{userName}", "/employers/{userName}/" })
	public EmployerDto createEmployer(@PathVariable("userName") String userName) throws IllegalArgumentException {
		Employer e = service.createEmployer(userName);
		if (e == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtoc(e);
	}

	/**
	 * Set the password
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/setPassword", "/setPassword/" })
	public CoopUserDto setPassword(@RequestParam(name = "Username") String userName,
			@RequestParam(name = "Password") String password) throws IllegalArgumentException {
		service.setPassword(userName, password);
		CoopUser s = service.findCoopUserByUsername(userName);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtoa(s);
	}

	/**
	 * Set email
	 * 
	 * @param userName
	 * @param email
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/setEmail", "/setEmail/" })
	public CoopUserDto setEmail(@RequestParam(name = "Username") String userName,
			@RequestParam(name = "Email") String email) throws IllegalArgumentException {
		service.setEmail(userName, email);
		CoopUser s = service.findCoopUserByUsername(userName);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtoa(s);
	}

	/**
	 * Set privacy settings
	 * 
	 * @param studentName
	 * @param a
	 * @param b
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/studentPreferences", "/studentPreferences/" })
	public StudentDto setPreferences(@RequestParam(name = "StudentName") String studentName,
			@RequestParam(name = "AllowCV") boolean a, @RequestParam(name = "AllowTranscript") boolean b)
			throws IllegalArgumentException {
		service.setStudentPermissions(studentName, a, b);
		Student s = service.findStudentByUsername(studentName);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtob(s);
	}

	/**
	 * Set the student personal documents
	 * 
	 * @param studentName
	 * @param personalDocumentsIds
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/studentPersonal", "/studentPersonal/" })
	public StudentDto setPreferences(@RequestParam(name = "StudentName") String studentName,
			@RequestParam(name = "PersonalDocumentsIds") ArrayList<String> personalDocumentsIds)
			throws IllegalArgumentException {
		service.setPersonalDocuments(studentName, personalDocumentsIds);
		Student s = service.findStudentByUsername(studentName);
		if (s == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtob(s);
	}

	/**
	 * Create a document
	 * 
	 * @param docId
	 * @param userName
	 * @param type
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/createDocument", "/createDocument/" })
	public void createDocument(@RequestParam(name = "DocumentId") String docId,
			@RequestParam(name = "UserName") String userName, @RequestParam(name = "Type") DocumentType type)
			throws IllegalArgumentException {
		service.createDocument(docId, userName, type);
	}

	/**
	 * Create event notification
	 * 
	 * @param enId
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/createEventNotification", "/createeventNotification/" })
	public void createEventNotification(@RequestParam(name = "EventNotificationId") String enId)
			throws IllegalArgumentException {
		service.createEventNotification(enId);
	}

	/**
	 * Set event settings
	 * 
	 * @param enId
	 * @param type
	 * @param location
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/setEventSettings", "/setEventSettings/" })
	public void setEventNotificationSettings(@RequestParam(name = "EventNotificationId") String enId,
			@RequestParam(name = "Type") Event type, @RequestParam(name = "Location") String location,
			@RequestParam(name = "Date") String date, @RequestParam(name = "StartTime") String startTime,
			@RequestParam(name = "EndTime") String endTime) throws IllegalArgumentException {
		service.setEventNotificationSettings(enId, type, location, Date.valueOf(date), Time.valueOf(startTime),
				Time.valueOf(endTime));
	}

	/**
	 * Create a new coopjob
	 * 
	 * @param jobId
	 * @param employerName
	 * @param studentName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/newJob", "/newJob/" })
	public CoopJobDto createJob(@RequestParam(name = "JobId") String jobId,
			@RequestParam(name = "EmployerName") String employerName,
			@RequestParam(name = "StudentName") String studentName) throws IllegalArgumentException {
		return convertToDto(service.createCoopJob(jobId, employerName, studentName));
	}

	/**
	 * Create a new coopjob
	 * 
	 * @param jobId
	 * @param employerName
	 * @param studentName
	 * @param description
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/newJobf", "/newJobf/" })
	public CoopJobDto createJobf(@RequestParam(name = "JobId") String jobId,
			@RequestParam(name = "EmployerName") String employerName,
			@RequestParam(name = "StudentName") String studentName,
			@RequestParam(name = "description") String description) throws IllegalArgumentException {
		return convertToDto(service.createCoopJobf(jobId, employerName, studentName, description));
	}

	/**
	 * Set the coopjob settings
	 * 
	 * @param jobId
	 * @param startDate
	 * @param endDate
	 * @param name
	 * @param state
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/JobSettings", "/JobSettings/" })
	public void setJobSettings(@RequestParam(name = "JobId") String jobId,
			@RequestParam(name = "StartDate") String startDate, @RequestParam(name = "EndDate") String endDate,
			@RequestParam(name = "JobName") String name, @RequestParam(name = "State") CoopState state)
			throws IllegalArgumentException {
		service.setCoopJobSettings(jobId, Date.valueOf(startDate), Date.valueOf(endDate), name, state);
	}

	/**
	 * Set the coopjob state
	 * 
	 * @param jobId
	 * @param state
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/JobState", "/JobState/" })
	public void setJobState(@RequestParam(name = "JobId") String jobId, @RequestParam(name = "State") CoopState state)
			throws IllegalArgumentException {
		service.setCoopJobState(jobId, state);
	}

	/**
	 * Add document to coop job
	 * 
	 * @param jobId
	 * @param documentId
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/addDocumentToJob", "/addDocumentToJob/" })
	public void addDocumentJob(@RequestParam(name = "JobId") String jobId,
			@RequestParam(name = "DocumentId") String documentId) throws IllegalArgumentException {
		service.addDocumentToCoopJob(jobId, documentId);
	}

	/**
	 * Create a message
	 * 
	 * @param mId
	 * @param sender
	 * @param receiver
	 * @param content
	 * @param attachementsIds
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/newMessage", "/newMessage/" })
	public void createAndSendMessage(@RequestParam(name = "MessageId") String mId,
			@RequestParam(name = "SenderName") String sender, @RequestParam(name = "ReceiverName") String receiver,
			@RequestParam(name = "Content") String content,
			@RequestParam(name = "ListofAttachementsIds") ArrayList<String> attachementsIds)
			throws IllegalArgumentException {
		service.createMessage(mId, sender, receiver, content, attachementsIds);

	}

	/*
	 * GET METHODS
	 * 
	 */

	/**
	 * Login
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/login", "/login/" })
	public boolean login(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password) throws IllegalArgumentException {
		return service.login(username, password);
	}

	/**
	 * retrieve the info from a coopsystem
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/coopsystem", "/coopsystem/" }) //
	public CoopSystemDto getCoopSystem() throws IllegalArgumentException {
		CoopSystem sys = service.getCoopSystem();
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return convertToDto(sys);
	}

	/**
	 * Retieve a student by its username
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/students/{userName}", "/students/{userName}/" })
	public StudentDto getStudent(@PathVariable("userName") String userName) throws IllegalArgumentException {
		Student sys = service.findStudentByUsername(userName);
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtob(sys);
	}

	/**
	 * Retrieve a user by its username
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/coopusers/{userName}", "/coopusers/{userName}/" })
	public CoopUserDto getCoopUser(@PathVariable("userName") String userName) throws IllegalArgumentException {
		CoopUser sys = service.findCoopUserByUsername(userName);
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtoa(sys);
	}

	/**
	 * Retrieve an email of a user
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/email/{userName}", "/email/{userName}/" })
	public String getEmail(@PathVariable("userName") String userName) throws IllegalArgumentException {
		CoopUser sys = service.findCoopUserByUsername(userName);
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return sys.getEmail();
	}

	/**
	 * Retrieve a McGill id of a student
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/mcgillid/{userName}", "/mcgillid/{userName}/" })
	public String getMcgillid(@PathVariable("userName") String userName) throws IllegalArgumentException {
		Student sys = service.findStudentByUsername(userName);
		if (sys == null) {
			throw new IllegalArgumentException();
		}
		return sys.getMcgillid();
	}

	/**
	 * Retrieve all students
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/students", "/students/" })
	public ArrayList<StudentDto> getStudents() throws IllegalArgumentException {
		ArrayList<StudentDto> sys = new ArrayList<StudentDto>();
		for (Student s : service.getAllStudents()) {
			sys.add(convertToDtob(s));
		}
		return sys;
	}

	/**
	 * Retrieve allcoopusers
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/coopusers", "/coopusers/" })
	public ArrayList<CoopUserDto> getCoopUsers() throws IllegalArgumentException {
		ArrayList<CoopUserDto> sys = new ArrayList<CoopUserDto>();
		for (CoopUser s : service.getAllCoopUsers()) {
			sys.add(convertToDtoa(s));
		}
		return sys;
	}

	/**
	 * Retrieve an employer by its username
	 * 
	 * @param userName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/employers/{userName}", "/employers/{userName}/" }) //
	public EmployerDto getEmployer(@PathVariable("userName") String userName) throws IllegalArgumentException {
		Employer e = service.findEmployerByUsername(userName);
		if (e == null) {
			throw new IllegalArgumentException();
		}
		return convertToDtoc(e);
	}

	/**
	 * retrieve all employers
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/employers", "/employers/" })
	public ArrayList<EmployerDto> getEmloyers() throws IllegalArgumentException {
		ArrayList<EmployerDto> sys = new ArrayList<EmployerDto>();
		for (Employer s : service.getAllEmployers()) {
			sys.add(convertToDtoc(s));
		}
		return sys;
	}

	/**
	 * Get a document by its id
	 * 
	 * @param docId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/getDocument", "/getDocument/" })
	public DocumentDto getDocument(@RequestParam(name = "DocumentId") String docId) throws IllegalArgumentException {
		return convertToDto(service.findDocumentByDocumentId(docId));

	}

	/**
	 * Get a lost of documents by the author
	 * 
	 * @param authorId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/getDocuments", "/getDocuments/" })
	public ArrayList<DocumentDto> getDocuments(@RequestParam(name = "AuthorId") String authorId)
			throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.findDocumentsByAuthor(authorId)) {
			list.add(convertToDto(d));
		}
		return list;

	}

	/**
	 * Get all the event notifications
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/getEventNotifications", "/getEventNotifications/" })
	public ArrayList<EventNotificationDto> getEventNotifcations() throws IllegalArgumentException {
		ArrayList<EventNotificationDto> list = new ArrayList<EventNotificationDto>();
		for (EventNotification d : service.findAllEventNotifications()) {
			list.add(convertToDto(d));
		}
		return list;

	}

	/**
	 * Get an event notification by id
	 * 
	 * @param eventNotificationId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/getEventNotification", "/getEventNotification/" })
	public EventNotificationDto getEventNotification(
			@RequestParam(name = "EventNotificationId") String eventNotificationId) throws IllegalArgumentException {
		return convertToDto(service.findEventNotificationByName(eventNotificationId));

	}

	/**
	 * Get an event notification
	 * @param x
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/getEventNotificationsinXdays", "/getEventNotificationsinXdays/" })
	public ArrayList<EventNotificationDto> getEventNotificationinXdays(@RequestParam(name = "NumberOfDays") int x)
			throws IllegalArgumentException {
		ArrayList<EventNotificationDto> list = new ArrayList<EventNotificationDto>();
		for (EventNotification d : service.getEventsInXDays(x)) {
			list.add(convertToDto(d));
		}
		return list;
	}

	/**
	 * Get messages by sender
	 * @param sender
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/SentMessages", "/SentMessages/" }) 
	public ArrayList<MessageDto> getSentMessages(@RequestParam(name = "SenderName") String sender)
			throws IllegalArgumentException {
		ArrayList<MessageDto> list = new ArrayList<MessageDto>();
		for (Message m : service.findMessagesBySender(sender)) {
			list.add(convertToDto(m));
		}
		return list;
	}

	/**
	 * Get messages by receiver
	 * @param receiver
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/ReceivedMessages", "/ReceivedMessages/" }) 
	public ArrayList<MessageDto> getReceivedMessages(@RequestParam(name = "ReceiverName") String receiver)
			throws IllegalArgumentException {
		ArrayList<MessageDto> list = new ArrayList<MessageDto>();
		for (Message m : service.findMessagesByReceiver(receiver)) {
			list.add(convertToDto(m));
		}
		return list;
	}

	/**
	 * Get messages by sender and receiver
	 * @param sender
	 * @param receiver
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/OneWayMessages", "/OneWayMessages/" })  
	public ArrayList<MessageDto> getOneWay(@RequestParam(name = "SenderName") String sender,
			@RequestParam(name = "ReceiverName") String receiver) throws IllegalArgumentException {
		ArrayList<MessageDto> list = new ArrayList<MessageDto>();
		for (Message m : service.findMessagesBySenderAndReceiver(sender, receiver)) {
			list.add(convertToDto(m));
		}
		return list;
	}

	/**
	 * Get list of authored documents
	 * @param username
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/AuthoredDocuments", "/AuthoredDocuments/" }) 
	public ArrayList<DocumentDto> getAuthoredDocuments(@RequestParam(name = "userName") String username)
			throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.getAuthoredDocuments(username)) {
			list.add(convertToDto(d));
		}
		return list;
	}

	/**
	 * Get list of attachments in message
	 * @param messageId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/Attachements", "/Attachements/" })  
	public ArrayList<DocumentDto> getAttachements(@RequestParam(name = "MessageId") String messageId)
			throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.getAttachements(messageId)) {
			list.add(convertToDto(d));
		}
		return list;
	}

	/**
	 * Get list of documents of coopjob
	 * @param jobId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/CoopJobDocuments", "/CoopJobDocuments/" }) 
	public ArrayList<DocumentDto> getCoopJobDocuments(@RequestParam(name = "JobId") String jobId)
			throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.getCoopJobDocuments(jobId)) {
			list.add(convertToDto(d));
		}
		return list;
	}

	/**
	 * Get list of specific-type documents of coopjob
	 * @param jobId
	 * @param type
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/CoopJobDocumentsType", "/CoopJobDocumentsType/" }) 																			
	public ArrayList<DocumentDto> getCoopJobDocumentsType(@RequestParam(name = "JobId") String jobId,
			@RequestParam(name = "DocumentType") DocumentType type) throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.getCoopJobDocuments(jobId)) {
			if (d.getType() == type) {
				list.add(convertToDto(d));
			}
		}
		return list;
	}

	/**
	 * Get list of students who worked at this employer
	 * @param employerName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/ArchivedInterns", "/ArchivedInterns/" }) 
	public ArrayList<StudentDto> getArchivedInterns(@RequestParam(name = "EmloyerName") String employerName)
			throws IllegalArgumentException {
		ArrayList<StudentDto> list = new ArrayList<StudentDto>();
		for (Student t : service.getArchivedInterns(employerName)) {
			list.add(convertToDtob(t));
		}
		return list;
	}

	/**
	 * Get personal documents of a student who worked at this employer
	 * @param employerName
	 * @param studentName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/ArchivedInternDocuments", "/ArchivedInternDocuments/" }) 
	public ArrayList<DocumentDto> getArchivedInternDocuments(@RequestParam(name = "EmloyerName") String employerName,

			@RequestParam(name = "StudentName") String studentName) throws IllegalArgumentException {
		ArrayList<DocumentDto> list = new ArrayList<DocumentDto>();
		for (Document d : service.getInternDocuments(employerName, studentName)) {
			list.add(convertToDto(d));
		}
		return list;
	}

	/**
	 * Get personal documents of all students who worked at this employer
	 * @param employerName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/ArchivedInternsDocuments", "/ArchivedInternsDocuments/" }) 
	public ArrayList<ArrayList<DocumentDto>> getArchivedInternsDocuments(
			@RequestParam(name = "EmloyerName") String employerName) throws IllegalArgumentException {
		ArrayList<ArrayList<DocumentDto>> list = new ArrayList<ArrayList<DocumentDto>>();
		ArrayList<DocumentDto> dl = new ArrayList<DocumentDto>();
		for (ArrayList<Document> d : service.getInternsDocuments(employerName).values()) {
			if (d != null) {
				dl = new ArrayList<DocumentDto>();
				for (Document doc : d) {
					if (doc != null) {
						dl.add(convertToDto(doc));
					}
				}
				list.add(dl);
			}
		}
		return list;
	}

	/**
	 * Get a coopjob by id
	 * @param jobId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/CoopJob", "/CoopJob/" }) 
	public CoopJobDto getJob(@RequestParam(name = "JobId") String jobId) throws IllegalArgumentException {
		return convertToDto(service.findCoopJobByJobId(jobId));

	}

	/**
	 * Get coopjobs by employer
	 * @param employerName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/JobsByEmployer", "/JobsByEmployer/" }) 
	public ArrayList<CoopJobDto> getCoopJobsByEmployer(@RequestParam(name = "EmployerName") String employerName)
			throws IllegalArgumentException {
		ArrayList<CoopJobDto> list = new ArrayList<CoopJobDto>();
		for (CoopJob j : service.findCoopJobsByEmployer(employerName)) {
			list.add(convertToDto(j));
		}
		return list;
	}

	/**
	 * get coopjobs by student
	 * @param studentName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/JobsByStudent", "/JobsByStudent/" }) 
	public ArrayList<CoopJobDto> getCoopJobsByStudent(@RequestParam(name = "StudentName") String studentName)
			throws IllegalArgumentException {
		ArrayList<CoopJobDto> list = new ArrayList<CoopJobDto>();
		for (CoopJob j : service.findCoopJobsByStudent(studentName)) {
			list.add(convertToDto(j));
		}
		return list;
	}


	/**
	 * Get coop jobs by employer and student
	 * @param employerName
	 * @param studentName
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/JobsByEmployerAndStudent", "/JobsByEmployerAndStudent/" })
	public ArrayList<CoopJobDto> getCoopJobsByEmployerAndStudent(
			@RequestParam(name = "EmployerName") String employerName,
			@RequestParam(name = "StudentName") String studentName) throws IllegalArgumentException {
		ArrayList<CoopJobDto> list = new ArrayList<CoopJobDto>();
		for (CoopJob j : service.findCoopJobsByEmployerAndStudent(employerName, studentName)) {
			list.add(convertToDto(j));
		}
		return list;
	}

	/**
	 * Get a coop job by ID
	 * @param employerName
	 * @param studentName
	 * @param endDate
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/JobByEmployerAndStudentAndEndDate", "/JobByEmployerAndStudentAndEndDate/" }) 
	public CoopJobDto getJobByEmployerAndStudentAndEndDate(@RequestParam(name = "EmployerName") String employerName,
			@RequestParam(name = "StudentName") String studentName, @RequestParam(name = "EndDate") String endDate)
			throws IllegalArgumentException {
		return convertToDto(
				service.findCoopJobByEmployerAndStudentAndEndDate(employerName, studentName, Date.valueOf(endDate)));

	}

	/**
	 * Get a message by id
	 * @param mId
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping(value = { "/Message", "/Message/" }) 
	public MessageDto getMessage(@RequestParam(name = "MessageId") String mId) throws IllegalArgumentException {
		return convertToDto(service.findMessageByMessageId(mId));

	}

	/*
	 * DELETE METHODS
	 * 
	 */

	/**
	 * delete the system (clear the database)
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/coopsystem", "/coopsystem/" }) 
	public void deleteSystem() throws IllegalArgumentException {
		service.deleteSystem();
	}

	/**
	 * Delete a student
	 * @param username
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/students/{username}", "/students/{username}/" })  
	public void deleteStudent(@PathVariable("username") String username) throws IllegalArgumentException {
		service.deleteStudent(username);
	}

	/**
	 * Delete an employer
	 * @param username
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/employers/{username}", "/employers/{username}/" }) 
	public void deleteEmployer(@PathVariable("username") String username) throws IllegalArgumentException {
		service.deleteEmployer(username);
	}

	/**
	 * Delete a user
	 * @param username
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/coopusers/{username}", "/coopusers/{username}/" }) 
	public void deleteCoopUser(@PathVariable("username") String username) throws IllegalArgumentException {
		service.deleteCoopUser(username);
	}

	/**
	 * Delete a document
	 * @param docId
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/deleteDocument", "/deleteDocument/" }) 
	public void deleteDocument(@RequestParam(name = "DocumentId") String docId) throws IllegalArgumentException {
		service.deleteDocument(docId);

	}

	/**
	 * Delete an event notification from the database
	 * @param mId
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/Event", "/Event/" }) 
	public void deleteEventNotification(@RequestParam(name = "EentId") String mId) throws IllegalArgumentException {
		service.deleteEventNotification(mId);

	}

	/**
	 * Delete a message by id
	 * @param mId
	 * @throws IllegalArgumentException
	 */
	@DeleteMapping(value = { "/Message", "/Message/" }) 
	public void deleteMessage(@RequestParam(name = "MessageId") String mId) throws IllegalArgumentException {
		// @formatter:on
		service.deleteMessage(mId);

	}

	/*
	 * CONVERSION METHODS
	 * 
	 */

	private CoopSystemDto convertToDto(CoopSystem sys) { // convert the coopsystem to a dto
		if (sys == null) {
			return null;
		}
		CoopSystemDto sysD = new CoopSystemDto();
		sysD.id = sys.getId();

		sysD.eventNotificationsNames = new HashSet<String>();
		for (EventNotification en : service.findAllEventNotifications()) { // create an arraylist containing the ids
			sysD.eventNotificationsNames.add(en.getName());
		}

		sysD.coopJobsIds = new HashSet<String>();
		for (CoopJob j : service.getAllCoopJobs()) {
			sysD.coopJobsIds.add(j.getJobId());
		}

		sysD.messagesIds = new HashSet<String>();
		for (Message m : service.getAllMessages()) {
			sysD.messagesIds.add(m.getMessageId());
		}

		sysD.coopUsersNames = new HashSet<String>();
		for (CoopUser u : service.getAllCoopUsers()) {
			sysD.coopUsersNames.add(u.getUsername());
		}

		sysD.documentsIds = new HashSet<String>();
		for (Document d : service.getAllDocuments()) {
			sysD.documentsIds.add(d.getDocumentId());
		}
		return sysD;
	}

	private EventNotificationDto convertToDto(EventNotification e) { // convert to a dto
		if (e == null) {
			return null;
		}
		EventNotificationDto eD = new EventNotificationDto();
		eD.coopSystemName = e.getCoopSystem().getId();
		eD.coopSystemName = e.getName();
		eD.type = e.getType().toString();
		eD.location = e.getLocation();
		eD.date = (Date) e.getDate().clone();
		eD.startTime = (Time) e.getStartTime().clone();
		eD.endTime = (Time) e.getEndTime().clone();
		return eD;
	}

	private CoopJobDto convertToDto(CoopJob job) { // convert to a dto
		if (job == null) {
			return null;
		}
		CoopJobDto jobD = new CoopJobDto();
		jobD.internName = job.getIntern().getUsername();
		jobD.employerName = job.getEmployer().getUsername();
		jobD.coopSystemName = job.getCoopSystem().getId();
		jobD.name = job.getName();
		jobD.description = job.getDescription();
		jobD.startDate = (Date) job.getStartDate().clone();
		jobD.endDate = (Date) job.getEndDate().clone();
		jobD.jobId = job.getJobId();
		jobD.state = job.getState().toString();
		jobD.coopJobDocumentsIds = new HashSet<String>();
		for (Document d : service.getCoopJobDocuments(job.getJobId())) {
			jobD.coopJobDocumentsIds.add(d.getDocumentId());
		}
		return jobD;
	}

	private CoopUserDto convertToDtoa(CoopUser user) { // convert to a dto
		if (user == null) {
			return null;
		} else if (user instanceof Student) {
			return (CoopUserDto) convertToDtob((Student) user);
		} else {
			return (CoopUserDto) convertToDtoc((Employer) user);
		}

	}

	private DocumentDto convertToDto(Document doc) { // convert to a dto
		if (doc == null) {
			return null;
		}
		DocumentDto docD = new DocumentDto();
		docD.documentId = doc.getDocumentId();
		docD.size = doc.getSize();
		docD.coopSystemName = doc.getCoopSystem().getId();
		docD.authorName = doc.getAuthor().getUsername();
		docD.type = doc.getType().toString();
		docD.submissionDate = (Date) doc.getSubmissionDate().clone();
		docD.submissionTime = (Time) doc.getSubmissionTime().clone();
		return docD;
	}

	private EmployerDto convertToDtoc(Employer u) { // convert to a dto
		if (u == null) {
			return null;
		}
		EmployerDto uD = new EmployerDto();
		uD.coopSystemName = u.getCoopSystem().getId();
		uD.username = u.getUsername();
		uD.password = u.getPassword();
		uD.email = u.getEmail();

		uD.authoredDocumentsIds = new HashSet<String>();
		for (Document doc : service.findDocumentsByAuthor(u.getUsername())) {
			uD.authoredDocumentsIds.add(doc.getDocumentId());
		}

		uD.sentMessagesIds = new HashSet<String>();
		for (Message m : service.findMessagesBySender(u.getUsername())) {
			uD.sentMessagesIds.add(m.getMessageId());
		}

		uD.receivedMessagesIds = new HashSet<String>();
		for (Message m : service.findMessagesByReceiver(u.getUsername())) {
			uD.receivedMessagesIds.add(m.getMessageId());
		}

		uD.coopJobsIds = new HashSet<String>();
		for (CoopJob j : service.findCoopJobsByEmployer(u.getUsername())) {
			uD.coopJobsIds.add(j.getJobId());
		}

		uD.eventNotificationsNames = new HashSet<String>();
		for (EventNotification e : service.findAllEventNotifications()) {
			uD.eventNotificationsNames.add(e.getName());
		}

		uD.archivedInternsNames = new HashSet<String>();
		for (Student s : service.getArchivedInterns(u.getUsername())) {
			uD.archivedInternsNames.add(s.getUsername());
		}
		return uD;
	}

	private MessageDto convertToDto(Message m) { // convert to a dto
		if (m == null) {
			return null;
		}
		MessageDto mD = new MessageDto();
		mD.coopSystemName = m.getCoopSystem().getId();
		mD.content = m.getContent();
		mD.senderName = m.getSender().getUsername();
		mD.receiverName = m.getReceiver().getUsername();
		mD.messageId = m.getMessageId();
		mD.date = (Date) m.getDate().clone();
		mD.time = (Time) m.getTime().clone();

		mD.attachementsIds = new HashSet<String>();
		for (Document doc : service.getAttachements(m.getMessageId())) {
			mD.attachementsIds.add(doc.getDocumentId());
		}

		return mD;
	}

	private StudentDto convertToDtob(Student u) { // convert to a dto
		if (u == null) {
			return null;
		}
		StudentDto uD = new StudentDto();
		uD.coopSystemName = u.getCoopSystem().getId();
		uD.username = u.getUsername();
		uD.password = u.getPassword();
		uD.mcgillid = u.getMcgillid();
		uD.email = u.getEmail();

		uD.authoredDocumentsIds = new HashSet<String>();
		for (Document doc : service.findDocumentsByAuthor(u.getUsername())) {
			uD.authoredDocumentsIds.add(doc.getDocumentId());
		}

		uD.sentMessagesIds = new HashSet<String>();
		for (Message m : service.findMessagesBySender(u.getUsername())) {
			uD.sentMessagesIds.add(m.getMessageId());
		}

		uD.receivedMessagesIds = new HashSet<String>();
		for (Message m : service.findMessagesByReceiver(u.getUsername())) {
			uD.receivedMessagesIds.add(m.getMessageId());
		}

		uD.allowCV = u.isAllowCV();
		uD.allowTranscript = u.isAllowTranscript();

		uD.personalDocumentsIds = new HashSet<String>();
		for (Document doc : service.getPersonalDocumentsByStudent(u.getUsername())) {
			uD.personalDocumentsIds.add(doc.getDocumentId());
		}

		uD.coopJobsIds = new HashSet<String>();
		for (CoopJob j : service.findCoopJobsByStudent(u.getUsername())) {
			uD.coopJobsIds.add(j.getJobId());
		}
		return uD;
	}

}

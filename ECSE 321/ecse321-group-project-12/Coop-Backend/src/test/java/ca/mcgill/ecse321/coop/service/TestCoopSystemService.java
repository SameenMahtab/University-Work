package ca.mcgill.ecse321.coop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import ca.mcgill.ecse321.coop.controller.*;
import ca.mcgill.ecse321.coop.dto.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ca.mcgill.ecse321.coop.dao.*;
import ca.mcgill.ecse321.coop.model.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCoopSystemService {

	@Autowired
	private CoopSystemService service; 

	@Autowired
	private CoopJobRepository coopJobRepository;

	@Autowired
	private CoopSystemRepository coopSystemRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private EventNotificationRepository eventNotificationRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CoopUserRepository coopUserRepository;

	@Mock
	private CoopUserRepository serviceMock;

	@Autowired
	private CoopController controller;

	private CoopJob coopjob;
	private Student student;
	private Employer employer;

	private static final String USERNAME = "ALI";
	private static final String PASSWORD = "12345";

	@Before // setting the mock.
	public void setupMock() {
		coopjob = mock(CoopJob.class);
		student = mock(Student.class);
		employer = mock(Employer.class);

	}

	@After // clearing the database after every test.
	public void clearDatabase() {
		service.clear();
	}

	@Test // testing login
	public void testLogin() {
		service.createCoopSystem();
		String student1 = "May";
		String student2 = "Just";
		String pass = PASSWORD;
		Student a = service.createStudent(student1);
		service.setPassword(student1, PASSWORD);
		assertEquals(true, service.login(student1, PASSWORD));
	}

	@Test // testing to receive coop system
	public void testGetCoopSystem() {
		service.createCoopSystem();
		assertNotNull(service.getCoopSystem());

	}

	@Test // testing deleting coop system
	public void testDeleteCoopSystem() {
		service.createCoopSystem();
		assertNotNull(service.getCoopSystem());
		service.deleteSystem();
		assertNull(service.getCoopSystem());

	}

	@Test // testing creating student
	public void testCreateStudent() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());
	}

	@Test // testing receiving a student after the creation
	public void testGetStudent() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.getStudent(student1).getUsername());

	}

	        // testing to get an employer or student which are coopusers in this case we
			// cover student
	@Test
	public void testGetCoopUser() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.getCoopUser(student1).getUsername());
	}

	@Test // testing to get the list of the students
	public void testGetAllStudents() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(student1, service.getAllStudents().get(0).getUsername());
	}

	@Test // testing to get the list of the coopusers
	public void testGetAllCoopUsers() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(1, service.getAllCoopUsers().size());
		assertEquals(student1, service.getAllCoopUsers().get(0).getUsername());

	}

	@Test // testing of creating an employer
	public void testCreateEmployer() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		assertEquals(employer1, service.findEmployerByUsername(employer1).getUsername());

	}

	@Test // testing to receive the existing employer
	public void testGetEmployer() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		assertEquals(employer1, service.getEmployer(employer1).getUsername());

	}

	@Test // testing to get the list of the employers
	public void testGetAllEmployers() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(employer1, service.getAllEmployers().get(0).getUsername());

	}

	@Test // testing to delete a student
	public void testDeleteStudent() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());
		service.deleteStudent(student1);
		assertNull(service.getStudent(student1));
	}

	@Test // testing of deletion of an employer
	public void testDeleteEmployer() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		assertEquals(employer1, service.findEmployerByUsername(employer1).getUsername());
		service.deleteEmployer(employer1);
		assertNull(service.getEmployer(employer1));
	}

	@Test // testing to set a password to a coopuser
	public void testSetPassword() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());
		service.setPassword(student1, PASSWORD);
		assertEquals(PASSWORD, service.getStudent(student1).getPassword());
	}



	@Test // testing of student permission setup
	public void testSetStudentPermissions() {
		service.createCoopSystem();
		String student1 = "May";

		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());
		service.setStudentPermissions(student1, false, false);
		assertEquals(false, service.getStudent(student1).isAllowCV());
		assertEquals(false, service.getStudent(student1).isAllowTranscript());

	}

	@Test // finding coopusername by his username with this test.
	public void testFindCoopUserByUsername() {
		service.createCoopSystem();
		String student1 = "May";

		service.createStudent(student1);
		assertEquals(student1, service.findCoopUserByUsername(student1).getUsername());
	}

	@Test // finding student by his username with this test.
	public void testFindStudentByUsername() {
		service.createCoopSystem();
		String student1 = "May";

		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());
	}

	@Test // finding employer by his username with this test.
	public void testFindEmployertByUsername() {
		service.createCoopSystem();
		String employer1 = "May";

		service.createEmployer(employer1);
		assertEquals(employer1, service.findEmployerByUsername(employer1).getUsername());
	}

	@Test // deleting coopuser with this test.
	public void testDeleteCoopuser() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		assertEquals(employer1, service.findCoopUserByUsername(employer1).getUsername());
		service.deleteCoopUser(employer1);
		assertNull(service.getCoopUser(employer1));
	}

	@Test // testing to find an event notificaiton by its name
	public void testFindEventNotificationByName() {
		service.createCoopSystem();
		String eventNotif = "Meeting Soon";
		service.createEventNotification(eventNotif);
		assertEquals(1, service.findAllEventNotifications().size());
		assertEquals(eventNotif, service.findEventNotificationByName(eventNotif).getName());

	}

	@Test // test of finding all event notificaitons
	public void testFindAllEventNotifications() {
		service.createCoopSystem();
		String eventNotif = "Meeting Soon";
		service.createEventNotification(eventNotif);
		assertEquals(1, service.findAllEventNotifications().size());
		assertEquals(eventNotif, service.findAllEventNotifications().get(0).getName());

	}

	@Test // test to create a message
	public void testCreateMessage() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessageByMessageId(USERNAME).getMessageId());
	}

	@Test // testing to find a message by its id.
	public void testFindMessageById() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessageByMessageId(USERNAME).getMessageId());
	}

	@Test // testing to find a message by its sender.
	public void testFindMessagesBySender() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessagesBySender(employer1).get(0).getMessageId());
	}

	@Test // testing to find a message by its receiver.
	public void testFindMessagesByReceiver() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessagesByReceiver(employer2).get(0).getMessageId());
	}

	@Test // testing to get the lsit of all the messages.
	public void testGetAllMessages() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.getAllMessages().get(0).getMessageId());
	}

	@Test // testing to find a message by its sender and its receiver.
	public void testFindMessagesBySenderAndReceiver() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);
		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessagesBySenderAndReceiver(employer1, employer2).get(0).getMessageId());
	}

	@Test // deleting a message in this test
	public void testDeleteMessage() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);

		service.createMessage(USERNAME, employer1, employer2, USERNAME, null);
		assertEquals(USERNAME, service.findMessageByMessageId(USERNAME).getMessageId());
		service.deleteMessage(USERNAME);
		assertNull(service.findMessageByMessageId(USERNAME));
	}

	@Test // finding a coopjob by its id in this test.
	public void testFindCoopJobById() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning";

		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());

		service.createEmployer(employerName);
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(2, service.getAllCoopUsers().size());

		service.createCoopJob(jobId, employerName, student1);
		assertEquals(student1, service.getStudent(student1).getUsername());

		assertEquals(1, service.getAllCoopJobs().size());

		assertEquals(employerName, service.getEmployer(employerName).getUsername());
		assertEquals(jobId, service.findCoopJobByJobId(jobId).getJobId());

	}

	@Test // setting coopjob state in this test.
	public void testSetCoopJobState() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning";
		CoopState state1 = CoopState.completed;

		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());

		service.createEmployer(employerName);
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(2, service.getAllCoopUsers().size());

		service.createCoopJob(jobId, employerName, student1);
		assertEquals(student1, service.getStudent(student1).getUsername());

		assertEquals(1, service.getAllCoopJobs().size());

		assertEquals(employerName, service.getEmployer(employerName).getUsername());
		assertEquals(jobId, service.findCoopJobByJobId(jobId).getJobId());

		service.setCoopJobState(jobId, state1);
		assertEquals(state1, service.findCoopJobByJobId(jobId).getState());
	}

	@Test // testing to get all of the documents in this test.
	public void testGetAllDocuments() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;
		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		assertEquals(1, service.getAllDocuments().size());

	}

	@Test // try to get personal documents by its student in this test.
	public void testGetPersonalDocumentsByStudent() {
		service.createCoopSystem();
		String docId = "1";
		String student = "Mike";
		DocumentType type = DocumentType.CV;
		ArrayList<String> documentIds = new ArrayList<>();
		documentIds.add(docId);

		service.createStudent(student);
		service.createDocument(docId, student, type);
		assertEquals(1, service.getAllDocuments().size());

		service.setPersonalDocuments(student, documentIds);
		assertEquals(1, service.getPersonalDocumentsByStudent(student).size());
		assertEquals(docId, service.getPersonalDocumentsByStudent(student).get(0).getDocumentId());
	}

	@Test // testing to get authored documents.
	public void testGetAuthoredDocuments() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;
		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		assertEquals(1, service.getAllDocuments().size());
		assertEquals(authorName, service.getAuthoredDocuments(authorName).get(0).getAuthor().getUsername());

	}

	@Test // test of getting attachments
	public void testGetAttachments() {
		service.createCoopSystem();
		String employer1 = "May";
		service.createEmployer(employer1);
		String employer2 = "May2";
		service.createEmployer(employer2);

		String docId = "1";
		String messageId = "m1";
		DocumentType type = DocumentType.CV;
		service.createEmployer(employer1);
		service.createDocument(docId, employer1, type);
		assertEquals(1, service.getAllDocuments().size());

		ArrayList<String> docs = new ArrayList<String>();
		docs.add(service.getAllDocuments().get(0).getDocumentId());

		service.createMessage(messageId, employer1, employer2, "content", docs);
		assertEquals(docId, service.getAttachements(messageId).get(0).getDocumentId());

	}

	@Test // test of getting coopjob documents.
	public void testGetCoopJobDocuments() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning";
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;
		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);
		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		service.createEmployer(employerName);
		service.createCoopJob(jobId, employerName, student1);
		service.addDocumentToCoopJob(jobId, docId);

		assertEquals(docId, service.getCoopJobDocuments(jobId).get(0).getDocumentId());

	}

	@Test // test to get archived interns.
	public void testGetArchivedInterns() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		assertEquals(student1, service.findStudentByUsername(student1).getUsername());

		String employer1 = "May2";
		service.createEmployer(employer1);
		assertEquals(employer1, service.findEmployerByUsername(employer1).getUsername());

		String jobId = "job1";
		service.createCoopJob(jobId, employer1, student1);
		assertEquals(student1, service.getArchivedInterns(employer1).get(0).getUsername());
	}

	@Test // test of getting intern documents.
	public void testGetInternDocuments() {
		service.createCoopSystem();
		String student1 = "May";
		service.createStudent(student1);
		service.setStudentPermissions(student1, true, true);
		String employer1 = "May2";
		service.createEmployer(employer1);

		String docId = "1";
		DocumentType type = DocumentType.CV;
		service.createDocument(docId, student1, type);
		assertEquals(1, service.getAllDocuments().size());
		ArrayList<String> docs = new ArrayList<String>();
		docs.add(docId);
		service.setPersonalDocuments(student1, docs);
		String jobId = "job1";
		service.createCoopJob(jobId, employer1, student1);

		assertEquals(docId, service.getInternDocuments(employer1, student1).get(0).getDocumentId());
	}

	@Test // test of finding coopjob by its employer, student and date.
	public void testFindCoopJobByEmployerAndStudentAndDate() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning";

		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());

		service.createEmployer(employerName);
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(2, service.getAllCoopUsers().size());

		service.createCoopJob(jobId, employerName, student1);
		assertEquals(student1, service.getStudent(student1).getUsername());

		assertEquals(1, service.getAllCoopJobs().size());

		assertEquals(employerName, service.getEmployer(employerName).getUsername());

		@SuppressWarnings("deprecation")
		Date d1 = new Date(111, 100, 100);
		Date d2 = new Date(111, 102, 100);
		CoopState state1 = CoopState.completed;

		service.setCoopJobSettings(jobId, d1, d2, USERNAME, state1);

		assertEquals(d2.toString(),
				service.findCoopJobByEmployerAndStudentAndEndDate(employerName, student1, d2).getEndDate().toString());
	}

	@Test // sample test.
	public void donothing() {
		service.createCoopSystem();
		service.createStudent("May");
		service.createDocument("dd", "May", DocumentType.CV);
		Document d = service.findDocumentByDocumentId("dd");
		Student s = (Student) d.getAuthor();
		assertEquals("May", s.getUsername());
	}

	@Test // testing of sample creation.
	public void testCreate() {
		service.createCoopSystem();
		assertEquals(0, service.getAllCoopUsers().size());
		EventNotification e = service.createEventNotification("Sam");
		assertEquals(1, service.findAllEventNotifications().size());
		Student s = service.createStudent("May");
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());

		service.setPassword("May", "why");
		s.setPassword("mna");
		assertEquals("why", service.findStudentByUsername("May").getPassword());
		service.deleteStudent("May");
		assertEquals(0, service.getAllStudents().size());
		assertEquals(0, service.getAllCoopUsers().size());
	}

	@Test // another sample creation test.
	public void TestCreate3() {
		service.createCoopSystem();
		String student1 = "May";
		String student2 = "Just";
		Student a = service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());
		service.setPassword(student1, "why");

		assertEquals("why", service.findStudentByUsername(student1).getPassword());

		service.createStudent(student2);
		assertEquals(2, service.getAllStudents().size());
		assertEquals(2, service.getAllCoopUsers().size());
		service.createEmployer("HH");
		service.setPassword("HH", "how");

		assertEquals("how", service.findEmployerByUsername("HH").getPassword());
		assertEquals("how", service.findCoopUserByUsername("HH").getPassword());
		assertEquals(2, service.getAllStudents().size());
		assertEquals(3, service.getAllCoopUsers().size());
		assertEquals(1, service.getAllEmployers().size());
		service.deleteStudent(student2);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(2, service.getAllCoopUsers().size());
		assertEquals(1, service.getAllEmployers().size());
		service.deleteCoopUser("HH");
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());
		assertEquals(0, service.getAllEmployers().size());

	}

	@Test // another sample creation test.
	public void TestCreate2() {
		service.createCoopSystem();
		String student1 = "May";
		String employer1 = "De";
		service.createStudent(student1);
		service.createEmployer(employer1);
		assertEquals(false, service.findStudentByUsername(student1).isAllowCV());
		assertEquals(false, service.findStudentByUsername(student1).isAllowTranscript());
		service.setStudentPermissions(student1, true, true);
		assertEquals(true, service.findStudentByUsername(student1).isAllowCV());
		assertEquals(true, service.findStudentByUsername(student1).isAllowTranscript());

		service.createDocument("doc1", student1, DocumentType.CV);
		assertEquals(1, service.getAllDocuments().size());
		assertEquals("doc1", service.findDocumentByDocumentId("doc1").getDocumentId());
		assertEquals(DocumentType.CV, service.findDocumentByDocumentId("doc1").getType());

		assertEquals(1, service.getAuthoredDocuments(student1).size());

	}

	@Test // testing of creating of coopjob.
	public void testCreateCoopJob() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning";

		service.createStudent(student1);
		assertEquals(1, service.getAllStudents().size());
		assertEquals(1, service.getAllCoopUsers().size());

		service.createEmployer(employerName);
		assertEquals(1, service.getAllEmployers().size());
		assertEquals(2, service.getAllCoopUsers().size());

		service.createCoopJob(jobId, employerName, student1);
		assertEquals(student1, service.getStudent(student1).getUsername());

		assertEquals(1, service.getAllCoopJobs().size());

		assertEquals(employerName, service.getEmployer(employerName).getUsername());
		assertEquals(jobId, service.findCoopJobByJobId(jobId).getJobId());

	}

	@Test // finding coopjobs in this test.
	public void testFindCoopJobs() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning2";

		service.createStudent(student1);
		service.createEmployer(employerName);

		CoopJob coopjob1 = service.createCoopJob(jobId, employerName, student1);
		assertEquals(1, service.getAllCoopJobs().size());
		List<CoopJob> coopJobs = service.getAllCoopJobs();

		// findCoopJobsByEmployer
		assertEquals(jobId, service.findCoopJobsByEmployer(employerName).get(0).getJobId());

		// findCoopJobsByStudent
		assertEquals(jobId, service.findCoopJobsByStudent(student1).get(0).getJobId());

		// findCoopJobsByEmployerAndStudent
		assertEquals(jobId, service.findCoopJobsByEmployerAndStudent(employerName, student1).get(0).getJobId());

	}

	@Test // deleting a coopjob in this test.
	public void testDeleteCoopJob() {
		service.createCoopSystem();
		String student1 = "May";
		String employerName = "Mike";
		String jobId = "Cleaning2";

		service.createStudent(student1);
		service.createEmployer(employerName);

		service.createCoopJob(jobId, employerName, student1);
		assertEquals(1, service.getAllCoopJobs().size());

		service.deleteCoopJob(jobId);
		assertEquals(0, service.getAllCoopJobs().size());

	}

	// setting a coopjob setting in this test.
	@Test
	public void testSetCoopJobSettings() {
		service.createCoopSystem();
		String jobId = "Cleaning2";
		String student1 = "May";
		String employerName = "Mike";
		String name = "holo";
		Date start = new Date(System.currentTimeMillis());
		Date end = new Date(System.currentTimeMillis() + 10000);

		CoopState state1 = CoopState.completed;
		service.createStudent(student1);
		service.createEmployer(employerName);

		CoopJob coopjob1 = service.createCoopJob(jobId, employerName, student1);
		assertEquals(1, service.getAllCoopJobs().size());

		service.setCoopJobSettings(jobId, start, end, name, state1);

		assertEquals(jobId, service.findCoopJobByJobId(jobId).getJobId());

		assertEquals(start.toString(), service.findCoopJobByJobId(jobId).getStartDate().toString());
		assertEquals(end.toString(), service.findCoopJobByJobId(jobId).getEndDate().toString());

		assertEquals(name, service.findCoopJobByJobId(jobId).getName());
		assertEquals(state1, service.findCoopJobByJobId(jobId).getState());

	}

	@Test // testing to add a document to an existing coopjob.
	public void testAddDocumentToCoopJob() {
		service.createCoopSystem();
		String jobId = "Cleaning2";
		String student1 = "May";
		String employerName = "Mike";
		String docId = "1";
		DocumentType type = DocumentType.Other;

		service.createStudent(student1);
		service.createEmployer(employerName);
		service.createCoopJob(jobId, employerName, student1);
		service.createDocument(docId, employerName, type);
		service.addDocumentToCoopJob(jobId, docId);

		assertEquals(1, service.getAllDocuments().size());

		assertEquals(jobId, service.findCoopJobByJobId(jobId).getJobId());

		assertEquals(docId, service.getCoopJobDocuments(jobId).get(0).getDocumentId());
		assertEquals(1, service.getCoopJobDocuments(jobId).size());

	}

	@Test // trying to test getting all coopjobs in this test.
	public void testGetAllCoopJobs() {
		service.createCoopSystem();
		String jobId = "Cleaning2";
		String student1 = "May";
		String employerName = "Mike";

		service.createStudent(student1);
		service.createEmployer(employerName);
		service.createCoopJob(jobId, employerName, student1);

		assertEquals(1, service.getAllCoopJobs().size());
	}

	@Test // test of creation of document.
	public void testCreateDocument() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;
		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		assertEquals(1, service.getAllDocuments().size());

	}

	@Test // testing to set personal document.
	public void testSetPersonalDocument() {
		service.createCoopSystem();
		String employer = "holo";
		String docId = "1";
		String student = "Mike";
		DocumentType type = DocumentType.CV;
		ArrayList<String> documents = new ArrayList<>();
		documents.add(docId);

		service.createStudent(student);
		service.createEmployer(employer);
		service.createDocument(docId, student, type);

		assertEquals(1, service.getAllDocuments().size());

		service.setPersonalDocuments(student, documents);

		assertEquals(docId, service.getPersonalDocumentsByStudent(student).get(0).getDocumentId());
		assertEquals(1, service.getPersonalDocumentsByStudent(student).size());

	}

	@Test // testing to find documents.
	public void testFindDocuments() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;

		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		assertEquals(docId, service.findDocumentByDocumentId(docId).getDocumentId());

	}

	@Test // test of finding documents by its author.
	public void testFindDocumentsByAuthor() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.Other;

		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		ArrayList<Document> docs = service.getAllDocuments();
		ArrayList<Document> authored = service.findDocumentsByAuthor(authorName);
		assertEquals(1, docs.size());
		assertEquals(1, authored.size());

		assertEquals(docs.get(0).getDocumentId(), authored.get(0).getDocumentId());
	}

	@Test // testing the deletion of a document.
	public void testDeleteDocument() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		DocumentType type = DocumentType.CV;

		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);

		assertEquals(1, service.getAllDocuments().size());

		service.deleteDocument(docId);
		assertEquals(0, service.getAllDocuments().size());
	}

	@Test // testing to delete all of the documents.
	public void testDeleteAllDocuments() {
		service.createCoopSystem();
		String authorName = "holo";
		String docId = "1";
		String docId2 = "2";
		DocumentType type = DocumentType.CV;

		service.createEmployer(authorName);
		service.createDocument(docId, authorName, type);
		service.createDocument(docId2, authorName, type);
		assertEquals(2, service.getAllDocuments().size());

		service.deleteAllDocuments();
		assertEquals(0, service.getAllDocuments().size());
	}

	@Test // testing the creation of event notification.
	public void testCreateEventNotification() {
		service.createCoopSystem();
		String eventNotif = "Meeting Soon";
		service.createEventNotification(eventNotif);
		assertEquals(1, service.findAllEventNotifications().size());
		assertEquals(eventNotif, service.findEventNotificationByName(eventNotif).getName());

	}

	@Test // testing to delete an event notification.
	public void testDeleteEventNotification() {
		service.createCoopSystem();
		String eventNotif = "Meeting Soon";
		service.createEventNotification(eventNotif);
		assertEquals(1, service.findAllEventNotifications().size());
		assertEquals(eventNotif, service.findEventNotificationByName(eventNotif).getName());

		service.deleteEventNotification(eventNotif);
		assertEquals(0, service.findAllEventNotifications().size());

	}

	@Test // setting an event notificaton in this test.
	public void testSetEventNotification() {
		service.createCoopSystem();
		String eventNotif = "Meeting Soon";
		Event event = Event.conference;
		String location = "nowhere";
		Date date = new Date(System.currentTimeMillis() + 1000000000);
		Time timeStart = new Time(System.currentTimeMillis() + 1000);
		Time timeEnd = new Time(System.currentTimeMillis() + 10000000);

		service.createEventNotification(eventNotif);
		service.setEventNotificationSettings(eventNotif, event, location, date, timeStart, timeEnd);

		assertEquals(1, service.findAllEventNotifications().size());

		assertEquals(eventNotif, service.findEventNotificationByName(eventNotif).getName());
		assertEquals(event, service.findEventNotificationByName(eventNotif).getType());
		assertEquals(location, service.findEventNotificationByName(eventNotif).getLocation());

		assertEquals(date.toString(), service.findEventNotificationByName(eventNotif).getDate().toString());
		assertEquals(timeStart.toString(), service.findEventNotificationByName(eventNotif).getStartTime().toString());
		assertEquals(timeEnd.toString(), service.findEventNotificationByName(eventNotif).getEndTime().toString());

	}

}

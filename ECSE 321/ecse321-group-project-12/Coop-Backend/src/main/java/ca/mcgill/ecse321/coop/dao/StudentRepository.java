package ca.mcgill.ecse321.coop.dao;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.coop.model.Student;


public interface StudentRepository extends CrudRepository<Student, String>{
	

}
package ca.mcgill.ecse321.coop.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.coop.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, String>{
	

}
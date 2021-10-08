package ca.mcgill.ecse321.coop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.coop.model.CoopUser;

public interface CoopUserRepository extends CrudRepository<CoopUser, String> {

}

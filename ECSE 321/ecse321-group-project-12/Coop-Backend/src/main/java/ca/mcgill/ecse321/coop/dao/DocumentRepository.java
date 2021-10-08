package ca.mcgill.ecse321.coop.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.coop.model.Document;

public interface DocumentRepository extends CrudRepository<Document, String> {

}

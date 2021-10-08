package ca.mcgill.ecse321.coop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.coop.model.Message;

public interface MessageRepository extends CrudRepository<Message, String>{

}

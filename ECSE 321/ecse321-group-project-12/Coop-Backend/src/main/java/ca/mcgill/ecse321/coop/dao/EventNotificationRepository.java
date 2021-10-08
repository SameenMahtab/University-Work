package ca.mcgill.ecse321.coop.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.coop.model.EventNotification;

public interface EventNotificationRepository extends CrudRepository<EventNotification, String> {

}

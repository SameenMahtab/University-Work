package ca.mcgill.ecse321.coop.dao;

import org.springframework.data.repository.CrudRepository;


import ca.mcgill.ecse321.coop.model.CoopJob;


public interface CoopJobRepository extends CrudRepository<CoopJob, String>{

}

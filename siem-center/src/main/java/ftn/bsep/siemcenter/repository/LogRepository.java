package ftn.bsep.siemcenter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ftn.bsep.siemcenter.model.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {

	/*
	public List<Log> findByLevel(String level);
	public Optional<Log> findById(String id);
	public List<Log> findByComputer_name(String computerName);
	public List<Log> findBySource_name(String sourceName);
	public List<Log> findByEvent_id(String eventId);
	*/
	
}

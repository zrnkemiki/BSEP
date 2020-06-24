package ftn.bsep.siemcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.bsep.siemcenter.model.Log;
import ftn.bsep.siemcenter.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private NextSequenceService nextSeq;
	
	
	public Log saveLog(Log log) {
		String iddd = nextSeq.getNextSequence("customSequences");
		log.setId(iddd);
		Log saved = logRepository.save(log);
		return saved;
	}
	
	public List<Log> findAll() {
		return logRepository.findAll();
	}
	
	public void saveAll(List<Log> logs) {
		for (Log log : logs) {
			saveLog(log);
		}
	}
	
	public Log findById(String id) {
		Optional<Log> opt = logRepository.findById(id);
		return opt.orElse(null);
	}
	
	public List<Log> findByLevel(String level) {
		List<Log> ret = logRepository.findByLevel(level);
		return ret;
	}
	
	public List<Log> findByComputerName(String computerName) {
		return logRepository.findByComputerName(computerName);
	}
	
	public List<Log> findBySourceName(String sourceName) {
		return logRepository.findBySourceName(sourceName);
	}
	
	public List<Log> findByEventId(String eventId) {
		return logRepository.findByEventId(eventId);
	}
	
	public void emptyCollection() {
		logRepository.deleteAll();
	}
}

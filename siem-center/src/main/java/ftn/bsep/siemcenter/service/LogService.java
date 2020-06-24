package ftn.bsep.siemcenter.service;

import java.util.List;

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
		log.setId(nextSeq.getNextSequence("customSequences"));
		Log saved = logRepository.save(log);
		System.out.println("Log [" + saved.getId() + "] saved to database.");
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
}

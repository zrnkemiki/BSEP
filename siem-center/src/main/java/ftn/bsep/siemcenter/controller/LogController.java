package ftn.bsep.siemcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.siemcenter.model.Log;
import ftn.bsep.siemcenter.rbs.RuleEngine;
import ftn.bsep.siemcenter.service.LogService;

@RestController
public class LogController {
	
	@Autowired
	private LogService logService;
	
	@PostMapping("/post-logs")
	public void receiveLogs(@RequestBody List<Log> logs) {
		System.out.println("Usao");
		//RuleEngine rulesEngine = new RuleEngine();
		//for (Log log : logs) {
		//	log.setExecutionTime(new Date());
		//}
		//rulesEngine.insertLog(logs);
		logService.saveAll(logs);
		
		
	}
	//TEST CONTROLLER
	@PostMapping("/testCEP")
	public void testCEP() {
		System.out.println("Usao u test cep");
		
		Log log = new Log();
		log.setLevel("ERROR");
		log.setComputerName("TEST");
		log.setEventId("EventId");
		log.setMessage("OVO JE PORUKA");
		log.setSourceName("SOURCE");
		log.setTimestamp("VREME");
		log.setExecutionTime(new Date());
		Log log1 = new Log();
		log1.setLevel("ERROR");
		log1.setComputerName("TEST");
		log1.setEventId("EventId");
		log1.setMessage("OVO JE PORUKA");
		log1.setSourceName("SOURCE");
		log1.setTimestamp("VREME");
		log1.setExecutionTime(new Date());
		
		Log log2 = new Log();
		
		log2.setLevel("ERROR");
		log2.setComputerName("TEST");
		log2.setEventId("EventId");
		log2.setMessage("OVO JE PORUKA");
		log2.setSourceName("SOURCE");
		log2.setTimestamp("VREME");
		log2.setExecutionTime(new Date());
		
		List<Log> logs = new ArrayList<Log>();
		logs.add(log);
		logs.add(log1);
		logs.add(log2);
		RuleEngine rulesEngine = new RuleEngine();
		rulesEngine.insertLog(logs);
		
	}
	
}

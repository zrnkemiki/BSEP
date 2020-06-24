package ftn.bsep.siemcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.siemcenter.model.Log;
import ftn.bsep.siemcenter.rbs.RuleEngine;

@RestController
public class LogController {
	
	@PostMapping("/post-logs")
	public void receiveLogs(@RequestBody List<Log> logs) {
		System.out.println("Usao");
		RuleEngine rulesEngine = new RuleEngine();
		rulesEngine.insertLog(logs);
	}
	//TEST CONTROLLER
	@PostMapping("/testCEP")
	public void testCEP() {
		System.out.println("Usao u test cep");
		
		Log log = new Log();
		log.setLevel("ERROR");
		log.setComputer_name("TEST");
		log.setEvent_id("EVENT_ID");
		log.setMessage("OVO JE PORUKA");
		log.setSource_name("SOURCE");
		log.setTimestamp("VREME");
		log.setExecutionTime(new Date());
		Log log1 = new Log();
		log1.setLevel("ERROR");
		log1.setComputer_name("TEST");
		log1.setEvent_id("EVENT_ID");
		log1.setMessage("OVO JE PORUKA");
		log1.setSource_name("SOURCE");
		log1.setTimestamp("VREME");
		log1.setExecutionTime(new Date());
		
		Log log2 = new Log();
		
		log2.setLevel("ERROR");
		log2.setComputer_name("TEST");
		log2.setEvent_id("EVENT_ID");
		log2.setMessage("OVO JE PORUKA");
		log2.setSource_name("SOURCE");
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

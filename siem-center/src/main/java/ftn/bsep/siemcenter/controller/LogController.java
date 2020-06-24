package ftn.bsep.siemcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.siemcenter.model.Log;
import ftn.bsep.siemcenter.rbs.RuleEngineService;
import ftn.bsep.siemcenter.rbs.RuleEngine;
import ftn.bsep.siemcenter.service.LogService;

@RestController
public class LogController {
	
	private final RuleEngineService ruleEngineService;
	
	@Autowired
	public LogController(RuleEngineService ruleEngineService) {
		this.ruleEngineService = ruleEngineService;
	}
	@Autowired
	private LogService logService;
	
	@PostMapping("/post-logs")
	public void receiveLogs(@RequestBody List<Log> logs) {
		System.out.println("Usao");
		List<String> alerts = ruleEngineService.insertLog(logs);
		
		if(alerts.size()!= 0) {
			for (String alert : alerts) {
				System.out.println(alert);
			}
		}
		alerts.clear();
		//RuleEngine rulesEngine = new RuleEngine();
		//for (Log log : logs) {
		//	log.setExecutionTime(new Date());
		//}
		//rulesEngine.insertLog(logs);
		System.out.println("Logs received: " + logs.size());
		logService.saveAll(logs);
		List<Log> savedLogs = logService.findAll();
		if (savedLogs.size() > 0) {
			System.out.println("Sacuvano: " + savedLogs.size());
		}
		
	}
	
	
	
	//TEST CONTROLLER
	@PostMapping("/testCEP/{param}")
	public void testCEP(@PathVariable("param") Long id) {
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
		if(id==1) {
			Log log = new Log();
			log.setLevel("ERROR");
			log.setComputer_name("TEST");
			log.setEvent_id("EVENT_ID");
			log.setMessage("OVO JE PORUKA");
			log.setSource_name("SOURCE");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);

		}
		else if(id==2) {
			
			Log log1 = new Log();
			log1.setLevel("ERROR");
			log1.setComputer_name("TEST");
			log1.setEvent_id("EVENT_ID");
			log1.setMessage("OVO JE PORUKA");
			log1.setSource_name("SOURCE");
			log1.setTimestamp("VREME");
			log1.setExecutionTime(new Date());
			logs.add(log1);
		}
		else if(id==3) {
			Log log2 = new Log();
			
			log2.setLevel("ERROR");
			log2.setComputer_name("TEST");
			log2.setEvent_id("EVENT_ID");
			log2.setMessage("OVO JE PORUKA");
			log2.setSource_name("SOURCE");
			log2.setTimestamp("VREME");
			log2.setExecutionTime(new Date());
			logs.add(log2);
			
			
		}
		
		List<String> alerts = ruleEngineService.insertLog(logs);

		if(alerts.size()!= 0) {
			for (String alert : alerts) {
				System.out.println(alert);
			}
		}
		alerts.clear();
		
	}
	
}

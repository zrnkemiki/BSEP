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
		System.out.println("Lista size: " + logs.size());
		List<String> alerts;
		alerts = new ArrayList<String>();
		alerts = ruleEngineService.insertLog(logs);
		
		if(alerts != null) {
			for (String alert : alerts) {
				System.out.println(alert);
			}
		}
		//alerts.clear();
		//RuleEngine rulesEngine = new RuleEngine();
		//for (Log log : logs) {
		//	log.setExecutionTime(new Date());
		//}
		//rulesEngine.insertLog(logs);
		logService.saveAll(logs);
		
		
	}
	
	@GetMapping(value="/get", produces= MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getAllLogs() {
		return logService.findAll();
	}
	
	@GetMapping(value="/get/eventId={id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByEventId(@PathVariable("id") String id) {
		return logService.findByEventId(id);
	}
	
	@GetMapping(value="/get/level={level}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByLevel(@PathVariable("level") String level) {
		return logService.findByLevel(level);
	}
	
	@GetMapping(value="/get/computername={computerName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByComputerName(@PathVariable("computerName") String computerName) {
		return logService.findByComputerName(computerName);
	}
	
	@GetMapping(value="/get/sourcename={sourceName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsBySourceName(@PathVariable("sourceName") String sourceName) {
		return logService.findBySourceName(sourceName);
	}
	
	
	//TEST CONTROLLER
	@PostMapping("/testCEP/{param}")
	public void testCEP(@PathVariable("param") Long id) {
		System.out.println("Usao u test cep");
		
		ArrayList<Log> logs = new ArrayList<Log>();

		if(id==1) {
			Log log = new Log();
			log.setEventId("4625");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);

		}
		else if(id==2) {
			
			Log log1 = new Log();
			log1.setLevel("ERROR");
			log1.setMessage("OVO JE PORUKA");
			log1.setTimestamp("VREME");
			log1.setExecutionTime(new Date());
			logs.add(log1);
		}
		else if(id==3) {
			Log log2 = new Log();
			
			log2.setLevel("ERROR");
			log2.setMessage("OVO JE PORUKA");
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

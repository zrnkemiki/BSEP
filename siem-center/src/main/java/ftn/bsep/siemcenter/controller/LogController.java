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
	
	
	
	//TEST CONTROLLER
	@PostMapping("/testCEP/{param}")
	public void testCEP(@PathVariable("param") Long id) {
		System.out.println("Usao u test cep");
		
		ArrayList<Log> logs = new ArrayList<Log>();

		if(id==1) {
			Log log = new Log();
			log.setLevel("SUCCESS_AUDIT");
			log.setEventId("4672");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}
		
		if(id==2) {
			Log log = new Log();
			log.setLevel("FAILURE_AUDIT");
			log.setEventId("4625");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}
		if(id==3) {
			Log log = new Log();
			log.setLevel("WARNING");
			log.setEventId("1234");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}
		if(id==4) {
			Log log = new Log();
			log.setLevel("WARNING");
			log.setEventId("9999");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
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

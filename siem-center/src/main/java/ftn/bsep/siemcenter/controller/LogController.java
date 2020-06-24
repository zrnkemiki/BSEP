package ftn.bsep.siemcenter.controller;

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
		RuleEngine rulesEngine = new RuleEngine();
		rulesEngine.insertLog(logs);
		
		logService.saveAll(logs);
		List<Log> savedLogs = logService.findAll();
		if (savedLogs.size() > 0) {
			System.out.println("Sacuvano: " + savedLogs.size());
		}
		
	}
	
}

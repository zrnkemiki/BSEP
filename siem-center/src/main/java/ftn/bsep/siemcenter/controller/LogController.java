package ftn.bsep.siemcenter.controller;

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
	
}

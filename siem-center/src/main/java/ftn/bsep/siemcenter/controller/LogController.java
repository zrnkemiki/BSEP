package ftn.bsep.siemcenter.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

	private List<String> alerts;
	FileOutputStream fop = null;
	File file;

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

		if (alerts != null) {
			for (String alert : alerts) {
				System.out.println(alert);
			}
		}
		logService.saveAll(logs);

		try {
			FileOutputStream fout = new FileOutputStream(
					"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-center\\src\\main\\resources\\alerts.txt");
			String alertS = "";
			for (String string : alerts) {
				alertS = alertS + string;
			}
			byte b[] = alertS.getBytes();// converting string into byte array
			fout.write(b);
			fout.close();
			System.out.println("success...");
		} catch (Exception e) {
			{
				System.out.println("Error writing to file.");
			}
		}

		// alerts.clear();
		// RuleEngine rulesEngine = new RuleEngine();
		// for (Log log : logs) {
		// log.setExecutionTime(new Date());
		// }
		// rulesEngine.insertLog(logs);

	}

	@GetMapping("/getAlerts")
	public List<String> getAlerts() {
		List<String> alertsReturn = new ArrayList<String>();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(
					"C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-center\\src\\main\\resources\\alerts.txt"),
					"utf-8"));
			String s;
			while ((s = in.readLine()) != null) {
				alertsReturn.add(s);
			}
		} catch (Exception e) {
			{
				System.out.println("No logs exist!");
			}
		}

		return alertsReturn;
	}

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getAllLogs() {
		return logService.findAll();
	}

	@GetMapping(value = "/get/eventId={id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByEventId(@PathVariable("id") String id) {
		return logService.findByEventId(id);
	}

	@GetMapping(value = "/get/level={level}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByLevel(@PathVariable("level") String level) {
		return logService.findByLevel(level);
	}

	@GetMapping(value = "/get/computername={computerName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsByComputerName(@PathVariable("computerName") String computerName) {
		return logService.findByComputerName(computerName);
	}

	@GetMapping(value = "/get/sourcename={sourceName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Log> getLogsBySourceName(@PathVariable("sourceName") String sourceName) {
		return logService.findBySourceName(sourceName);
	}

	// TEST CONTROLLER
	@GetMapping("/testCEP/{param}")
	public List<String> testCEP(@PathVariable("param") Long id) {

		ArrayList<Log> logs = new ArrayList<Log>();
		List<String> alertsReturn = new ArrayList<String>();
		if (id == 1) {
			Log log = new Log();
			log.setLevel("SUCCESS_AUDIT");
			log.setEventId("4672");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}

		if (id == 2) {
			Log log = new Log();
			log.setLevel("FAILURE_AUDIT");
			log.setEventId("4625");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}
		if (id == 3) {
			Log log = new Log();
			log.setLevel("WARNING");
			log.setEventId("1234");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}
		if (id == 4) {
			Log log = new Log();
			log.setLevel("WARNING");
			log.setEventId("9999");
			log.setMessage("OVO JE PORUKA");
			log.setTimestamp("VREME");
			log.setExecutionTime(new Date());
			logs.add(log);
		}

		alerts = ruleEngineService.insertLog(logs);

		if (alerts.size() != 0) {
			for (String alert : alerts) {
				System.out.println("Ovo je alert: " + alert);
				alertsReturn.add(alert);
			}
		}

		alerts.clear();
		return alertsReturn;
	}

}

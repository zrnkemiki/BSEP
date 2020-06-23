package ftn.bsep.siemcenter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ftn.bsep.siemcenter.Proba;
import ftn.bsep.siemcenter.model.Log;

@RestController
public class LogController {
	
	@PostMapping("/post-logs")
	public void receiveLogs(@RequestBody List<Log> logs) {
		System.out.println("Usao");
		Proba proba = new Proba();
		for (Log log : logs) {
			System.out.println(log);
			proba.test1(log);
		}
	}
	
	@PostMapping("/a")
	public void test() {
		System.out.println("usao test");
	}
	
	@GetMapping("/b")
	public void test2() {
		System.out.println("get prosao");
	}
}

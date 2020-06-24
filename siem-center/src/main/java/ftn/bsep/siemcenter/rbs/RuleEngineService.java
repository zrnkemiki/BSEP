package ftn.bsep.siemcenter.rbs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.bsep.siemcenter.model.Log;


@Service
public class RuleEngineService {
	
	private final KieContainer kieContainer;
	private final KieSession kSession;
	
	@Autowired
	public RuleEngineService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
		kSession = kieContainer.newKieSession("rulesSession");
		kSession.setGlobal("logsOutput", new ArrayList<String>());
	}
	
	List<String> logsOutput;
	
	@SuppressWarnings("unchecked")
	public List<String> insertLog(List<Log> logs) {
		
		//KieServices ks = KieServices.Factory.get();
		//KieContainer kContainer = ks.newKieClasspathContainer();
		//KieSession kSession = kieContainer.newKieSession("rulesSession");
	//	kSession.setGlobal("logsOutput", new ArrayList<String>());
		
		for(Log log : logs) {
			log.setExecutionTime(new Date());
			kSession.insert(log);
		}
		
		int fired = kSession.fireAllRules();
		System.out.println("Number of rules fired: " + fired);
		
		
		try {
			logsOutput = (ArrayList<String>) kSession.getGlobal("logsOutput");
		} catch (Exception e) {
		
		}
        
		return logsOutput;
		
	}

}

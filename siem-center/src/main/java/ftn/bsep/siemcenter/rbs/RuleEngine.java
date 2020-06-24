package ftn.bsep.siemcenter.rbs;

import java.util.ArrayList;
import java.util.List;

import ftn.bsep.siemcenter.model.Log;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;



public class RuleEngine {
	
	List<String> logsOutput;
	
	@SuppressWarnings("unchecked")
	public List<String> insertLog(List<Log> logs) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.newKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("rulesSession");
		kSession.setGlobal("logsOutput", new ArrayList<String>());
		
		for (Log log : logs) {
			kSession.insert(log);
		}
		
		int fired = kSession.fireAllRules();
		System.out.println("Number of rules fired: " + fired);
		
		
		try {
			logsOutput = (ArrayList<String>) kSession.getGlobal("logsOutput");
		} catch (Exception e) {
		
		}
	
		if(logsOutput.size() != 0)
	        for (String data : logsOutput) {
				System.out.println(data);
	        }
        
		return logsOutput;
		
	}

}

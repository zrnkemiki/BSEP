package ftn.bsep.siemcenter;

import java.util.ArrayList;
import java.util.List;

import ftn.bsep.siemcenter.model.Log;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;



public class Proba {
	
	public static List<String> test1(Log log) {
		System.out.println("Bootstrapping the Rule Engine ...");
		// Bootstrapping a Rule Engine Session
		KieServices ks = KieServices.Factory.get();
		//KieContainer kContainer = ks.getKieClasspathContainer();
		KieContainer kContainer = ks.newKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("rulesSession");
		// KieSession kSession = kContainer.newKieSession("test-session2");
		kSession.setGlobal("fixes", new ArrayList<String>());
		
		
				
		kSession.insert(log);
		
		int fired = kSession.fireAllRules();
		System.out.println("No of rules fired: " + fired);
		System.out.println("=-=-=-=-=-=-=-=-=END-=-=-=-=-=-=-=-=-\n");
		
        @SuppressWarnings("unchecked")
		List<String> fixes = (ArrayList<String>) kSession.getGlobal("fixes");
        for (String fix : fixes) {
			System.out.println(fix);
		}
        return fixes;
		
	}

}

package ftn.bsep.siemcenter;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SiemCenterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SiemCenterApplication.class, args);
	}
	
	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}

}

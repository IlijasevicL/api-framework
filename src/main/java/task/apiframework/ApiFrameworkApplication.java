package task.apiframework;

import task.apiframework.configuration.ApplicationConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ApiFrameworkApplication {

	public static void main(String[] args) {
		System.setProperty("org.aspectj.weaver.Dump.condition", "abort");
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
	}

}

package hr.algebra.validationservers;
import hr.algebra.validationservers.xmlrpc.RpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ValidationServersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidationServersApplication.class, args);
		RpcServer.start();
	}

}

package entertain_me.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "EntertainmeApi", version = "Alpha", description = "Application Entertain-Me API"))
@SpringBootApplication
public class EntertainmeApi {

	public static void main(String[] args) {
		SpringApplication.run(EntertainmeApi.class, args);
	}
}
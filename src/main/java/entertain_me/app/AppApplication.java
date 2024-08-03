package entertain_me.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "entertainme-api", version = "1.0", description = "Application EntertainMe-Shamble API"))
@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {

		SpringApplication.run(AppApplication.class, args);
	}
}
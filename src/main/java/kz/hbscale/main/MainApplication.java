package kz.hbscale.main;

import kz.hbscale.main.model.UserEntity;
import kz.hbscale.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MainApplication {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainApplication.class);
		app.addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
		app.run(args);
	}


	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			UserEntity user = new UserEntity();
			user.setId(1l);
			user.setPassword(
					bCryptPasswordEncoder.encode("123")
			);
			user.setUsername("gali");
			userRepository.save(user);
		};
	}
}

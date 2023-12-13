package mailauthenticatorapp;

import jakarta.mail.MessagingException;
import mailauthenticatorapp.config.EmailConfig;
import mailauthenticatorapp.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class MailAuthenticatorAppApplication implements CommandLineRunner {

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(MailAuthenticatorAppApplication.class, args);
	}

	@Autowired
	private EmailUtil eu;

	@Override
	public void run(String... args) throws Exception {
		eu.sendOtpOnEmail("radhikashahade9096@gmail.com", "098098");
	}
}

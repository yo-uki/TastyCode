package pl.youkidesign.TastyCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.youkidesign.TastyCode.service.TastyCodeOrderResource;
import pl.youkidesign.TastyCode.utils.UserInputProcess;

@SpringBootApplication
public class TastyCodeApplication  implements CommandLineRunner {

	@Autowired
	TastyCodeOrderResource tastyCodeOrderResource;

	@Autowired
	UserInputProcess userInputProcess;


	public static void main(String[] args) {
		SpringApplication.run(TastyCodeApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("╔════════════════════════════════════╗");
		System.out.println("║        Welcome to TastyCode!       ║");
		System.out.println("╚════════════════════════════════════╝");

		boolean newOrderReuest = true;
		while (newOrderReuest) {
			tastyCodeOrderResource.makeAnOrder();
			System.out.println("Another order? [Y/N]");
			newOrderReuest = userInputProcess.ifOrder();
		}

		System.out.println("╔════════════════════════════════════╗");
		System.out.println("║         Goodbye TastyCode!         ║");
		System.out.println("╚════════════════════════════════════╝");
		System.exit(200);
	}
}

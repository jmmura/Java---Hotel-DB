package be.TF.JM.mvc;

import be.TF.JM.mvc.repository.ReservationRepository;
import be.TF.JM.mvc.utils.EMFSharer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.*;

@SpringBootApplication
public class DemoSpringMvcApplication {

	public static void main(String[] args) {
		ApplicationContext ctxt = SpringApplication.run(DemoSpringMvcApplication.class, args);


		ReservationRepository repository = ctxt.getBean(ReservationRepository.class);
		System.out.println("\n\n\n\n");
		repository.getFutureReservation().forEach(System.out::println);
		System.out.println("\n\n\n\n");


		ctxt.getBean(EMFSharer.class).close();
	}

}

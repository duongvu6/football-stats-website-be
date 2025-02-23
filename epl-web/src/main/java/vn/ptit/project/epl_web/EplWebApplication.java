package vn.ptit.project.epl_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.Player;

@SpringBootApplication
public class EplWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EplWebApplication.class, args);
	}

}

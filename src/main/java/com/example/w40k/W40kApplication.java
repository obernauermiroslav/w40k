package com.example.w40k;

import com.example.w40k.models.ShipClass;
import com.example.w40k.models.Ships;
import com.example.w40k.models.User;
import com.example.w40k.repositories.ShipRepository;
import com.example.w40k.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class W40kApplication implements CommandLineRunner {

	private final ShipRepository shipRepository;

	private final UserRepository userRepository;

	public W40kApplication(ShipRepository shipRepository, UserRepository userRepository) {
		this.shipRepository = shipRepository;
		this.userRepository = userRepository;
	}


@Autowired
	public W40kApplication(ShipRepository shipRepository) {
		this.shipRepository = shipRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(W40kApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		shipRepository.save(new Ships(1L, "Escort ship", ShipClass.ESCORTS, new User()));
	}
}

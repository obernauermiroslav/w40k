package com.example.w40k;

import com.example.w40k.models.ShipClass;
import com.example.w40k.models.Ships;
import com.example.w40k.models.UserRole;
import com.example.w40k.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class W40kApplication implements CommandLineRunner {

	private final ShipRepository shipRepository;

	@Autowired
	public W40kApplication(ShipRepository shipRepository) {
		this.shipRepository = shipRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(W40kApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		shipRepository.save(new Ships(1L, "Cobra-class Destroyer", ShipClass.ESCORTS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(2L, "Hunter-class Destroyer", ShipClass.ESCORTS, UserRole.SPACE_MARINES));
		shipRepository.save(new Ships(3L, "Viper-class Destroyer", ShipClass.ESCORTS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(4L, "Gladius-class Frigate", ShipClass.ESCORTS, UserRole.SPACE_MARINES));
		shipRepository.save(new Ships(5L, "Sword-class Heavy Frigate", ShipClass.ESCORTS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(6L, "Firestorm-class Frigate", ShipClass.ESCORTS, UserRole.IMPERIAL_NAVY));

		shipRepository.save(new Ships(7L, "Endeavor Class Light Cruiser", ShipClass.CRUISERS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(8L, "Lunar Class Cruiser", ShipClass.CRUISERS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(9L, "Armageddon Class Battle Cruiser", ShipClass.CRUISERS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(10L, "Vengeance Class Grand Cruiser", ShipClass.CRUISERS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(11L, "Punisher-class Strike Cruiser", ShipClass.CRUISERS, UserRole.SPACE_MARINES));

		shipRepository.save(new Ships(12L, "Retribution Class Battleship", ShipClass.BATTLESHIPS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(13L, "Emperor Class Battleship", ShipClass.BATTLESHIPS, UserRole.IMPERIAL_NAVY));
		shipRepository.save(new Ships(14L, "Ironclad-class Battle Barge", ShipClass.BATTLESHIPS,UserRole.SPACE_MARINES));
	}
}

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

	@Autowired
	public W40kApplication(ShipRepository shipRepository, UserRepository userRepository) {
		this.shipRepository = shipRepository;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(W40kApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user1 = userRepository.save(new User(1L, "w40k@gmail.com", "Imperial Navy", "braceforimpact"));
		User user2 = userRepository.save(new User(2L, "astartes@gmail.com", "Space Marines", "weshalknownofear"));

		shipRepository.save(new Ships(1L, "Cobra-class Destroyer", ShipClass.ESCORTS, user1));
		shipRepository.save(new Ships(2L, "Hunter-class Destroyer", ShipClass.ESCORTS, user2));
		shipRepository.save(new Ships(3L, "Viper-class Destroyer", ShipClass.ESCORTS, user1));
		shipRepository.save(new Ships(4L, "Gladius-class Frigate", ShipClass.ESCORTS, user2));
		shipRepository.save(new Ships(5L, "Sword-class Heavy Frigate", ShipClass.ESCORTS, user1));
		shipRepository.save(new Ships(6L, "Firestorm-class Frigate", ShipClass.ESCORTS, user1));

		shipRepository.save(new Ships(7L, "Endeavor Class Light Cruiser", ShipClass.CRUISERS, user1));
		shipRepository.save(new Ships(8L, "Lunar Class Cruiser", ShipClass.CRUISERS, user1));
		shipRepository.save(new Ships(9L, "Armageddon Class Battle Cruiser", ShipClass.CRUISERS, user1));
		shipRepository.save(new Ships(10L, "Vengeance Class Grand Cruiser", ShipClass.CRUISERS, user1));
		shipRepository.save(new Ships(11L, "Punisher-class Strike Cruiser", ShipClass.CRUISERS, user2));

		shipRepository.save(new Ships(12L, "Retribution Class Battleship", ShipClass.BATTLESHIPS, user1));
		shipRepository.save(new Ships(13L, "Emperor Class Battleship", ShipClass.BATTLESHIPS, user1));
		shipRepository.save(new Ships(14L, "Ironclad-class Battle Barge", ShipClass.BATTLESHIPS, user2));







	}
}

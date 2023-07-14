package com.example.w40k.services;

import com.example.w40k.components.JwtUtilities;
import com.example.w40k.models.Responses;
import com.example.w40k.models.SecurityUser;
import com.example.w40k.models.User;
import com.example.w40k.models.UserDto;
import com.example.w40k.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDatabaseService implements UserService{

    private final UserRepository userRepository;
    private final JwtUtilities jwtUtilities;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDatabaseService(UserRepository userRepository, JwtUtilities jwtUtilities, EmailSenderService emailSenderService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtilities = jwtUtilities;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByActivationCode(String activationCode) {
        return userRepository.findByActivationCode(activationCode);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> myUser = userRepository.findByUsernameOrEmail(username, username);
        UserDetails userDetails = null;
        if(myUser.isPresent()) {
            userDetails = userRepository
                    .findByUsername(username)
                    .map(SecurityUser::new)
                    .orElseThrow(()-> new UsernameNotFoundException("Username not found: " + username));
        }

        return userDetails;
    }


    public ResponseEntity<Responses> register(User user) {
        if (user.getUsername().isEmpty()) {
            return ResponseEntity.status(400).body(new Responses("username", "User name is empty!"));
        }

        if (user.getEmail().isEmpty()) {
            return ResponseEntity.status(400).body(new Responses("email", "Email is empty!"));
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body(new Responses("email", "Email already exists!"));
        }

        if (user.getPassword().isEmpty()) {
            return ResponseEntity.status(400).body(new Responses("password", "Password is empty!"));
        }

        if (!isGoodPassword(user.getPassword()) || user.getPassword().length() < 8) {
            return ResponseEntity.status(400).body(new Responses("password",
                    "Password has to have at least 8 characters and has to contain at least " +
                            "one capital letter, one number and one special character - !@#$%^&* !"
            ));
        }

        String activationCode = GenerateActivationCode.generate(48);

        user.setActivationCode(activationCode);
        user.setIsActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User registeredUser = userRepository.save(user);

        emailSenderService.sendEmail(registeredUser.getEmail(), activationCode);

        return ResponseEntity.ok(new Responses("success", user.getUsername() +
                ", you were successfully registered! Now you can log in and have to activate your account to gain full access."));
    }


    @Override
    public ResponseEntity<Responses> login(UserDto user) {
        Optional<User> myUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getUsername());

        if(!myUser.isPresent()) {
            return ResponseEntity.badRequest().body(new Responses("username", "User doesn't exist!"));
        }

        if ((myUser.get().getUsername().equals(user.getUsername()) || myUser.get().getEmail().equals(user.getUsername()))
                && !passwordDecoder(user.getPassword(), myUser.get().getPassword())) {
            return ResponseEntity.badRequest().body(new Responses("password", "Wrong password!"));
        }

        final UserDetails userDetails = loadUserByUsername(user.getUsername());
        var token = jwtUtilities.generateToken(userDetails);

        return ResponseEntity.ok(new Responses("jwt", token));
    }

    @Override
    public ResponseEntity<?> activate(String username, String activationCode) {
        Optional<User> userOptional = userRepository.findByUsernameAndActivationCode(username, activationCode);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            user.setActivationCode(null);
            userRepository.save(user);

            Responses successResponse = new Responses("success", "User activated successfully! You can now log in.");
            return ResponseEntity.ok(successResponse);
        } else {
            Responses errorResponse = new Responses("error", "Invalid activation code.");
            return ResponseEntity.ok(errorResponse);
        }
    }

    private boolean passwordDecoder(String password, String encodedPassword) {
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        return matches;
    }

    private boolean isGoodPassword(String password) {
        String specialCharacters = "!@#$%^&*";
        boolean containsCapital = false;
        boolean containsNumber = false;
        boolean containsSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                containsCapital = true;
            }

            if (Character.isDigit(ch)) {
                containsNumber = true;
            }

            if (specialCharacters.contains(String.valueOf(ch))) {
                containsSpecial = true;
            }

            if(containsCapital && containsNumber && containsSpecial) {
                return true;
            }
        }
        return false;
    }
}



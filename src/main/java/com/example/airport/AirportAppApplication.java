package com.example.airport;

import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AirportAppApplication {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private UserRoleRepository userRoleDao;

	@Autowired
	private UserStatusRepository userStatusDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	public static void main(String[] args) {

		SpringApplication.run(AirportAppApplication.class, args);
		System.out.println("Airport App Started");
	}

	@GetMapping("/data/me")
	public ResponseEntity<?> getMyDetails(){
		return ResponseEntity.ok("");
	}


}

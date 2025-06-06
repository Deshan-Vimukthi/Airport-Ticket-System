package com.example.airport;

import com.example.airport.airplane.Airplane;
import com.example.airport.airplane.AirplaneRepository;
import com.example.airport.countries.CountryRepository;
import com.example.airport.models.AirplaneModels;
import com.example.airport.models.AirplaneModelsRepository;
import com.example.airport.users.User;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private UserStatusRepository userStatusDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AirplaneRepository airplaneDao;

    @Autowired
    private AirplaneModelsRepository airplaneModelsDao;

    @Autowired
    private CountryRepository countryDao;

    @Override
    @Transactional
    public void run(String... args) {
        if (userDao.findByUsername("Admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("Admin");
            adminUser.setPassword(bCryptPasswordEncoder.encode("Admin@123"));
            adminUser.setUserStatus(userStatusDao.getReferenceById(1));
            adminUser.setCreateDate(LocalDateTime.now());
            adminUser.setRole(userRoleDao.getReferenceById(1));

            userDao.save(adminUser);
            System.out.println("✅ Admin user created successfully!");
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }
}


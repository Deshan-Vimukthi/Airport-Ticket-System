package com.example.airport.webConfigaration;


import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.userRole.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import  org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserStatusRepository userStatusDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MenuService menuService;


    // GET: /login
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("pageTitle", "Login - My App");
        return "auth";
    } // maps to src/main/resources/templates/auth.html

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user manually or with a service
        boolean success = authService.login(loginRequest);

        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid credentials"));
        }
    }

    @PostMapping("/api/register-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequest registerRequest) {
        try {
            customerService.register(registerRequest);
             // assuming login auto-generates a token/session

            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Unexpected error: " + e.getMessage()
            ));
        }
    }


    // GET: /register
    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth"; // same page with tab switched via JS if needed
    }

    // GET: /reset-user/test (dev/test only)
    @GetMapping("/reset-user/test")
    public String showResetPasswordDevPage() {
        return "reset-password-dev"; // maps to reset-password-dev.html
    }
/*


    @GetMapping(value = "/login")
    public ModelAndView loginUI(){
        return new ModelAndView("login");
    }
*/

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user,mav);
        mav.addObject("content", "reports"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/reports");
        return mav;
    }

    @GetMapping("/reports")
    public ModelAndView showReports() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "reports"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/reports");
        return mav;
    }

    @GetMapping("/airplanes")
    public ModelAndView showAirplanes() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "airplane"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/airplanes");
        mav.addObject("customJSURL","/airplane.js");
        return mav;
    }

    @GetMapping("/users")
    public ModelAndView showUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "Users"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/users");
        mav.addObject("customJSURL","/users.js");
        return mav;
    }

    @GetMapping("/employees")
    public ModelAndView showEmployees() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "Employee"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/employee");
        mav.addObject("customJSURL","/employees.js");
        return mav;
    }

    @GetMapping("/customers")
    public ModelAndView showCustomers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "Customer"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/customers");
        mav.addObject("customJSURL","/customers.js");
        return mav;
    }



    @GetMapping("/my-booking")
    public ModelAndView showMyBooking() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "my-booking"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/my-booking");
        mav.addObject("customJSURL","/my-booking.js");
        return mav;
    }

    @GetMapping("/payment")
    public ModelAndView showPayments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElseThrow(()->new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN));
        ModelAndView mav = new ModelAndView();
        mav = MenuItem.addMenu(user, mav); // Sets sidebar and view name
        mav.addObject("content", "payments"); // Sets which fragment to load
        mav.addObject("selectedUrl", "/payment");
        mav.addObject("customJSURL","/payment.js");
        return mav;
    }


    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}

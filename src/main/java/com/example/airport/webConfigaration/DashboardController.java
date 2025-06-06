package com.example.airport.webConfigaration;


import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import  org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
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
    private MenuService menuService;


    @GetMapping(value = "/login")
    public ModelAndView loginUI(){
        return new ModelAndView("login");
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        List<MenuItem> combinedMenu = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            combinedMenu.addAll(menuService.getMenuForRole(authority.getAuthority()));
        }
        combinedMenu.add(new MenuItem("Dashboard", "/dashboard", "ni ni-tv-2 text-primary"));
        combinedMenu.add(new MenuItem("Flights", "/flights", "ni ni-send text-danger"));

        ModelAndView mav = new ModelAndView("admin-dashboard");
        mav.addObject("sidebarMenu", combinedMenu);
        return mav;
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}

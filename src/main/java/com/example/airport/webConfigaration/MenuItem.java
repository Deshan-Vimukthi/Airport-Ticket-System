package com.example.airport.webConfigaration;

import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.userRole.UserRole;
import com.example.airport.users.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuItem {
    private String title;
    private String url;
    private String icon;

    public static ModelAndView addMenu(User user,ModelAndView mav){
        List<MenuItem> combinedMenu = new ArrayList<>();
        if(user.getRole().getId() <= UserRole.RoleEnum.ADMIN.getId()){
            combinedMenu.add(new MenuItem("Airplanes", "/airplanes", "fa-solid fa-plane-up"));
            combinedMenu.add(new MenuItem("Users", "/users", "fa-solid fa-user-tie"));
            combinedMenu.add(new MenuItem("Employees", "/employees", "fa-solid fa-users-line"));
        }if(user.getRole().getId() <= UserRole.RoleEnum.OPERATOR.getId()){
            combinedMenu.add(new MenuItem("Customers", "/customers", "fa-solid fa-id-card-clip"));
            combinedMenu.add(new MenuItem("Flights", "/flights", "fa-solid fa-plane"));
            combinedMenu.add(new MenuItem("Reports", "/reports", "fa-solid fa-folder-tree"));
        }if (user.getRole().getId() <= UserRole.RoleEnum.CUSTOMER.getId()) {
            combinedMenu.add(new MenuItem("My Booking", "/my-booking", "fa-solid fa-ticket"));
            combinedMenu.add(new MenuItem("Payments", "/payment", "fa-solid fa-credit-card"));
        }

        mav.setViewName("admin-dashboard");
        mav.addObject("sidebarMenu", combinedMenu);
        return mav;
    }
    // Constructors, Getters, Setters
}


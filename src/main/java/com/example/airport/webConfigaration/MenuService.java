package com.example.airport.webConfigaration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Cacheable(value = "menuCache", key = "#role")
    public List<MenuItem> getMenuForRole(String role) {
        // Simulate heavy logic or DB calls
        List<MenuItem> menu = new ArrayList<>();
        if ("ADMIN".equals(role)) {
            menu.add(new MenuItem("Admin Dashboard", "/admin", "fa fa-lock"));
        } else if ("CUSTOMER".equals(role)) {
            menu.add(new MenuItem("User Dashboard", "/user", "fa fa-home"));
        }
        // ... Add more role handling
        return menu;
    }
}

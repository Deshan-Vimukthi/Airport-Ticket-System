package com.example.airport.webConfigaration;

import com.example.airport.View;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleViewResolver {
    private static final Map<String, Integer> ROLE_PRIORITY = Map.of(
            "PUBLIC", 0,
            "CUSTOMER", 1,
            "OPERATION_MANAGER", 2,
            "ADMIN", 3
    );

    private static final Map<Integer, Class<?>> PRIORITY_TO_VIEW = Map.of(
            0, View.Public.class,
            1, View.Customer.class,
            2, View.Operator.class,
            3, View.Admin.class
    );

    public static Class<?> resolveJsonView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return View.Public.class;
        }

        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        int maxPriority = 0;

        for (String role : roles) {
            String cleanRole = role.startsWith("ROLE_") ? role.substring(5) : role;

            Integer priority = ROLE_PRIORITY.get(cleanRole);
            if (priority != null && priority > maxPriority) {
                maxPriority = priority;
            }
        }

        return PRIORITY_TO_VIEW.getOrDefault(maxPriority, View.Public.class);
    }
}

package com.example.airport.exceptionHandling.AccessControlAnnotaion;


import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedCode;
import com.example.airport.exceptionHandling.accessDeniedException.AccessDeniedException;
import com.example.airport.userRole.UserRole;
import com.example.airport.userRole.UserRoleRepository;
import com.example.airport.userStatus.UserStatusRepository;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class HasAuthorityAspect {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserRoleRepository userRoleDao;

    @Autowired
    private UserStatusRepository userStatusDao;

    @Before("@annotation(HasAuthority)")
    public void checkAuthority(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HasAuthority annotation = method.getAnnotation(HasAuthority.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated", AccessDeniedCode.NOT_SIGNED_IN);
        }

        User user = userDao.findByUsername(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("Invalid user credentials", AccessDeniedCode.INVALID_USERNAME));

        // Check user status
        if (!isUserActive(user)) {
            throw new AccessDeniedException("User account is " + user.getUserStatus().getName(), AccessDeniedCode.EXPIRED_ACCOUNT);
        }

        // If role is OPERATOR, validate linked employee account
        if (isOperator(user)) {
            validateEmployeeStatus(user);
        }

        // Role-based access check
        if (!isAuthorized(user, annotation.value(), annotation.enablePriorityOrder())) {
            throw new AccessDeniedException("You do not have permission to perform this action.", AccessDeniedCode.ROLE_DENIED);
        }
    }


    private boolean isUserActive(User user) {
        return user.getUserStatus() != null && user.getUserStatus().getId() == 1;
    }

    private boolean isOperator(User user) {
        return user.getRole() != null && user.getRole().getId() == UserRole.RoleEnum.OPERATOR.getId();
    }

    private void validateEmployeeStatus(User user) {
        if (user.getEmployee() == null) {
            throw new AccessDeniedException("Operator account missing employee link", AccessDeniedCode.INVALID_ACCOUNT);
        }

        Integer employeeStatusId = user.getEmployee().getStatus().getId();
        if (employeeStatusId == null || employeeStatusId != 1) {
            throw new AccessDeniedException("Employee status is " + user.getEmployee().getStatus().getName(), AccessDeniedCode.EXPIRED_ACCOUNT);
        }
    }

    private boolean isAuthorized(User user, UserRole.RoleEnum[] allowedRoles, boolean priorityOrder) {
        int userRoleId = user.getRole().getId();

        for (UserRole.RoleEnum role : allowedRoles) {
            int requiredId = role.getId();

            if (!priorityOrder && userRoleId == requiredId) return true;
            if (priorityOrder && userRoleId <= requiredId) return true;
        }

        return false;
    }

}

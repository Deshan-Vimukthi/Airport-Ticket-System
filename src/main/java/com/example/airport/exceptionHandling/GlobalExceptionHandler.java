package com.example.airport.exceptionHandling;

import com.example.airport.userRole.UserRole;
import com.example.airport.users.User;
import com.example.airport.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private static UserRepository userDao;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> getAccessDeniesException(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied : "+ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<?> getAccessDeniedException(HttpMessageNotWritableException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    public static boolean checkAuthorization(boolean throwable,UserRole... userRoles){
        boolean state = checkAuthorization(userRoles);
        if(throwable && !state)
            throw new AccessDeniedException("User wasn't have permission.");
        return state;
    }
    public static boolean checkAuthorization(UserRole... userRoles){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByUsername(auth.getName()).orElse(null);
        if(user == null){
            return false;
        }
        for(UserRole userRole:userRoles){
            if(user.getRole() == userRole) return true;
        }
        return false;
    }
}

package com.example.airport.exceptionHandling.AccessControlAnnotaion;

import com.example.airport.userRole.UserRole;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasAuthority {
    UserRole.RoleEnum[] value();
    boolean enablePriorityOrder() default false;
}


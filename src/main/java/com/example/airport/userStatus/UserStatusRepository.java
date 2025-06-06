package com.example.airport.userStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

    @Query("SELECT us FROM UserStatus us WHERE us.id <> 2")
    List<UserStatus> getUserStatusListWithoutDelete();

    @Query("SELECT us FROM UserStatus us WHERE us.id = ?1")
    UserStatus getUserStatusById(Integer id);
}

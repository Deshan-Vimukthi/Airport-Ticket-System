package com.example.airport.users;

import com.example.airport.ApiResponse;
import com.example.airport.exceptionHandling.AccessControlAnnotaion.HasAuthority;
import com.example.airport.userRole.UserRole;
import com.example.airport.userStatus.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserStatusRepository userStatusDao;

    @HasAuthority(UserRole.RoleEnum.ADMIN)
    @GetMapping("")
    public ResponseEntity<?> getUsersList(){
        List<User> userList = userDao.findAll();
        return ResponseEntity.ok(ApiResponse.success(userList));
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserStatus(userStatusDao.getUserStatusById(1));
        user.setCreateDate(LocalDateTime.now());
        user.setUpdatedDate(null);
        user.setDeletedDate(null);
        userDao.save(user);

        return ResponseEntity.ok(ApiResponse.success("user created successfully"));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        Optional<User> optionalUser = userDao.findByUsername(request.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        user.setUpdatedDate(LocalDateTime.now());
        userDao.save(user);

        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
        Optional<User> optionalUser = userDao.findByUsername(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Email not found"));
        }

        User user = optionalUser.get();

        // Generate token (you can use UUID or JWT)
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30)); // valid for 30 mins
        userDao.save(user);

        // Send email (or return token in response for dev/testing)
        // emailService.sendPasswordResetEmail(user.getEmail(), resetToken);

        return ResponseEntity.ok(ApiResponse.success("Reset token generated", resetToken)); // You might omit token in prod
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        Optional<User> optionalUser = userDao.findByResetToken(request.getToken());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid or expired token"));
        }

        User user = optionalUser.get();

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Token has expired"));
        }

        user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        user.setUpdatedDate(LocalDateTime.now());
        userDao.save(user);

        return ResponseEntity.ok(ApiResponse.success("Password reset successfully"));
    }


}

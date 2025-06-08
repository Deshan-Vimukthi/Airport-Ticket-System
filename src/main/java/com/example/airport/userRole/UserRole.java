package com.example.airport.userRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_role")
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public enum RoleEnum {
        ADMIN(1),
        OPERATOR(2),
        CUSTOMER(3);

        private final int id;

        RoleEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

}

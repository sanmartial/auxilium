package com.globaroman.auxilium.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globaroman.auxilium.model.entity.security.RoleAUX;
import com.globaroman.auxilium.model.entity.security.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "users_aux")
@Entity
public class UserAUX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonIgnore
    private String user_id;
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate date_birth;
    @JsonIgnore
    private LocalDateTime date_logged;
    private String email;
    private String phonenumber;
    @Enumerated(EnumType.STRING)
    private RoleAUX role;
    @Enumerated(EnumType.STRING)
    private Status status;

    public UserAUX(String username, String password, String firstname, String lastname, String email, String phonenumber, RoleAUX role, Status status) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.role = role;
        this.status = status;
    }
}




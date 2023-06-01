package com.globaroman.auxilium.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "user_id", nullable = false)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
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




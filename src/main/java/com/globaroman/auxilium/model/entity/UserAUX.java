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
@Table(name = "users")
@Entity
public class UserAUX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleAUX role;

    public UserAUX(String userName, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "UserAUX{" + "id=" + id +
                ", userName= '" + userName + '\'' +
                ", password= '" + password + '\'' +
                ", firstName= '" + firstName + '\'' +
                ", lastName= ' " + lastName + '\'' +
                ", email= '" + email + '\'' +
                ", phoneNumber= '" + phoneNumber + '\'' +
                ", role= " + role.getRoleName() +
                '}';
    }
}

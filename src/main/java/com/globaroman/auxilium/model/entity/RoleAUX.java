package com.globaroman.auxilium.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
@Entity
public class RoleAUX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long id;
    @Column(name = "role_name")
    private String roleName;

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<UserAUX> usersList;

    public RoleAUX(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoleAUX{");
        sb.append("id=").append(id);
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void addListRoles(UserAUX userAUX) {
        usersList.add(userAUX);
    }
}

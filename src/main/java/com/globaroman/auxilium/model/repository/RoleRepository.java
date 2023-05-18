package com.globaroman.auxilium.model.repository;

import com.globaroman.auxilium.model.entity.RoleAUX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleAUX, Long> {

}

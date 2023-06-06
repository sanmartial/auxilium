package com.globaroman.auxilium.model.repository;

import com.globaroman.auxilium.model.entity.UserAUX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAUX, Long> {
    @Query("select u from UserAUX u where u.username = :username ")
    Optional<UserAUX> findByUsername(@Param("username") String username);

    @Query("select u from UserAUX u where u.role = 'PATIENT'")
    List<UserAUX> findAllPatients();
}

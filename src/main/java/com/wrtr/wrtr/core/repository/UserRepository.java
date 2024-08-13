package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email= :email")
    public User getUserByUsername(@Param("email") String username);

    @Query("SELECT u FROM User u WHERE u.id= :id")
    public User getUserById(@Param("id") UUID id);
}

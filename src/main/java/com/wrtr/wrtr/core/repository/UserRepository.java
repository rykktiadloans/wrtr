package com.wrtr.wrtr.core.repository;

import com.wrtr.wrtr.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository of users
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    /**
     * Returns a user with the same email
     * @param email Email to look for with
     * @return Matching user
     */
    @Query("SELECT u FROM User u WHERE u.email= :email")
    public User getUserByEmail(@Param("email") String email);

    /**
     * Returns a user with the same ID
     * @param id User's UUID
     * @return Matching user
     */
    @Query("SELECT u FROM User u WHERE u.id= :id")
    public User getUserById(@Param("id") UUID id);

    /**
     * Returns all the users whose username contains the supplied substring
     * @param matchBy Substring to match by
     * @return List of matched users
     */
    @Query("FROM User u WHERE u.username LIKE CONCAT('%', :matchBy, '%')")
    public List<User> searchUsersWithSimilarUsername(@Param("matchBy") String matchBy);
}

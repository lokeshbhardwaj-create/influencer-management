package com.influencers.socialMediainfluencers.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByEmailId(String email);


    @Query("SELECT u FROM User u WHERE u.emailId = :email AND u.username = :username")
    User findByEmailIdAndUsername(@Param("email") String email, @Param("username") String username);


    boolean existsByEmailId(String emailId);
}

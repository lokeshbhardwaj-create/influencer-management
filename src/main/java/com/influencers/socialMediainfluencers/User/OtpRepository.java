package com.influencers.socialMediainfluencers.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository <OtpVerification, Long> {

        OtpVerification findTopByEmailAndNameOrderByCreatedAtDesc(String email, String name);

  //  @Query("SELECT o FROM OtpVerification o WHERE o.email = :email AND TRIM(o.username) = :username ORDER BY o.createdAt DESC")
    //OtpVerification findTopByEmailAndUsernameOrderByCreatedAtDesc(@Param("email") String email, @Param("username") String username);
    @Query(value = "SELECT * FROM otp_verification WHERE email = :email AND TRIM(username) = :username ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    OtpVerification findLatestByEmailAndUsername(@Param("email") String email, @Param("username") String username);

}


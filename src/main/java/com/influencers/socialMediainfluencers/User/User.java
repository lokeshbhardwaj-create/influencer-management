package com.influencers.socialMediainfluencers.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "UsersInformation")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;
    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(name = "username", nullable = false)
    private String username;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private String Password;
    private String role;
   @Column(nullable = false )
   private int credit = 1000;

    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



}

package com.influencers.socialMediainfluencers.influencers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    private String platform;
    private String username;
    private Long avgLikes;
    private Long avgViews;
    private Integer uploadsPerWeek;
    private LocalDateTime lastUpdated;
    private Long followers;

}

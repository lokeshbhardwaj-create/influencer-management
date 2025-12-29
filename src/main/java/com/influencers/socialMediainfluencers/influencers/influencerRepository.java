package com.influencers.socialMediainfluencers.influencers;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface influencerRepository extends JpaRepository<Influencer,Long> {


    @Query("SELECT i FROM Influencer i WHERE "
            + "(:platform IS NULL OR i.platform = :platform) AND "
            + "(:username IS NULL OR i.username LIKE %:username%) AND "
            + "(:minFollowers IS NULL OR i.followers >= :minFollowers) AND "
            + "(:maxFollowers IS NULL OR i.followers <= :maxFollowers)")
    Page<Influencer> findByFilters(@Param("platform") String platform,
                                   @Param("username") String username,
                                   @Param("minFollowers") Long minFollowers,
                                   @Param("maxFollowers") Long maxFollowers,
                                   Pageable pageable);

}

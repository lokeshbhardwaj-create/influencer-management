package com.influencers.socialMediainfluencers.influencers;
import com.influencers.socialMediainfluencers.Credit.CreditService;
import com.influencers.socialMediainfluencers.Exceptionhandeler.InsufficientCreditsException;
import com.influencers.socialMediainfluencers.User.User;
import com.influencers.socialMediainfluencers.User.UserRepository;
import org.springframework.data.domain.Pageable; // ✅ Correct import
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
public class InfluencerService {

    @Autowired
    private UserRepository userRepository;

@Autowired
private influencerRepository influencerRepository;

@Autowired
private CreditService creditService;

    @Transactional
    public void saveInfluencers(List<Influencer> influencers) {
        influencerRepository.saveAll(influencers);
    }

    public void importInfluencersFromCSV(MultipartFile file) {
        if (!CSVHelper.hasCSVFormat(file)) {
            throw new RuntimeException("Invalid file format. Please upload a CSV file.");
        }
        try {
            List<Influencer> influencers = CSVHelper.csvToInfluencers(file.getInputStream());
            influencerRepository.saveAll(influencers);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store influencer data: " + e.getMessage());
        }
    }

    public Page<Influencer> getInfluencers(String platform, String username, Long minFollowers, Long maxFollowers, int page, int size, long userId) {
        // Check if user is an admin
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equalsIgnoreCase("ADMIN")) {
            // Apply credit system only for non-admin users
            int recordsToFetch = size;
            int creditsRequired = recordsToFetch / 10; // Example: 100 credits for 1000 records

            if (creditService.getCredits(userId) >= creditsRequired) {
                creditService.deductCredits(userId, creditsRequired);
            } else {
                throw new InsufficientCreditsException("Not enough credits");
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("followers").descending());
        return influencerRepository.findByFilters(platform, username, minFollowers, maxFollowers, pageable);
    }


}


package com.influencers.socialMediainfluencers.influencers;

import jakarta.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class influencerController {
    @Autowired
    InfluencerService influencerService;

    @RequestMapping("/upload")
    public ResponseEntity<String> saveInfluencer(@RequestParam("file")MultipartFile file){
        influencerService.importInfluencersFromCSV(file);
             return ResponseEntity.ok("Data uploaded successfully");
    }

    @GetMapping("/search")
    public Page<Influencer> searchInfluencers(
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long minFollowers,
            @RequestParam(required = false) Long maxFollowers,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam long userId) {
        return influencerService.getInfluencers(platform, username, minFollowers, maxFollowers, page, size, userId);
    }


}

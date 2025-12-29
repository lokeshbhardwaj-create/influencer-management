package com.influencers.socialMediainfluencers.influencers;

import jakarta.mail.Multipart;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
public class CSVHelper {

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file){
      return TYPE.equals(file.getContentType());
    }


    public static List<Influencer> csvToInfluencers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<Influencer> influencers = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                Influencer influencer = Influencer.builder()
                        .platform(record.get("platform"))
                        .username(record.get("username"))
                        .followers(Long.parseLong(record.get("followers")))
                        .avgLikes(Long.parseLong(record.get("avg_likes")))
                        .avgViews(Long.parseLong(record.get("avg_views")))
                        .uploadsPerWeek(Integer.parseInt(record.get("uploads_per_week")))
                        .lastUpdated(java.time.LocalDateTime.now())
                        .build();

                influencers.add(influencer);
            }

            return influencers;
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}







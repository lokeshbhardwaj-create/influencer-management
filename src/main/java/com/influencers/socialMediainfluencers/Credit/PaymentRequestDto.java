package com.influencers.socialMediainfluencers.Credit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long userId;
    private int credit;

    private String paymentToken;
    @OneToOne
    private PaymentDetailsDTO paymentDetailsDTO;
}

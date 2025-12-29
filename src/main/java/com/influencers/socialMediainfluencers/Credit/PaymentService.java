package com.influencers.socialMediainfluencers.Credit;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${Stripe.Secret.Key}")
    private String stripeSecretKey;

    public PaymentService(@Value("${Stripe.Secret.Key}") String stripeSecretKey) {
        Stripe.apiKey = stripeSecretKey;
    }
    public boolean processPayment(PaymentRequestDto paymentRequestDTO) {
       ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount((long) paymentRequestDTO.getCredit() * 100)
                .setCurrency("usd")
                .setDescription("credit purchase")
                .setSource("tok_visa")
                .build();

        try {
            Charge charge = Charge.create(params);
            return charge.getPaid();
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }

    }
}
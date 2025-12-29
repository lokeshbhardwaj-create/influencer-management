package com.influencers.socialMediainfluencers.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditController {

    @Autowired
     PaymentService paymentService;
    @Autowired
    CreditService creditService;

    @PostMapping("/buyCredits")
    public ResponseEntity<String> buyCredits(@RequestBody PaymentRequestDto paymentRequestDto ){
   boolean paymentSuccess =  paymentService.processPayment(paymentRequestDto);

   if(paymentSuccess){
       creditService.addCredits(paymentRequestDto.getUserId(),paymentRequestDto.getCredit());
  return ResponseEntity.ok("Payment successful and credit added to your account");
  }else
    {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("payment failed");
    }
}
}

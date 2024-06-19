package com.cypherfund.bbn.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${app.payment.endpoint}")
public interface PaymentFeignClient {
//    @PostMapping ("/payment-api/payment/cryptocurrency/initiate-payment")
//    ApiResponse<PaymentResponse> payByCrypto(@RequestBody CryptoPaymentRequest cryptoPaymentRequest);
//
//    @PostMapping ("/payment-api/payment/mobile-wallet")
//    ApiResponse<PaymentResponse> payByMobile(@RequestBody MobilePaymentRequest cryptoPaymentRequest);
}

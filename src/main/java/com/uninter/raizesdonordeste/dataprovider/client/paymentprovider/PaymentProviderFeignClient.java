package com.uninter.raizesdonordeste.dataprovider.client.paymentprovider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-provider", url = "${payment.provider.url}")
public interface PaymentProviderFeignClient {

    @PostMapping("/v1/payments")
    void processPayment(@RequestBody PaymentProviderRequest request);

}

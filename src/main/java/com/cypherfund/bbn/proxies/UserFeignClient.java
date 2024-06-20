package com.cypherfund.bbn.proxies;

import com.cypherfund.bbn.dto.TUserDto;
import com.cypherfund.bbn.models.ApiResponse;
import com.cypherfund.bbn.models.DebitRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", url = "${app.user.endpoint}")
public interface UserFeignClient {
    @GetMapping("/user-api/users/id/{id}")
    ApiResponse<TUserDto> getUserById(@PathVariable String id);
    @PostMapping("/user-api/users/validate-token")
    ApiResponse<TUserDto> validateToken(@RequestBody String token);

    @PostMapping("/user-api/account/play")
    ApiResponse<String> play(@RequestBody DebitRequest debitRequest);

    @PostMapping("/user-api/account/winning")
    ApiResponse<String> creditWinning(@RequestParam("amount") double amount,
                                      @RequestParam("userId") String userId,
                                      @RequestParam("reference") String reference);
}

package com.cypherfund.bbn.proxies;

import com.cypherfund.bbn.dto.TUserDto;
import com.cypherfund.bbn.models.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${app.user.endpoint}")
public interface UserFeignClient {
    @GetMapping("/user-api/users/id/{id}")
    ApiResponse<TUserDto> getUserById(@PathVariable String id);
    @PostMapping("/user-api/users/validate-token")
    ApiResponse<TUserDto> validateToken(@RequestBody String token);
}

package com.cypherfund.bbn.dto;

import com.cypherfund.bbn.utils.Enumerations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TUserDto implements Serializable {
    @Size(max = 100)
    private String imgUrl;
    @Size(max = 50)
    String userId;
    @Size(max = 255)
    String name;
    @NotNull
    @Size(max = 255)
    String username;
    @Size(max = 15)
    String phone;
    @Size(max = 15)
    Enumerations.USER_STATUS status;
    Instant dtCreated;
    Instant dtUpdated;
    @Size(max = 40)
    String email;
    List<String> roles;
    @Size(max = 15)
    String strLoginProvider;
}

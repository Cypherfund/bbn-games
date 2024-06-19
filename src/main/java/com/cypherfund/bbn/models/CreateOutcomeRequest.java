package com.cypherfund.bbn.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Author: E.Ngai
 * Date: 6/19/2024
 * Time: 8:05 PM
 **/
@Data
public class CreateOutcomeRequest {
    @NotEmpty
    List<Integer> houseIds;
    @NotNull
    Integer eventId;
    double odds;
}

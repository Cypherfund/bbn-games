package com.cypherfund.bbn.models;

import com.cypherfund.bbn.utils.Enumerations;
import lombok.Data;

@Data
public class DebitRequest {
    String userId;
    double amount;
    String reference;
    Enumerations.TRANSACTION_TYPE transactionType;



}

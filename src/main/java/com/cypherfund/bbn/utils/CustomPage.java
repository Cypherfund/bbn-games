package com.cypherfund.bbn.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomPage {
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
}

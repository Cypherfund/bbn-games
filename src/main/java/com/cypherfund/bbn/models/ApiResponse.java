package com.cypherfund.bbn.models;

import com.cypherfund.bbn.utils.CustomPage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private CustomPage page;
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data, null);
    }

    //mostly used for post requests
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    //mostly used for requests that return a page
    public static <T> ApiResponse<T> success(CustomPage page, T data) {
        return new ApiResponse<>(true, null, data, page);
    }

    public static CustomPage buildPage(Page page) {
        return new CustomPage(page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, null);
    }
}

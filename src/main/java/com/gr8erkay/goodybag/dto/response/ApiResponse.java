package com.gr8erkay.goodybag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //Getter, Setter, RequiredArgsConst, ToString, Hash and Equals
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private String statusCode;
    private String message;
    private T data;
    private final LocalDateTime time = LocalDateTime.now();
}

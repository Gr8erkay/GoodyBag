package com.gr8erkay.goodybag.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String phoneNumber;

    private Double accountBalance;

    public UserResponseDto(String firstName, String lastName, String userName, String email, String phoneNumber) {
    }
}

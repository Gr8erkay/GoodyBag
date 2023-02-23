package com.gr8erkay.goodybag.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "firstname is required")
    @Column(nullable = false, length = 50)
    private String firstname;

    @NotBlank(message = "lastname is required")
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "username is required")
    @Column(nullable = false, length = 50)
    private String userName;

    @NotBlank(message = "email is required")
    @Column(nullable = false, length = 50)
    private String email;

    @NotBlank(message = "phoneNumber is required")
    @Min(11)
    @Max(11)
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Column(nullable = false, length = 50)
    private String password;

    @NotBlank(message = "enter password again")
    @Column(nullable = false, length = 50)
    private String confirmPassword;

}

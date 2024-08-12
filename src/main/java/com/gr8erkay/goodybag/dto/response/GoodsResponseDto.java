package com.gr8erkay.goodybag.dto.response;

import com.gr8erkay.goodybag.enums.Category;
import com.gr8erkay.goodybag.enums.Status;
import com.gr8erkay.goodybag.model.User;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsResponseDto {

    private String title;

    private String description;

    private Integer quantity;

    private Double unitPrice;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Category category;

    private Double totalPrice;

    private String userName;

}

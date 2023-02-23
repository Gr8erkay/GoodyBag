package com.gr8erkay.goodybag.dto.response;

import com.gr8erkay.goodybag.enums.Category;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsResponseDto {

    private String title;

    private String description;

    private Integer quantity;

    private Double unitPrice;

    private Category category;

    private Double totalPrice;

    public GoodsResponseDto(String title, String description, Integer quantity, Double price, Category category) {
    }
}

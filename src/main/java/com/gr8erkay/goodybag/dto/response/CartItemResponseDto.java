package com.gr8erkay.goodybag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long goodsId;
    private String goodsTitle;
    private double price;
    private int quantity;

}

package com.gr8erkay.goodybag.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long cartId;
    private Double totalCost;
    private String message;
    private List<CartItemResponseDto> items;
}

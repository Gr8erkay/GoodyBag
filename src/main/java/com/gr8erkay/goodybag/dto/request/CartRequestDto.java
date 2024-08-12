package com.gr8erkay.goodybag.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    private Long userId;     // ID of the user
    private Long cartId;     // ID of the specific cart to operate on
    private Long goodsId;    // ID of the goods to add/remove
    private int quantity;    // Quantity to add/remove

}

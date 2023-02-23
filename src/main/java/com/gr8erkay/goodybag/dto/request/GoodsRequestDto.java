package com.gr8erkay.goodybag.dto.request;

import com.gr8erkay.goodybag.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GoodsRequestDto {

    @NotBlank(message = "enter goods title")
    private String title;

    @NotBlank(message = "enter goods description")
    private String description;

    @NotNull(message = "enter 0 if quantity is not available")
    private Integer quantity;

    @NotNull(message = "enter the unit price of goods")
    private Double unitPrice;

    @NotNull(message = "enter the category of goods")
    private Category category;

    private Integer purchaseQuantity;
}

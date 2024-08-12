package com.gr8erkay.goodybag.dto.request;

import com.gr8erkay.goodybag.enums.Category;
import com.gr8erkay.goodybag.enums.Status;
import com.gr8erkay.goodybag.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Persistable;

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

    @NotNull(message = "select status")
    private Status status;

    @NotNull(message = "enter the category of goods")
    private Category category;

    private Integer purchaseQuantity;

    private Long userId;
}

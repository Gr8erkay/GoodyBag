package com.gr8erkay.goodybag.service;

import com.gr8erkay.goodybag.dto.request.CartRequestDto;
import com.gr8erkay.goodybag.dto.response.CartResponseDto;

public interface CartService {

    CartResponseDto addGoods(CartRequestDto request);
    CartResponseDto removeGoods(CartRequestDto request);

    CartResponseDto checkoutCart(CartRequestDto request);

}

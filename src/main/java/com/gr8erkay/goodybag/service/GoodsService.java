package com.gr8erkay.goodybag.service;

import com.gr8erkay.goodybag.dto.request.GoodsRequestDto;
import com.gr8erkay.goodybag.dto.request.UserRequestDto;
import com.gr8erkay.goodybag.dto.response.GoodsResponseDto;
import com.gr8erkay.goodybag.dto.response.UserResponseDto;
import com.gr8erkay.goodybag.enums.Category;

import java.util.List;

public interface GoodsService {

    GoodsResponseDto createGoods(GoodsRequestDto request);

    GoodsResponseDto fetchGoodsById(Long goodsId);

    GoodsResponseDto buyGoods(Long goodsId, GoodsRequestDto goodsRequest, UserRequestDto userRequest);

    List<GoodsResponseDto> findAll();


    List<GoodsResponseDto> fetchAllGoodsByCategory(Category category);

    List<GoodsResponseDto> fetchAllGoodsByUserId(Long userId);

    GoodsResponseDto updateGoods(Long userId, GoodsRequestDto request);

    void deleteGoods(Long goodsId);
}

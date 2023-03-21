package com.gr8erkay.goodybag.controller;


import com.gr8erkay.goodybag.dto.request.GoodsRequestDto;
import com.gr8erkay.goodybag.dto.response.ApiResponse;
import com.gr8erkay.goodybag.dto.response.GoodsResponse;
import com.gr8erkay.goodybag.dto.response.GoodsResponseDto;
import com.gr8erkay.goodybag.enums.AppConstants;
import com.gr8erkay.goodybag.enums.Category;
import com.gr8erkay.goodybag.repository.GoodsRepository;
import com.gr8erkay.goodybag.service.GoodsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsRepository goodsRepository;

    private final GoodsService goodsService;

    @PostMapping(path = "/goods/create")
    public ResponseEntity<ApiResponse<GoodsResponseDto>> createGoods(@RequestBody @Valid GoodsRequestDto request){
        log.info("create goods call for title: {}", request.getTitle());
        GoodsResponseDto response =  goodsService.createGoods(request);

        //Using constructor
        ApiResponse<GoodsResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(response);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user created successfully");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "{goodsId}/goods/details")
    public ResponseEntity<Object> fetchGoodsById(@PathVariable Long goodsId) {
        GoodsResponseDto goodsFound = goodsService.fetchGoodsById(goodsId);

        ApiResponse<GoodsResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(goodsFound);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user fetchAllGoodsByUserId");

        return ResponseEntity.status(200).body(apiResponse);
    }

    @GetMapping(path = "{category}/goods/details")
    public ResponseEntity<Object> fetchUserByCategory(@PathVariable Category category) {
        GoodsResponseDto listOfGoods = (GoodsResponseDto) goodsService.fetchAllGoodsByCategory(category);

        ApiResponse<GoodsResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(listOfGoods);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("user fetchAllGoodsByUserId");

        return ResponseEntity.status(200).body(apiResponse);
    }

    @GetMapping(path = "{userId}/goods/details")
    public ResponseEntity<Object> fetchAllGoodsByUserId(@PathVariable Long userId) {
        GoodsResponseDto listOfGoods = (GoodsResponseDto) goodsService.fetchAllGoodsByUserId(userId);

        ApiResponse<GoodsResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setData(listOfGoods);
        apiResponse.setStatusCode("00");
        apiResponse.setMessage("All goods by this user");

        return ResponseEntity.status(200).body(apiResponse);
    }
    @GetMapping
    public GoodsResponse fetchAllGoods(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){


        return goodsService.fetchAllGoods(pageNo, pageSize,sortBy, sortDir);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GoodsResponseDto>> searchGoods(@RequestParam("text") String text) {
        return ResponseEntity.ok(goodsService.searchGoods(text));
    }
}

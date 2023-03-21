package com.gr8erkay.goodybag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResponse {

    private List<GoodsResponseDto> content;
    private int pageNo;

    private int pageSize;

    private long totalElement;

    private int totalPages;

    private boolean last;
}

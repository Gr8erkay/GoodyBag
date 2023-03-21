package com.gr8erkay.goodybag.service.serviceimplentaion;

import com.gr8erkay.goodybag.dto.request.GoodsRequestDto;
import com.gr8erkay.goodybag.dto.request.UserRequestDto;
import com.gr8erkay.goodybag.dto.response.GoodsResponse;
import com.gr8erkay.goodybag.dto.response.GoodsResponseDto;
import com.gr8erkay.goodybag.enums.Category;
import com.gr8erkay.goodybag.model.Goods;
import com.gr8erkay.goodybag.model.User;
import com.gr8erkay.goodybag.repository.GoodsRepository;
import com.gr8erkay.goodybag.repository.UserRepository;
import com.gr8erkay.goodybag.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;


    @Override
    public GoodsResponseDto createGoods(GoodsRequestDto request) {
        log.info("service:: about setting");
        Goods goods = new Goods();
        goods.setTitle(request.getTitle());
        goods.setDescription(request.getDescription());
        goods.setQuantity(request.getQuantity());
        goods.setPrice(request.getUnitPrice());
        goods.setCategory(request.getCategory());
        goods.setCreatedAt(LocalDateTime.now());
        goods.setUpdatedAt(LocalDateTime.now());

        log.info("about saving");
        Goods saveGoods = goodsRepository.save(goods);
        log.info("saved goods");
        return new GoodsResponseDto(saveGoods.getTitle(),saveGoods.getDescription(),
                saveGoods.getQuantity(), saveGoods.getPrice(),
                saveGoods.getCategory());
    }

    @Override
    public GoodsResponseDto fetchGoodsById(Long goodsId) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        if (goods.isPresent()){
            Goods g = goods.get();
            goodsResponseDto.setTitle(g.getTitle());
            goodsResponseDto.setDescription(g.getDescription());
            goodsResponseDto.setQuantity(g.getQuantity());
            goodsResponseDto.setUnitPrice(g.getPrice());
        }

        return goodsResponseDto;
    }
    @Override
    public GoodsResponseDto buyGoods(Long goodsId, GoodsRequestDto goodsRequest, UserRequestDto userRequest) {
        Optional<Goods> optionalGoods = goodsRepository.findById(goodsId);
        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();

        if (optionalGoods.isPresent()) {
            Goods goods = optionalGoods.get();
            int availableQuantity = goods.getQuantity();
            int purchaseQuantity = goodsRequest.getPurchaseQuantity();

            if (availableQuantity >= purchaseQuantity) {
                Optional<User> optionalSeller = userRepository.findById(goods.getUserId());
                Optional<User> optionalBuyer = userRepository.findUserByUserName(userRequest.getUserName());

                if (optionalSeller.isPresent() && optionalBuyer.isPresent()) {
                    User seller = optionalSeller.get();
                    User buyer = optionalBuyer.get();

                    // Calculate purchase cost and update account balances
                    double purchaseCost = purchaseQuantity * goods.getPrice();
                    seller.setAccountBalance(seller.getAccountBalance() + purchaseCost);
                    buyer.setAccountBalance(buyer.getAccountBalance() - purchaseCost);

                    // Save changes to the repository
                    userRepository.save(seller);
                    userRepository.save(buyer);

                    // Reduce available quantity and update goods entity
                    goods.setQuantity(availableQuantity - purchaseQuantity);
                    goodsRepository.save(goods);

                    // Set the response properties
                    goodsResponseDto.setTitle(goods.getTitle());
                    goodsResponseDto.setDescription(goods.getDescription());
                    goodsResponseDto.setQuantity(purchaseQuantity);
                    goodsResponseDto.setTotalPrice(purchaseCost);
                    goodsResponseDto.setUnitPrice(goods.getPrice());
                }
            }
        }

        return goodsResponseDto;
    }


    @Override
    public GoodsResponse fetchAllGoods(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //Get content for page object
        Page<Goods> goodsList = goodsRepository.findAll(pageable);

        List<Goods> listOfGoods = goodsList.getContent();
        List<GoodsResponseDto> content = listOfGoods.stream().map(this::mapToDto).collect(Collectors.toList());
        GoodsResponse goodsResponse = new GoodsResponse();
        goodsResponse.setContent(content);
        goodsResponse.setPageNo(goodsList.getNumber());
        goodsResponse.setPageSize(goodsList.getSize());
        goodsResponse.setTotalElement(goodsList.getTotalElements());
        goodsResponse.setLast(goodsList.isLast());
        return goodsResponse;
    }

    @Override
    public List<GoodsResponseDto> fetchAllGoodsByCategory(Category category) {
        List<Goods> goods = goodsRepository.findAllByCategory(category);
        return getGoodsResponseDto(goods);
    }

    private List<GoodsResponseDto> getGoodsResponseDto(List<Goods> goods1) {
        List<GoodsResponseDto> requests = new ArrayList<>();
        GoodsResponseDto goodsResponseDto = new GoodsResponseDto();
        for (Goods good : goods1) {
            goodsResponseDto.setTitle(good.getTitle());
            goodsResponseDto.setDescription(good.getDescription());
            goodsResponseDto.setQuantity(good.getQuantity());
            goodsResponseDto.setUnitPrice(good.getPrice());
            goodsResponseDto.setCategory(good.getCategory());
            requests.add(goodsResponseDto);
        }
        return requests;
    }
    public GoodsResponseDto mapToDto(Goods goods) {

        return GoodsResponseDto.builder()
                .title(goods.getTitle())
                .description(goods.getDescription())
                .quantity(goods.getQuantity())
                .unitPrice(goods.getPrice())
                .category(goods.getCategory())
                .build();
    }


    @Override
    public List<GoodsResponseDto> fetchAllGoodsByUserId(Long userId) {
        List<Goods> goods = goodsRepository.fetchAllGoodsByUserId(userId);
        return getGoodsResponseDto(goods);
    }

    @Override
    public GoodsResponseDto updateGoods(Long goodsId, GoodsRequestDto request) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        Goods goods1 = new Goods();
        if (goods.isPresent()){
            goods1 = goods.get();
            goods1.setTitle(request.getTitle());
            goods1.setDescription(request.getDescription());
            goods1.setPrice(request.getUnitPrice());
            goods1.setQuantity(request.getQuantity());
            goods1.setCategory(request.getCategory());
            goods1.setUpdatedAt(LocalDateTime.now());
        }
        Goods updatedGoods = goodsRepository.save(goods1);

        return new GoodsResponseDto(updatedGoods.getTitle(),updatedGoods.getDescription(),
                updatedGoods.getQuantity(),updatedGoods.getPrice(), updatedGoods.getCategory());
    }

    @Override
    public void deleteGoods(Long goodsId) {
        Optional<Goods> goods = goodsRepository.findById(goodsId);
        if (goods.isPresent()){
            Goods goods1 = goods.get();
            goodsRepository.delete(goods1);
        }
    }
}

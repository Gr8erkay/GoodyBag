package com.gr8erkay.goodybag.repository;

import com.gr8erkay.goodybag.enums.Category;
import com.gr8erkay.goodybag.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
//    List<Goods> fetchAllGoodsByCategory(Category category);

    List<Goods> findAllByCategory(Category category);


    @Query(value = "SELECT * from Goods g WHERE g.userId = ?", nativeQuery = true)
    List<Goods> fetchAllGoodsByUserId(Long userId);

    List<Goods> findAllByUserId(Long userId);

    List<Goods> findAll();
}

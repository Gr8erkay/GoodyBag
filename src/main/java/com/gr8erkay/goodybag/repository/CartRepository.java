package com.gr8erkay.goodybag.repository;

import com.gr8erkay.goodybag.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

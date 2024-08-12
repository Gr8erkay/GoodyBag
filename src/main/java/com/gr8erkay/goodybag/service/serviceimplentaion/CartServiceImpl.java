package com.gr8erkay.goodybag.service.serviceimplentaion;

import com.gr8erkay.goodybag.dto.request.CartRequestDto;
import com.gr8erkay.goodybag.dto.response.CartItemResponseDto;
import com.gr8erkay.goodybag.dto.response.CartResponseDto;
import com.gr8erkay.goodybag.model.Cart;
import com.gr8erkay.goodybag.model.CartItem;
import com.gr8erkay.goodybag.model.Goods;
import com.gr8erkay.goodybag.model.User;
import com.gr8erkay.goodybag.repository.CartRepository;
import com.gr8erkay.goodybag.repository.GoodsRepository;
import com.gr8erkay.goodybag.repository.UserRepository;
import com.gr8erkay.goodybag.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;

    @Override
    public CartResponseDto addGoods(CartRequestDto request) {
        // Fetch the user to verify existence
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the goods to verify existence
        Goods goods = goodsRepository.findById(request.getGoodsId())
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        // Fetch the specific cart using the cartId provided in the request
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find if the item already exists in the cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getGoods().getId().equals(goods.getId()))
                .findFirst()
                .orElse(null);

        // If the item does not exist, create a new CartItem
        if (existingItem == null) {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setGoods(goods);
            newItem.setQuantity(request.getQuantity());
            cart.getItems().add(newItem);
        } else {
            // If the item exists, just update the quantity
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        }

        // Save the updated cart
        cartRepository.save(cart);

        // Return the updated cart details
        return mapCartToCartResponseDto(cart);
    }


    @Override
    public CartResponseDto removeGoods(CartRequestDto request) {
        // Fetch the user to verify existence
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the specific cart using the cartId provided in the request
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Find the item in the cart
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getGoods().getId().equals(request.getGoodsId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        // Check if the quantity to remove is less than the current quantity
        if (itemToRemove.getQuantity() > request.getQuantity()) {
            itemToRemove.setQuantity(itemToRemove.getQuantity() - request.getQuantity());
        } else {
            // If the quantity to remove is the entire quantity or more, remove the item from the cart
            cart.getItems().remove(itemToRemove);
        }

        // Save the updated cart
        cartRepository.save(cart);

        // Return the updated cart details
        return mapCartToCartResponseDto(cart);
    }

    private CartResponseDto mapCartToCartResponseDto(Cart cart) {
        CartResponseDto response = new CartResponseDto();
        response.setCartId(cart.getId());

        // Check if the cart and items list are not null and not empty
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            List<CartItemResponseDto> items = cart.getItems().stream()
                    .map(this::mapCartItemToCartItemResponseDto)
                    .collect(Collectors.toList());
            response.setItems(items);
        } else {
            // Handle empty or null items list
            response.setItems(Collections.emptyList());
        }

        return response;
    }

    private CartItemResponseDto mapCartItemToCartItemResponseDto(CartItem item) {
        if (item == null) {
            return null;  // Optionally, handle this case based on your application's requirements
        }

        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(item.getId()); // Set the ID of the cart item
        dto.setGoodsId(item.getGoods().getId()); // Set the ID of the goods from the cart item
        dto.setGoodsTitle(item.getGoods().getTitle()); // Set the title of the goods
        dto.setPrice(item.getGoods().getPrice()); // Set the price of the goods
        dto.setQuantity(item.getQuantity()); // Set the quantity of the goods in the cart

        return dto;
    }

    @Override
    public CartResponseDto checkoutCart(CartRequestDto request) {
        // Fetch the user to ensure they exist
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the specific cart using the cartId provided in the request
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Check if the cart belongs to the user
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Cart does not belong to the user");
        }

        // Ensure the cart is not empty before proceeding
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot checkout an empty cart");
        }

        // Process each item in the cart (e.g., reduce stock, calculate total cost)
        double total = 0.0;
        for (CartItem item : cart.getItems()) {
            Goods goods = item.getGoods();
            if (goods.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for item: " + goods.getTitle());
            }
            // Reduce the stock
            goods.setQuantity(goods.getQuantity() - item.getQuantity());
            goodsRepository.save(goods);

            // Calculate total cost
            total += item.getQuantity() * goods.getPrice();
        }

        // Initialize payment service and process payment
        try {
            PaymentServiceImpl paymentService = new PaymentServiceImpl();
            JSONObject paymentResult = paymentService.initializePayment(total, user.getEmail());
            if (!paymentResult.getBoolean("status")) {
                throw new RuntimeException("Payment initialization failed: " + paymentResult.getString("message"));
            }
            // Assume you get a reference from the payment result to verify
            String reference = paymentResult.getJSONObject("data").getString("reference");
            JSONObject verificationResult = paymentService.verifyPayment(reference);
            if (!verificationResult.getJSONObject("data").getBoolean("status")) {
                throw new RuntimeException("Payment verification failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Payment processing error: " + e.getMessage(), e);
        }

        // Clear the cart items after successful payment
        cart.getItems().clear();
        cartRepository.save(cart);

        // Create response with the total and a success message
        CartResponseDto response = new CartResponseDto();
        response.setTotalCost(total);
        response.setMessage("Checkout successful. Total cost: " + total);
        return response;
    }
}
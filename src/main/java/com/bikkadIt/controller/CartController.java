package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.AddItemToCartREquest;
import com.bikkadIt.dto.CartDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.service.CartServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartServiceI cartServiceI;

    @PostMapping("/userId/{userId}")
    public ResponseEntity<CartDto>addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartREquest request){
        CartDto cartDto = this.cartServiceI.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/userId/{userId}/itemId/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId) {
        cartServiceI.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder().message(AppConstant.DELETE)
                .status(true)
                .httpStatus(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<ApiResponse>clearAllItems(@PathVariable String userId){
        this.cartServiceI.clearCart(userId);
        ApiResponse response = ApiResponse.builder().message("Item is removed")
                .status(true)
                .httpStatus(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<CartDto>getCart(@PathVariable String userId){
        CartDto cartByUser = this.cartServiceI.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

}

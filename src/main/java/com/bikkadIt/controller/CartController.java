package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.AddItemToCartREquest;
import com.bikkadIt.dto.CartDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.service.CartServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartServiceI cartServiceI;

    /**
     * @author Rushikesh Hatkar
     * @apiNote For add item to cart with userId
     * @param userId
     * @param request
     * @return
     * @since 1.0 v
     */
    @PostMapping("/userId/{userId}")
    public ResponseEntity<CartDto>addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartREquest request){
        log.info("Enter the  request for add item to  cart with userId:{} ",userId);
        CartDto cartDto = this.cartServiceI.addItemToCart(userId, request);
        log.info("Completed the  request for add item to  cart with userId:{} ",userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For remove item from cart
     * @param userId
     * @param itemId
     * @return
     * @since 1.0 v
     */
    @DeleteMapping("/userId/{userId}/itemId/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId) {
        log.info("Enter the  request for remove item from cart with userId and itemId :{} :{} ",userId,itemId);
        cartServiceI.removeItemFromCart(userId, itemId);
        ApiResponse response = ApiResponse.builder().message(AppConstant.DELETE)
                .status(true)
                .httpStatus(HttpStatus.OK)
                .build();
        log.info("Completed the  request for remove item from cart with userId and itemId :{} :{} ",userId,itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For clear all items with userId
     * @param userId
     * @return
     * @since 1.0v
     */
    @DeleteMapping("/userId/{userId}")
    public ResponseEntity<ApiResponse>clearAllItems(@PathVariable String userId){
        log.info("Enter the  request for clear all items with userId:{} ",userId);
        this.cartServiceI.clearCart(userId);
        ApiResponse response = ApiResponse.builder().message("Item is removed")
                .status(true)
                .httpStatus(HttpStatus.OK)
                .build();
        log.info("Completed the  request for clear all items with userId:{} ",userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get cart with user id
     * @param userId
     * @return
     * @since 1.0v
     */
    @GetMapping("/userId/{userId}")
    public ResponseEntity<CartDto>getCart(@PathVariable String userId){
        log.info("Enter the  request for get cart with userId:{} ",userId);
        CartDto cartByUser = this.cartServiceI.getCartByUser(userId);
        log.info("Completed the  request for get cart with userId:{} ",userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

}

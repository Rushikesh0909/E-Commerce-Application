package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.AddItemToCartREquest;
import com.bikkadIt.dto.CartDto;
import com.bikkadIt.entity.Cart;
import com.bikkadIt.entity.CartItem;
import com.bikkadIt.entity.Product;
import com.bikkadIt.entity.User;
import com.bikkadIt.exception.BadRequest;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.repository.CartItemRepo;
import com.bikkadIt.repository.CartRepo;
import com.bikkadIt.repository.ProductRepo;
import com.bikkadIt.repository.UserRepo;
import com.bikkadIt.service.CartServiceI;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartServiceI {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;
    @Autowired
   private ProductRepo productRepo;

    private CartItemRepo cartItemRepo;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartREquest request) {
        log.info("Entering the Dao call for add items to cart With userId :{}",userId);
        String productId = request.getProductId();
        Integer quantity = request.getQuantity();
        if(quantity<=0){
            throw new BadRequest(AppConstant.NOT_VALID_QUANTITY);
        }
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+productId));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + userId));
        Cart cart = null;

        try{
            cart=cartRepo.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart=new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items= items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
            item.setQuantity(quantity);
            item.setTotalPrice(quantity*product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        if (!updated.get()){
            CartItem cartItem = CartItem.builder().quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }


        cart.setUser(user);
        Cart updatedCart = cartRepo.save(cart);
        log.info("Completed the Dao call for add items to cart With userId :{}",userId);
        return modelMapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer itemId) {
        log.info("Entering the Dao call for remove items from cart With userId and itemId :{} :{}",userId,itemId);
        CartItem cartItem1 = cartItemRepo.findById(itemId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + itemId));
        cartItemRepo.delete(cartItem1);
        log.info("Completed the Dao call for remove items from cart With userId and itemId :{} :{}",userId,itemId);

    }

    @Override
    public void clearCart(String userId) {
        log.info("Entering the Dao call for clear cart With userId :{}",userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + userId));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + userId));
        cart.getItems().clear();
        cartRepo.save(cart);
        log.info("Completed the Dao call for clear cart With userId :{}",userId);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        log.info("Entering the Dao call for get cart by user With userId :{}",userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+userId));

        Cart cart = this.cartRepo.findByUser(user).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND+userId));
        log.info("Completed the Dao call for get cart by user With userId :{}",userId);
        return modelMapper.map(cart,CartDto.class);

    }
}

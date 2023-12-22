package com.bikkadIt.service.impl;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.CreateOrderRequest;
import com.bikkadIt.dto.OrderDto;
import com.bikkadIt.entity.*;
import com.bikkadIt.exception.BadRequest;
import com.bikkadIt.exception.ResourseNotFoundException;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.payloads.helper;
import com.bikkadIt.repository.CartRepo;
import com.bikkadIt.repository.OrderRepo;
import com.bikkadIt.repository.UserRepo;
import com.bikkadIt.service.OrderServiceI;
import org.hibernate.resource.beans.internal.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderServiceI {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + userId));
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + cartId));

        List<CartItem> cartItems = cart.getItems();

        if(cartItems.size()<=0){
            throw new BadRequest("invalid number of items in cart");
        }
        Order order = Order.builder().billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStaus(orderDto.getPaymentStaus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem ->
        {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepo.save(cart);
        Order save = orderRepo.save(order);

        return this.modelMapper.map(save,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = this.orderRepo.findById(orderId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + orderId));
        this.orderRepo.delete(order);

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException(AppConstant.NOT_FOUND + userId));
        List<Order> orders = orderRepo.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
       Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> page = this.orderRepo.findAll(pageable);
        return helper.getPageableResponse(page,OrderDto.class);
    }
}

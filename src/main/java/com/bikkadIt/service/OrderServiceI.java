package com.bikkadIt.service;

import com.bikkadIt.dto.CreateOrderRequest;
import com.bikkadIt.dto.OrderDto;
import com.bikkadIt.payloads.PageableResponse;

import java.util.List;

public interface OrderServiceI {

    // create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    // remove order
    void removeOrder(String orderId);

    // get orders of users
    List<OrderDto>getOrdersOfUser(String userId);

    // get orders
    PageableResponse<OrderDto>getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
}

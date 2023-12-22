package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.CreateOrderRequest;
import com.bikkadIt.dto.OrderDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.OrderServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;

    @PostMapping("/")
    public ResponseEntity<OrderDto>cerateOrder(@RequestBody CreateOrderRequest request){
        OrderDto order = this.orderServiceI.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping ("/orderId/{orderId}")
    public ResponseEntity<ApiResponse>removeOrder(@PathVariable String orderId){
        this.orderServiceI.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(true).httpStatus(HttpStatus.OK)
                .message("order is removed").build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderDto>>getOrdersOfUser(@PathVariable String userId){
        List<OrderDto> ordersOfUser = this.orderServiceI.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse>getOrders(
                                                     @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstant.Order_SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "direction", defaultValue = AppConstant.ORDER_SORT_BY, required = false) String direction){
        PageableResponse<OrderDto> orders = this.orderServiceI.getOrders(pageNumber, pageSize, sortBy, direction);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}

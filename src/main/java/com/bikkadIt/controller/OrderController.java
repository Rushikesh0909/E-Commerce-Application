package com.bikkadIt.controller;

import com.bikkadIt.constants.AppConstant;
import com.bikkadIt.dto.CreateOrderRequest;
import com.bikkadIt.dto.OrderDto;
import com.bikkadIt.payloads.ApiResponse;
import com.bikkadIt.payloads.PageableResponse;
import com.bikkadIt.service.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderServiceI orderServiceI;

    /**
     * @author Rushikesh Hatkar
     * @apiNote For create orders
     * @param request
     * @return
     * @since 1.0v
     */
    @PostMapping("/")
    public ResponseEntity<OrderDto>cerateOrder(@RequestBody CreateOrderRequest request){
        log.info("Enter the  request for Create  the Order");
        OrderDto order = this.orderServiceI.createOrder(request);
        log.info("Completed the  request for Create  the Order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For remove order with orderId
     * @param orderId
     * @return
     * @since 1.0v
     */
    @DeleteMapping ("/orderId/{orderId}")
    public ResponseEntity<ApiResponse>removeOrder(@PathVariable String orderId){
        log.info("Enter the  request for remove  the order With orderId :{}", orderId);
        this.orderServiceI.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(true).httpStatus(HttpStatus.OK)
                .message("order is removed").build();
        log.info("Completed the  request for remove  the order With orderId :{}", orderId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get orders of user
     * @param userId
     * @return
     * @since 1.0v
     */
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<OrderDto>>getOrdersOfUser(@PathVariable String userId){
        log.info("Enter the  request for Get order of users With userId  :{}", userId);
        List<OrderDto> ordersOfUser = this.orderServiceI.getOrdersOfUser(userId);
        log.info("Completed the  request for Get  all order of users With userId  :{}", userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    /**
     * @author Rushikesh Hatkar
     * @apiNote For get Orders
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param direction
     * @return
     * @since 1.0v
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse>getOrders(
                                                     @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstant.Order_SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "direction", defaultValue = AppConstant.ORDER_SORT_BY, required = false) String direction){
        log.info("Enter the  request for Get all orders ");
        PageableResponse<OrderDto> orders = this.orderServiceI.getOrders(pageNumber, pageSize, sortBy, direction);
        log.info("Enter the  request for Get all orders ");
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}

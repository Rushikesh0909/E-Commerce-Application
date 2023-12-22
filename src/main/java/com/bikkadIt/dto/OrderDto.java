package com.bikkadIt.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;

    private  String orderStatus="PENDING";

    private String paymentStaus="NOTPAID";

    private  Integer orderAmount;

    private String billingName;

    private  String billingAddress;

    private String billingPhone;

    private Date orderedDate;

    private  Date deliveredDate;

    private List<OrderItemDto>itemDtos=new ArrayList<>();
}

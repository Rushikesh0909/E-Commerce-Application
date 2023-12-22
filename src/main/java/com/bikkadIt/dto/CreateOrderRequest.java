package com.bikkadIt.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    private String cartId;

    private String userId;

    private  String orderStatus="PENDING";

    private String paymentStaus="NOTPAID";

    private String billingName;

    private  String billingAddress;

    private String billingPhone;



}

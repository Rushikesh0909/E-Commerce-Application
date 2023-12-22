package com.bikkadIt.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;

    private  String orderStatus;

    private String paymentStaus;

    private  Integer orderAmount;

    private String billingName;

    private  String billingAddress;

    private String billingPhone;

    private Date orderedDate;

    private  Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem>orderItems=new ArrayList<>();
}

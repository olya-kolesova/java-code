package com.javacode.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaidOrderDto {
    private String item;
    private int quantity;
    private double price;
    private boolean paid;
}

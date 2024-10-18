package com.javacode.notifications.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShippedOrderDto {
    private String item;
    private int quantity;
    private double price;
    private boolean paid;
    private boolean shipped;

}

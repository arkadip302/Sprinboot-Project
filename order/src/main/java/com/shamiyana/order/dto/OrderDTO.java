package com.shamiyana.order.dto;

import com.shamiyana.order.utility.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Product ID is required")
    private String productSku;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Total price cannot be null")
    private Double totalPrice;

    @NotNull(message = "Total price cannot be null")
    private String bankName;

    private OrderStatus status = OrderStatus.PENDING;
}

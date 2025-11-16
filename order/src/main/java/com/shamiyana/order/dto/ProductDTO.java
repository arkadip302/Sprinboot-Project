package com.shamiyana.order.dto;

import com.shamiyana.order.utility.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String skuCode;
    private Category category;
}


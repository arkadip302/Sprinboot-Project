package com.shamiyana.product.DTO;

import com.shamiyana.product.utility.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be positive")
    private Double price;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Category is required")
    private Category category;

    private String skuCode;
}

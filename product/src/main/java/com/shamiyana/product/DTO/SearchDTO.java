package com.shamiyana.product.DTO;

import com.shamiyana.product.utility.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {

    private String name;          // Partial or full match
    private Category category;    // Enum - CLOTHING, MOBILES, EAPPLIANCES
    private Double minPrice;      // Filter: >= minPrice
    private Double maxPrice;      // Filter: <= maxPrice
    private Integer minQuantity;  // Filter: >= minQuantity
    private Integer maxQuantity;  // Filter: <= maxQuantity
    private String sku;           // Optional direct lookup by SKU

    // Pagination and sorting support
    private Integer page = 0;           // Default page number
    private Integer size = 10;          // Default page size
    private String sortBy = "name";     // Default sort field
    private String sortDirection = "ASC"; // ASC or DESC
}

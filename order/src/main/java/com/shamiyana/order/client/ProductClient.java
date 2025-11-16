package com.shamiyana.order.client;

import com.shamiyana.order.configuration.FeignClientConfig;
import com.shamiyana.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service",path = "/api/v1",configuration = FeignClientConfig.class)
public interface ProductClient {

    @GetMapping("/getAllProduct")
    List<ProductDTO> getAllProduct(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(defaultValue = "id") String sortBy);

    @GetMapping("/{sku}/stock")
    ProductDTO getStockDetails(@PathVariable String sku);

    @PutMapping("/{sku}/changeQuantity/{quantity}")
    void reduceQuantity(@PathVariable String sku, @PathVariable Integer quantity);

    @GetMapping("/getProductById/{sku}")
    public ProductDTO getAProductById(@PathVariable String sku);
}


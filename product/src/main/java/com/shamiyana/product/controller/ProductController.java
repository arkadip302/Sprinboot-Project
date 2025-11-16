package com.shamiyana.product.controller;

import com.shamiyana.product.DTO.ProductDTO;
import com.shamiyana.product.DTO.SearchDTO;
import com.shamiyana.product.entity.Product;
import com.shamiyana.product.repository.ProductRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/getAllProduct")
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(value = "page",defaultValue = "1")Integer page, @RequestParam(defaultValue = "id") String sortBy){
        return ResponseEntity.ok(productRepository.findAll(PageRequest.of(page,pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/getProductById/{sku}")
    public ResponseEntity<?> getAProductById(@PathVariable String sku){
        return ResponseEntity.ok(productRepository.findBySkuCode(sku));
    }

    @PostMapping("/addProducts")
    public ResponseEntity<?> addNewProductsType(@RequestBody @Valid List<ProductDTO> productDTOS){
        List<Product> products = new ArrayList<>();
        productDTOS.stream().forEach(e->{
            Product product = new Product();
            BeanUtils.copyProperties(e,product);
            product.setCategory(e.getCategory().name());
            products.add(product);
        });
        productRepository.saveAll(products);
        return ResponseEntity.ok("New Product Saved");
    }

    @GetMapping("/{sku}/stock")
    public ResponseEntity<?> getStockDetails(@PathVariable String sku ){
        Optional<Product> product = productRepository.findBySkuCode(sku);
        if(product.isPresent()){
           return ResponseEntity.ok(product.get().getQuantity());
        }else{
            return ResponseEntity.badRequest().body("Product Not Present");
        }
    }

    @PutMapping("/{sku}/changeQuantity/{quantity}")
    public ResponseEntity<?> reduceQuantity(@PathVariable String sku,@PathVariable Integer quantity ){
        Optional<Product> product = productRepository.findBySkuCode(sku);
        if(product.isEmpty()){
            return ResponseEntity.badRequest().body("Product Not Found");
        }

        if(quantity > 0){
            product.get().setQuantity(quantity + product.get().getQuantity());
            productRepository.save(product.get());
            return ResponseEntity.ok("Inventory Updated For "+sku);
        }else if(product.get().getQuantity() -  Math.abs( quantity)  >=0){
                product.get().setQuantity(product.get().getQuantity() -  Math.abs( quantity) );
                productRepository.save(product.get());
                return ResponseEntity.ok("Inventory Updated");
        }else{
            return ResponseEntity.badRequest().body("Quantity Not Adequate");
        }

    }

    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody SearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                searchDTO.getSortDirection().equalsIgnoreCase("ASC")
                        ? Sort.by(searchDTO.getSortBy()).ascending()
                        : Sort.by(searchDTO.getSortBy()).descending()
        );

        Page<Product> products = productRepository.searchProducts(searchDTO, pageable);
        return ResponseEntity.ok(products);
    }


}

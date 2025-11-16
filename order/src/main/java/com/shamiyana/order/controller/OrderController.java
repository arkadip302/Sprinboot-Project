package com.shamiyana.order.controller;

import com.shamiyana.order.client.ProductClient;
import com.shamiyana.order.client.UserBankClient;
import com.shamiyana.order.dto.OrderDTO;
import com.shamiyana.order.dto.TransactionDTO;
import com.shamiyana.order.entity.Order;
import com.shamiyana.order.repository.OrderRepository;
import com.shamiyana.order.utility.OrderStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private HashMap<Long,String> cashingProductValues = new HashMap<>();
    private HashMap<Long,String> cashingUserDetailsValues = new HashMap<>();

    @Value("${bank.id}")
    private Long bankId;

    @Autowired
    private  ProductClient productClient;

    @Autowired
    private UserBankClient userBankClient;

    @Autowired
    private String str;

    @Autowired
    private int fsfs;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(@RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, @RequestParam(name = "page",defaultValue = "0")Integer page, @RequestParam(defaultValue = "id") String sortBy){
        return ResponseEntity.ok(orderRepository.findAll(PageRequest.of(page,pageSize, Sort.by(sortBy))));
    }

    @GetMapping("/allOrders/{userId}")
    public ResponseEntity<?> getAllOrdersForAUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderRepository.findByUserId(userId));
    }

    @PostMapping("/place")
    @Transactional
    public ResponseEntity<?> placeOrder(@RequestBody @Valid OrderDTO orderRequest) {
        var product = productClient.getAProductById(orderRequest.getProductSku());

        double total = product.getPrice() * orderRequest.getQuantity();

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(orderRequest.getTotalPrice());
        transactionDTO.setSenderUserId(orderRequest.getUserId());
        transactionDTO.setSenderBank(orderRequest.getBankName());
        transactionDTO.setRecBank("Axis");
        transactionDTO.setRecUserId(bankId);
        String status = userBankClient.transaction(transactionDTO);
        if (!status.contains("Successful")) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Deduct product stock
        productClient.reduceQuantity(orderRequest.getProductSku(), orderRequest.getQuantity());

        // Save order
        Order order = new Order();
        order.setProductId(product.getId());
        order.setUserId(orderRequest.getUserId());

        
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(total);
        order.setStatus(OrderStatus.valueOf("CONFIRMED"));

        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderRepository.findById(id));
    }

    @GetMapping("/check")
    public int getjsjwpvhrwsibvrw(){
        return str.hashCode();
    }
    @GetMapping("/checke")
    public int getjsjwpvhrsdvwsibvrw(){
        return fsfs;
    }
}

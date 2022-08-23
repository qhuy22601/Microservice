package com.qhuy.orderms.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhuy.orderms.dto.ItemDTO;
import com.qhuy.orderms.dto.ProductDTO;
import com.qhuy.orderms.model.OrderItem;
import com.qhuy.orderms.model.OrderModel;
import com.qhuy.orderms.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderItemService {

    @Value("${orderline.topic.name}")
    private String orderLineTopic;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private WebClient.Builder webConfig;

    ObjectMapper om = new ObjectMapper();

    public List<OrderItem> getAll(){
        return orderItemRepo.findAll();
    }

    public OrderItem getById(String orderItemId){
        Optional<OrderItem> orderItemOpt = orderItemRepo.findById(orderItemId);
        if(orderItemOpt.isEmpty()){
            log.info("ko tim thay order nay");
            return null;
        }
        else{
            OrderItem orderItem = orderItemOpt.get();
            log.info("da thay order:" + orderItem);
            return orderItem;
        }
    }

    public OrderItem save(Long productId , Integer amount){
        ItemDTO item = webConfig.build().get()
                .uri("http://localhost:8082/api/product/get/{id}", productId)
                .retrieve()
                .bodyToMono(ItemDTO.class)
                .block();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(item.getName());
        productDTO.setPrice(item.getPrice());
        productDTO.setId(productId);
        OrderItem orderItem = new OrderItem();

        orderItem.setAmount(amount);
        try{
            String orderlineStr = om.writeValueAsString(orderItem);
            kafkaTemplate.send(orderLineTopic, orderlineStr);
            log.info("orderItem"+orderItem);
        }catch (Exception e){
            e.printStackTrace();
        }


        return orderItemRepo.save(orderItem);
    }
}

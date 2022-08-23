package com.qhuy.orderkafka.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.qhuy.orderkafka.model.OrderItem;
import com.qhuy.orderkafka.model.Product;
import com.qhuy.orderkafka.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class OrderItemNotiListener {



    @Autowired
    private OrderItemRepository orderItemRepo;

    ObjectMapper om = new ObjectMapper();


    @Autowired
    private WebClient.Builder webConfig;






    @KafkaListener(topics = {"orderLineTopic"}, groupId = "groupId")
    public void orderLineProcessing(String orderItemStr){


        try {
           OrderItem orderItem = om.readValue(orderItemStr, OrderItem.class);
            log.info("orderItem: "+orderItem);
            Product product = webConfig.build().get()
                    .uri("http://localhost:8082/api/product/get/{id}", orderItem.getProductDTO().getId())
                    .retrieve()
                    .bodyToFlux(Product.class)
                    .blockLast();



            if(orderItem.getProductDTO().getId().equals(product.getId()) && orderItem.getAmount()>product.getQuantity()){
                log.error("ko du san pham");
            } else if (orderItem.getProductDTO().getId().equals(product.getId()) && orderItem.getAmount()<product.getQuantity()) {
                log.info("dat hang thanh cong:" + orderItem.getAmount() + orderItem.getProductDTO().getName());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }






    }
}

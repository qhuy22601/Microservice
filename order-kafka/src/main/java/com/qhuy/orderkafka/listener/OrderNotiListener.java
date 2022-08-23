package com.qhuy.orderkafka.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhuy.orderkafka.dto.ProductDTO;
import com.qhuy.orderkafka.dto.TestProductDTO;
import com.qhuy.orderkafka.model.OrderItem;
import com.qhuy.orderkafka.model.OrderModel;
import com.qhuy.orderkafka.model.Product;
import com.qhuy.orderkafka.model.User;
import com.qhuy.orderkafka.repository.OrderItemRepository;
import com.qhuy.orderkafka.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.udp.UdpServer;

@Service
@Slf4j
public class OrderNotiListener {
//    @Value("${order.topic.name}")
//    private String topicName;


    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebClient.Builder webConfig;


    @KafkaListener(topics = "orderTopic", groupId = "groupId1")
    public void orderLineProcessing( String orderStr){
        try {

            OrderModel order = om.readValue(orderStr, OrderModel.class);

            User user = webConfig.build().get()
                    .uri("http://localhost:8081/api/user/get/{id}", order.getUserId())
                   // .header("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxaHV5MjI2MDFAZ21haWwuY29tIiwiaWF0IjoxNjYwNTUyODczLCJleHAiOjE2NjA2MzkyNzN9.C3tsoTHj7GdopDorg9MhahFGiQfaHYH95pwMM1vLLvjIaeSPhDrmrGJuTkBD4-Wy-yVv2KzzIST2r7hgDBX88w")
                    .retrieve()
                    .bodyToFlux(User.class)
                    .blockLast();

            if(order.getUserId().equals(user.getId()) && order.getTotalBill()> user.getBalance()){
                log.error("ko du tien mua");
            } else if (order.getUserId().equals(user.getId()) && order.getTotalBill()<= user.getBalance()) {
                log.info("da thanh toan:" + order.getTotalBill() +" so tien con lai" + (user.getBalance()-order.getTotalBill()));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}

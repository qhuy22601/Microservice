package com.qhuy.orderms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhuy.orderms.dto.ItemDTO;
import com.qhuy.orderms.dto.ProductDTO;
import com.qhuy.orderms.model.OrderItem;
import com.qhuy.orderms.model.OrderModel;
import com.qhuy.orderms.model.User;
import com.qhuy.orderms.repository.OrderItemRepository;
import com.qhuy.orderms.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import test.qhuy.common.dto.OrchestratorRequestDto;
import test.qhuy.common.dto.OrchestratorResponseDto;
import test.qhuy.common.dto.OrderRequestDto;
import test.qhuy.common.dto.OrderResponseDto;
import test.qhuy.common.event.OrderStatus;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Value("${order.topic.name}")
    private String topicName;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderItemRepository orderItemRepo;

    @Autowired
    private WebClient.Builder webConfig;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    private Sinks.Many<OrchestratorRequestDto> sink;




    ObjectMapper om = new ObjectMapper();


    public Mono<OrderModel> createOrder(OrderRequestDto orderRequestDto){
        return this.orderRepo.save(this.dtoToEntity(orderRequestDto))
                .doOnNext((e-> orderRequestDto.setOrderId(e.getId())))
                .doOnNext(e -> this.emitEvent(orderRequestDto));

    }

    public Flux<OrderResponseDto> all(){
        return this.orderRepo.findAll()
                .map(this::entityToDto);
    }


    public void emitEvent(OrderRequestDto orderRequestDto){
        this.sink.tryEmitNext(this.getOrchestratorRequestDto(orderRequestDto));
    }


    private OrderModel dtoToEntity(OrderRequestDto orderRequestDto){
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(orderRequestDto.getUserId());
        orderModel.setId(orderRequestDto.getOrderId());
        orderModel.setProductId(orderRequestDto.getProductId());
        orderModel.setOrderStatus(OrderStatus.ORDER_CREATED);

        ProductDTO productDTO=webConfig.build().get()
                .uri("http://localhost:8082/api/product/get/{id}", orderRequestDto.getProductId())
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();

        orderModel.setPrice(productDTO.getPrice());
        return orderModel;
    }



    public OrderResponseDto entityToDto(OrderModel orderModel){
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(orderModel.getId());
        dto.setUserId(orderModel.getUserId());
        dto.setProductId(orderModel.getProductId());
        dto.setAmount(orderModel.getPrice());
        dto.setStatus(orderModel.getOrderStatus());
        return dto;
    }

    public OrchestratorRequestDto getOrchestratorRequestDto(OrderRequestDto orderRequestDto){
        OrchestratorRequestDto orchestratorRequestDto = new OrchestratorRequestDto();
        orchestratorRequestDto.setOrderId(orderRequestDto.getOrderId());
        orchestratorRequestDto.setUserId(orderRequestDto.getUserId());
        orchestratorRequestDto.setProductId(orderRequestDto.getProductId());
        ProductDTO productDTO=webConfig.build().get()
                .uri("http://localhost:8082/api/product/get/{id}", orderRequestDto.getProductId())
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();
        orchestratorRequestDto.setTotalBill(productDTO.getPrice());
        return orchestratorRequestDto;

    }

//    public List<OrderModel> getAll() {
//        return orderRepo.findAll();
//    }

    public OrderModel getById(String orderId) {
        return orderRepo.findOrderModelById(orderId);
    }


//    @Transactional
//    public OrderModel orderPlace(String orderItemId, Long userId) {
//        Optional<OrderItem> orderItemOpt = orderItemRepo.findById(orderItemId);
//        OrderItem orderItem = orderItemOpt.get();
//
//        Double totalBill = orderItem.getAmount() * orderItem.getProductDTO().getPrice();
//
//
//        OrderModel orderModel = new OrderModel();
//        orderModel.setTotalBill(totalBill);
//        orderModel.setUserId(userId);
//        orderModel.setOrderItems(orderItem);
//        orderModel.setOrderItemId(orderItemId);
//
//
//
//
//        OrderModel order = orderRepo.save(orderModel);
//
//
//
////        String uri = UriComponentsBuilder.fromUriString("http://localhost:8082/api/product/reduce/{id}")
////
////                .toUriString();
//        webConfig.build().put()
//                .uri("http://localhost:8082/api/product/reduce/{orderId}", order.getId())
//                .retrieve()
//                .bodyToMono(ItemDTO.class)
//                .block();
//        orderItemRepo.deleteById(orderItemId);
//
//
//        User  user = webConfig.build().put()
//                .uri("http://localhost:8081/api/user/deduct/{id}/{bill}", order.getUserId(), order.getTotalBill())
//                .retrieve()
//                .bodyToMono(User.class)
//                .block();
//
//
//
//        try {
//
//            String orderStr = om.writeValueAsString(orderModel);
//            kafkaTemplate.send(topicName, orderStr);
//
//
//            String userStr = om.writeValueAsString(user);
//            kafkaTemplate.send(topicName, userStr);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return order;
//
//
//    }


}

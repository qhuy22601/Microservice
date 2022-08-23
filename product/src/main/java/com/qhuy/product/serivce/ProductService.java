package com.qhuy.product.serivce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qhuy.product.dto.OrderDTO;
import com.qhuy.product.dto.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import com.qhuy.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.qhuy.product.repository.ProductRepository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import test.qhuy.common.dto.ProductRequestDTO;
import test.qhuy.common.dto.ProductResponseDTO;
import test.qhuy.common.event.ProductStatus;

import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private WebClient.Builder webConfig;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${product.topic.name}")
    private String productTopic;

    @Autowired
    private ProductRepository productRepo;


    ObjectMapper om = new ObjectMapper();

    public List<ProductModel> getAll() {
        return productRepo.findAll();
    }


    public ProductModel getById(Long id) {
        Optional<ProductModel> productOpt = productRepo.findById(id);
        if (productOpt.isEmpty()) {
            log.info("ko tim thay san pham" + id);
            return null;
        } else {
            ProductModel product = productOpt.get();
//            try {
//                String proStr = om.writeValueAsString(product);
//                kafkaTemplate.send(proStr, productTopic);
//                } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
            return product;
        }
    }

    public ProductModel save(ProductModel product) {
        product = productRepo.save(product);

        return product;

    }


    public ProductModel reduceQuantity(String orderId) {

//        String uri = UriComponentsBuilder.fromUriString("http://localhost:8084/api/order/get/{userId}")
//                .queryParam("orderId", orderId)
//                .build()
//                .toUriString();
        OrderDTO orderDTO = webConfig.build().get()
                .uri("http://localhost:8084/api/order/get/{id}",orderId)
                .retrieve()
                .bodyToMono(OrderDTO.class)
                .block();

        log.info("order:"+orderDTO);


        OrderItemDTO orderItemDTO = webConfig.build().get()
                .uri("http://localhost:8084/api/orderitem/get/{id}", orderDTO.getOrderItemId())
                .retrieve()
                .bodyToMono(OrderItemDTO.class)
                .block();




        Optional<ProductModel> prodOpt = productRepo.findById(orderItemDTO.getProductDTO().getId());


        ProductModel productModel = prodOpt.get();
        productModel.setQuantity(productModel.getQuantity()-(int)((orderDTO.getTotalBill())/(orderItemDTO.getProductDTO().getPrice())));

        return productRepo.save(productModel);
    }


    public ProductResponseDTO deduct(ProductRequestDTO productRequestDTO){

        ProductModel productModel = productRepo.findById(productRequestDTO.getProductId()).get();

        int quantity = productModel.getQuantity();

        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setProductId(productRequestDTO.getProductId());
        responseDTO.setOrderId(productRequestDTO.getOrderId());
        responseDTO.setUserId(productRequestDTO.getUserId());
        responseDTO.setStatus(ProductStatus.UNAVAILABLE);
        if(quantity>0){
            responseDTO.setStatus(ProductStatus.AVAILABLE);
            productModel.setQuantity(productModel.getQuantity()-1);
            productRepo.save(productModel);
        }
        return responseDTO;

    }

}

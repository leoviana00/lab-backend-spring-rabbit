package viana.tech.io.orderms_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import viana.tech.io.orderms_api.entity.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {

}

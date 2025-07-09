package viana.tech.io.orderms_api.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import viana.tech.io.orderms_api.entity.OrderEntity;
import viana.tech.io.orderms_api.entity.OrderItem;
import viana.tech.io.orderms_api.listener.OrderCreatedListener;
import viana.tech.io.orderms_api.listener.dto.OrderCreatedEvent;
import viana.tech.io.orderms_api.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event){

        var entity = new OrderEntity();
        
        entity.setOrderId(event.codigoCliente());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItens(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);

    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

    }

    private List<OrderItem> getOrderItens(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(i -> new OrderItem(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}

package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiContorller;
import jpabook.jpashop.controller.OrderController;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<OrderApiContorller.OrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItems();
        return orders.stream()
                .map(o -> new OrderApiContorller.OrderDto(o))
                .collect(toList());
    }
}

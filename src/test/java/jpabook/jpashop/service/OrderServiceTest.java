package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() {
        Member member = createMember();

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order savedOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, savedOrder.getStatus());
        assertEquals(1, savedOrder.getOrderItems().size());
        assertEquals(20000, savedOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test()
    void 주문_수량초과() {
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 11;

        Assertions.assertThatThrownBy(() ->
                orderService.order(member.getId(), book.getId(), orderCount)
        ).isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문취소() {
        Member member = createMember();
        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus());
        assertEquals(10, book.getStockQuantity());
    }

    @Test
    void 상품주문_재고수량초과() {

    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}

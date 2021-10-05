package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울", "1", "1111"));
            em.persist(member);

            Book book = new Book();
            book.setName("JPA1 BOOK");
            book.setPrice(10000);
            book.setStockQuantity(100);
            em.persist(book);

            Book book1 = new Book();
            book1.setName("JPA2 BOOK");
            book1.setPrice(20000);
            book1.setStockQuantity(100);
            em.persist(book1);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 20000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("부산", "2", "2222"));
            em.persist(member);

            Book book = new Book();
            book.setName("SPRING1 BOOK");
            book.setPrice(10000);
            book.setStockQuantity(100);
            em.persist(book);

            Book book1 = new Book();
            book1.setName("SPRING2 BOOK");
            book1.setPrice(20000);
            book1.setStockQuantity(100);
            em.persist(book1);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book1, 20000, 1);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}

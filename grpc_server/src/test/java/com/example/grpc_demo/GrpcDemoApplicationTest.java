package com.example.grpc_demo;

import com.example.grpc_demo.model.Order;
import com.example.grpc_demo.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-07 16:05
 */
@SpringBootTest
class GrpcDemoApplicationTest {
    @Resource
    private OrderService orderService;
    private static final String ID = "595064783fa64599a0f121370578206e";
    @BeforeEach
    public void setOrderOne(){
        Order order = new Order();
        order.setOrderName("测试");
        order.setOrderId(ID);
        int orderInsert = orderService.saveOrder(order);
        Assertions.assertEquals(1, orderInsert);
    }
    @Test
    void saveOrder(){
        List<String> orderList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setOrderName("测试" + i);
            int orderInsert = orderService.saveOrder(order);
            orderList.add(order.getOrderId());
            Assertions.assertEquals(1, orderInsert);
        }
        orderService.removeBatchOrder(orderList);
    }

    @Test
    void listOrder(){
        List<Order> orderList = orderService.listOrder();
        Assertions.assertFalse(orderList.isEmpty());
    }


    @Test
    void getOrder() {
        Order orderNew = orderService.getOrder(ID);
        Assertions.assertNotNull(orderNew);
    }

    @Test
    void deleteOrder() {
        int orderInsert = orderService.deleteOrder(ID);
        Assertions.assertEquals(1, orderInsert);
    }

    @AfterEach
    void removeOrder() {
        int orderInsert = orderService.removeOrder(ID);
        Assertions.assertEquals(1, orderInsert);
    }
}
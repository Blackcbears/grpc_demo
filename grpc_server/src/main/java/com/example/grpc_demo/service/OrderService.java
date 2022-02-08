package com.example.grpc_demo.service;

import com.example.grpc_demo.model.Order;

import java.util.List;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-01-26 00:18
 */
public interface OrderService {

    /**
     * 保存订单
     *
     * @param order 订单
     * @return int
     */
    int saveOrder(Order order);

    /**
     * 订单列表
     *
     * @return {@link List}<{@link Order}>
     */
    List<Order> listOrder();

    /**
     * 得到订单
     *
     * @param id id
     * @return {@link Order}
     */
    Order getOrder(String id);

    /**
     * 删除订单
     *
     * @param id id
     * @return int
     */
    int deleteOrder(String id);

    /**
     * 删除订单
     *
     * @param id id
     * @return int
     */
    int removeOrder(String id);

    /**
     * 删除批处理命令
     *
     * @param ids id
     * @return int
     */
    int removeBatchOrder(List<String> ids);
}






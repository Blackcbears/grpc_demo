package com.example.grpc_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.example.grpc_demo.model.Order;
import com.example.grpc_demo.mapper.OrderMapper;
import com.example.grpc_demo.service.OrderService;

import java.util.List;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-01-26 00:18
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;


    @Override
    public int saveOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public List<Order> listOrder() {
        return orderMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    public Order getOrder(String id) {
        return orderMapper.selectList(new QueryWrapper<Order>().eq(Order.COL_ORDER_ID, id)).stream().findFirst().orElse(null);
    }

    @Override
    public int deleteOrder(String id) {
        Order order = new Order();
        order.setOrderId(id);
        order.setIsDeleted(true);
        return orderMapper.updateById(order);
    }

    @Override
    public int removeOrder(String id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public int removeBatchOrder(List<String> ids) {
        return orderMapper.deleteBatchIds(ids);
    }
}






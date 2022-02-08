package com.example.grpc_demo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.grpc_demo.constant.DataBaseConstants;
import com.example.grpc_demo.model.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-07 11:10
 */
@Mapper
@DS(DataBaseConstants.DATASOURCE_SHARDING)
public interface OrderMapper extends BaseMapper<Order> {
}
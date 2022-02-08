package com.example.grpc_demo.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-07 11:10
 */
@Data
@TableName(value = "t_order")
public class Order implements Serializable {
    @TableId(value = "order_id", type = IdType.ASSIGN_UUID)
    private String orderId;

    @TableField(value = "order_name")
    private String orderName;

    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean isDeleted;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_NAME = "order_name";

    public static final String COL_IS_DELETED = "is_deleted";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}
# grpc_demo
用于grpc演示

1. sharding-jdbc 5.0.0的读写分离与分库分表插件
2. dynamic-datasource的多库控制。
3. grpc流式数据接收。
4. Security-grpc校验。

## 一、 读写分离

```aidl
2022-02-08 14:59:23.127  INFO 1664 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLInsertStatement(setAssignment=Optional.empty, onDuplicateKeyColumns=Optional.empty)
2022-02-08 14:59:23.127  INFO 1664 --- [           main] ShardingSphere-SQL                       : Actual SQL: write-shardingmaster ::: INSERT INTO t_order1  ( order_id,
order_name,
is_deleted,
create_time )  VALUES  (?, ?, ?, ?) ::: [595064783fa64599a0f121370578206e, 测试, false, 2022-02-08T14:59:22.502]
2022-02-08 14:59:23.243  INFO 1664 --- [           main] ShardingSphere-SQL                       : Logic SQL: SELECT  order_id,order_name,is_deleted,create_time,update_time  FROM t_order
2022-02-08 14:59:23.243  INFO 1664 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLSelectStatement(limit=Optional.empty, lock=Optional.empty, window=Optional.empty)
2022-02-08 14:59:23.243  INFO 1664 --- [           main] ShardingSphere-SQL                       : Actual SQL: read-shardingslave0 ::: SELECT  order_id,order_name,is_deleted,create_time,update_time  FROM t_order1 ORDER BY order_id ASC 
2022-02-08 14:59:23.243  INFO 1664 --- [           main] ShardingSphere-SQL                       : Actual SQL: read-shardingslave1 ::: SELECT  order_id,order_name,is_deleted,create_time,update_time  FROM t_order2 ORDER BY order_id ASC 
2022-02-08 14:59:23.315  INFO 1664 --- [           main] ShardingSphere-SQL                       : Logic SQL: DELETE FROM t_order WHERE order_id=?
2022-02-08 14:59:23.315  INFO 1664 --- [           main] ShardingSphere-SQL                       : SQLStatement: MySQLDeleteStatement(orderBy=Optional.empty, limit=Optional.empty)
2022-02-08 14:59:23.315  INFO 1664 --- [           main] ShardingSphere-SQL                       : Actual SQL: write-shardingmaster ::: DELETE FROM t_order1 WHERE order_id=? ::: [595064783fa64599a0f121370578206e]
```
上述write-shardingmaster 写库。
read-shardingslave0 读库1.
read-shardingslave1 读库2.
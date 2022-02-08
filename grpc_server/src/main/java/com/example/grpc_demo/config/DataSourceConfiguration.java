package com.example.grpc_demo.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.example.grpc_demo.constant.DataBaseConstants;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-01-25 23:43
 */
@Configuration
public class DataSourceConfiguration {



    private final DynamicDataSourceProperties properties;

    private final Map<String, DataSource> dataSources;

    public DataSourceConfiguration(DynamicDataSourceProperties properties, @Lazy Map<String, DataSource> dataSources) {
        this.properties = properties;
        this.dataSources = dataSources;
    }



    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new AbstractDataSourceProvider() {

            @Override
            public Map<String, DataSource> loadDataSources() {
                ShardingSphereDataSource shardingDataSource = (ShardingSphereDataSource)dataSources.get("shardingSphereDataSource");
                Map<String, DataSource> dataSourceMap = new HashMap<>(16);
                dataSourceMap.put(DataBaseConstants.DATASOURCE_SHARDING, shardingDataSource);
                Map<String, DataSource> shardingInnerDataSources = shardingDataSource.getContextManager().getDataSourceMap(shardingDataSource.getSchemaName());
                dataSourceMap.putAll(shardingInnerDataSources);
                return dataSourceMap;
            }
        };
    }

    /**
     * 将动态数据源设置为首选的
     * 当spring存在多个数据源时, 自动注入的是首选的对象
     * 设置为主要的数据源之后，就可以支持shardingjdbc原生的配置方式了
     * 3.4.0版本及以上使用以下方式注入,老版本请阅读文档  进阶-手动注入多数据源
     * @return
     */
    @Primary
    @Bean
    public DataSource dataSource() {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
        dataSource.setP6spy(properties.getP6spy());
        dataSource.setSeata(properties.getSeata());
        return dataSource;
    }

}

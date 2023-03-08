package top.rectorlee.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description RabbitMQ连接工具类
 * @date 2023-03-07  20:45:41
 */
public class RabbitMQConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        // 创建连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // 设置RabbitMQ主机地址,默认是localhost
        connectionFactory.setHost("localhost");

        // 设置RabbitMQ主机端口,默认是5672
        connectionFactory.setPort(5672);

        // 设置虚拟机名,默认是"/"
        connectionFactory.setVirtualHost("/");

        // 设置连接密码,默认是guest
        connectionFactory.setPassword("guest");

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        return connection;
    }
}

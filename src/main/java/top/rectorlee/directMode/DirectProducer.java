package top.rectorlee.directMode;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息生产方
 * @date 2023-03-08  11:49:48
 */
public class DirectProducer {
    // 交换机名称
    public static final String DIRECT_EXCHANGE_NAME = "Direct-Exchange";

    // 路由key
    private static final String ROUTING_KEY = "direct-1";

    // 消息体
    private static final String MSG = "直连模型";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 发布消息
        channel.basicPublish(DIRECT_EXCHANGE_NAME, ROUTING_KEY, null, MSG.getBytes());
    }
}

package top.rectorlee.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息生产方
 * @date 2023-03-08  16:09:18
 */
public class TopicProducer {
    // 交换机名称
    public static final String TOPIC_EXCHANGE_NAME = "Topic-Exchange";

    // 消息体
    private static String MSG = "通配符模型";

    // 通配符
    private static String TOPIC = "topic.web.rabbitMQ";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 发布消息
        channel.basicPublish(TOPIC_EXCHANGE_NAME, TOPIC, null, MSG.getBytes());
    }
}

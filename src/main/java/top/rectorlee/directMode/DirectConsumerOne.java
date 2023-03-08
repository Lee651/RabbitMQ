package top.rectorlee.directMode;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Lee
 * @description 消息消费方
 * @date 2023-03-08  11:56:29
 */
public class DirectConsumerOne {
    // 队列名称
    private static final String DIRECT_QUEUE_NAME = "Direct-Queue-1";

    // 路由key
    private static final String ROUTING_KEY = "direct-1";

    // 消息体
    private static String MSG = "";

    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(DIRECT_QUEUE_NAME, true, false, false, null);

        // 声明交换机
        // channel.exchangeDeclare(DirectProducer.DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 将队列绑定到交换机上
        channel.queueBind(DIRECT_QUEUE_NAME, DirectProducer.DIRECT_EXCHANGE_NAME, ROUTING_KEY);

        // 消费消息
        channel.basicConsume(DIRECT_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) throws IOException {
                MSG = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者1消费的消息为: " + MSG);
            }
        });
    }
}

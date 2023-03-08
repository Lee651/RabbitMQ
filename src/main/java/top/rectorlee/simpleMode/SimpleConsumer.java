package top.rectorlee.simpleMode;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息消费者
 * @date 2023-03-07  18:40:58
 */
public class SimpleConsumer {
    // 队列名称
    private static final String SIMPLE_QUEUE_NAME = "Simple-Queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(SIMPLE_QUEUE_NAME, true, false, false, null);

        /**
         * 消费消息:
         * 参数1: 队列名
         * 参数2: 是否自动确认,true: 表示接收到消息后自动向MQ回复接收到了,MQ接收到回复后自动删除消息
         * 参数3: 消息接收到后回调
         */
        channel.basicConsume(SIMPLE_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties, byte[] bytes) throws UnsupportedEncodingException {
                // 设置路由
                String routingKey = envelope.getRoutingKey();
                System.out.println("路由key为: " + routingKey);

                // 获取交换机信息
                String exchange = envelope.getExchange();
                System.out.println("交换机信息为: " + exchange);

                // 获取消息id
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("消息id为: " + deliveryTag);

                // 获取消息信息
                String msg = new String(bytes, "utf-8");
                System.out.println("消息为: " + msg);
            }
        });
    }
}

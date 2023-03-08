package top.rectorlee.fanoutMode;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息消费者
 * @date 2023-03-08  1:43:14
 */
public class FanoutConsumerTwo {
    // 队列名称
    private static final String FANOUT_QUEUE_NAME = "Fanout-Queue-2";

    // 消息体
    private static String MSG;

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(FANOUT_QUEUE_NAME, true, false, false, null);

        /**
         * 将队列绑定到交换机上
         * 参数一: 队列名称
         * 参数二: 交换机名称
         * 参数三: 路由key,无关紧要的参数
         */
        channel.queueBind(FANOUT_QUEUE_NAME, FanoutProducer.FANOUT_EXCHANGE_NAME, "");

        // 消费消息
        channel.basicConsume(FANOUT_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) throws IOException {
                MSG = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者2消费的消息为: " + MSG);
            }
        });
    }
}

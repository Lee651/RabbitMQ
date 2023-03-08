package top.rectorlee.workMode;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息消费者
 * @date 2023-03-07  22:10:25
 */
public class WorkConsumerOne {
    // 队列名称
    private static final String QUEUE_NAME = "Work-Queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        aveConsumption();
    }

    /**
     * 平均消费队列中的消息
     */
    public static void aveConsumption() throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 消费消息
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) {
                String msg = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者1消费的消息为: " + msg);
            }
        });
    }
}

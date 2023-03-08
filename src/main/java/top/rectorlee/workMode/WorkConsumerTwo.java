package top.rectorlee.workMode;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息消费者
 * @date 2023-03-07  22:11:03
 */
public class WorkConsumerTwo {
    // 队列名称
    private static final String WORK_QUEUE_NAME = "Work-Queue";

    // 休眠时间
    private static final long SLEEP_TIME = 500;

    public static void main(String[] args) throws IOException, TimeoutException {
        // aveConsumption();
        nonAveConsumption();
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
        channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);

        // 消费消息
        channel.basicConsume(WORK_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) {
                String msg = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者2消费的消息为: " + msg);
            }
        });
    }

    /**
     * 非平均消费队列中的消息
     */
    public static void nonAveConsumption() throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 表示该消费者在接收到队列中的消息但没有返回确认结果之前,不会将新的消息分发给该消费者
        channel.basicQos(1);

        // 声明队列
        channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);

        // 每消费一条,手动确认一条
        channel.basicConsume(WORK_QUEUE_NAME, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) throws IOException {
                try {
                    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                String msg = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者2消费的消息为: " + msg);

                // 反馈消息的消费状态
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}

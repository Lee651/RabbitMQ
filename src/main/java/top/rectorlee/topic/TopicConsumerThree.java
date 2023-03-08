package top.rectorlee.topic;

import com.rabbitmq.client.*;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息消费方
 * @date 2023-03-08  16:31:26
 */
public class TopicConsumerThree {
    // 队列名称
    private static final String TOPIC_QUEUE_NAME = "Topic-Queue-3";

    // 消息体
    private static String MSG = "";

    // 通配符
    private static String Topic = "topic.web.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(TOPIC_QUEUE_NAME, true, false, false, null);

        // 生命交换机
        channel.exchangeDeclare(TopicProducer.TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 将队列绑定到交换机上
        channel.queueBind(TOPIC_QUEUE_NAME, TopicProducer.TOPIC_EXCHANGE_NAME, Topic);

        // 消费消息
        channel.basicConsume(TOPIC_QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] bytes) throws IOException {
                MSG = new String(bytes, StandardCharsets.UTF_8);

                System.out.println("消费者3消费的消息为: " + MSG);
            }
        });
    }
}

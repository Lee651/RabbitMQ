package top.rectorlee.workMode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息生产者
 * @date 2023-03-07  22:09:38
 */
public class WorkProducer {
    // 队列名称
    private static final String WORK_QUEUE_NAME = "Work-Queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);

        // 发布消息
        for (int i = 0; i < 20; i++) {
            String msg = "你好---" + i;

            channel.basicPublish("", WORK_QUEUE_NAME, null, msg.getBytes());
        }
    }
}

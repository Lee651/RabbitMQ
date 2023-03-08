package top.rectorlee.simpleMode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息生产者
 * @date 2023-03-07  18:28:19
 */
public class SimpleProducer {
    // 队列名称
    private static final String SIMPLE_QUEUE_NAME = "Simple-Queue";

    // 消息体
    private static final String MSG = "你好啊,靓仔！";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        /**
         * 声明队列:
         * 参数1: 队列名
         * 参数2: 是否持久化队列,设置为true表示持久化队列,那么一旦连接关闭,在RabbitMQ控制台仍然能看到该队列存在,否则该队列在RabbitMQ控制台就不存在了(默认设置为true)
         * 参数3: 是否独占本次连接,设置为true表示当前连接与队列绑定,无论队列是否持久化,一旦连接关闭,该队列在RabbitMQ控制台上就不存在了(默认设置为false)
         * 参数4: 是否自动删除队列,一旦队列中的消息被消费且消费端关闭连接了,队列就会被删除(默认设置为false)
         * 参数5: 队列其他参数
         */
        channel.queueDeclare(SIMPLE_QUEUE_NAME, true, false, false, null);

        /**
         * 发布消息:
         * 参数1: 交换机名,如果没有指定,则使用默认的Default Exchange(空字符串就是默认的交换机)
         * 参数2: 路由key,即队列名
         * 参数3: 其他消息属性
         * 参数4: 消息内容
         */
        channel.basicPublish("", SIMPLE_QUEUE_NAME, null, MSG.getBytes());

        // 释放资源
        // channel.close();
        // connection.close();
    }
}

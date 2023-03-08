package top.rectorlee.fanoutMode;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import top.rectorlee.util.RabbitMQConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Lee
 * @description 消息生产者
 * @date 2023-03-08  1:48:58
 */
public class FanoutProducer {
    // 交换机名称
    public static final String FANOUT_EXCHANGE_NAME = "Fanout-Exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        Connection connection = RabbitMQConnectionUtils.getConnection();

        // 创建频道
        Channel channel = connection.createChannel();

        /**
         * 声明交换机
         * 参数一: 交换机名称
         * 参数二: 交换机类型
         */
        channel.exchangeDeclare(FANOUT_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        // 发布消息
        for (int i = 0; i < 10; i++) {
            String msg = "你好--" + i;

            channel.basicPublish(FANOUT_EXCHANGE_NAME, "", null, msg.getBytes());
        }
    }
}

package com.larscheng.www.workqueues;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 * 消息发布者
 *
 * @author lars
 * @date 2019/7/24 11:35
 */
@Slf4j
public class Send {
    private final static String TASK_ROUTE_KEY = "d4";

    private final static String TASK_EXCHANGE = "Direct-test";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // 改成对应的密码
        factory.setPassword("songrui1234");
        // 改成对应的登录名
        factory.setUsername("songrui");
        // 改成对应的网址
        factory.setHost("songrui.vip");
      //  factory.setPort(5672);  //默认值是5672

        try (
                //创建链接、频道
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            //声明队列持久
            boolean durable = true;
            //指定一个队列(持久化队列只能在初始化时定义，已存在已创建的队列无效)
          //  声明队列 如果由队列就不用申请了
           channel.queueDeclare(TASK_ROUTE_KEY, durable, false, false, null);

            //发送10条消息，依次在消息后面附加1-10个点
            StringBuilder stringBuilder = new StringBuilder("workqueues");
            for (int i = 1; i <= 3; i++){
                stringBuilder.append(".");
                String msg = stringBuilder.toString()+i;


                //将消息标记为持久性
                AMQP.BasicProperties basicProperties = MessageProperties.PERSISTENT_TEXT_PLAIN;
                //向队列中发送消息
                channel.basicPublish("", TASK_ROUTE_KEY, basicProperties, msg.getBytes());
                log.info("发送消息：{} 到队列路由：{}", msg, TASK_ROUTE_KEY);
            }
        }
    }
}
/***
 * 上面的basicPublish 发送时
 *  当exchange是空字符串的时候   并且存在没有绑定的队列  还和routingKey 一致时 默认发送到同名的队列
 *
 *  当exchange 有数据时 不能发送信息到 和routingKey同名的队列
 *
 *
 */

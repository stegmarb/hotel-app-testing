package com.greenfox.model;

import com.rabbitmq.client.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Getter
@Setter
@NoArgsConstructor
public class Send {

  private URI rabbitMqUrl;
  private final static String QUEUE_NAME = "kryptonite";
  private static final String EXCHANGE_NAME = "log";

  public void send() throws Exception {
    try {
      rabbitMqUrl = new URI(System.getenv("RABBITMQ_BIGWIG_TX_URL"));
    } catch(URISyntaxException e) {
      e.getStackTrace();
    }
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitMqUrl.getHost());
    factory.setPort(rabbitMqUrl.getPort());
    factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    System.out.println(1111111111);
    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    System.out.println(222222222);
//    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    String message = "Hello World!";
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }

  public void consume() throws Exception {
    try {
      rabbitMqUrl = new URI(System.getenv("RABBITMQ_BIGWIG_RX_URL"));
    } catch(URISyntaxException e) {
      e.getStackTrace();
    }
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(rabbitMqUrl.getUserInfo().split(":")[0]);
    factory.setPassword(rabbitMqUrl.getUserInfo().split(":")[1]);
    factory.setHost(rabbitMqUrl.getHost());
    factory.setPort(rabbitMqUrl.getPort());
    factory.setVirtualHost(rabbitMqUrl.getPath().substring(1));
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    System.out.println(333333333);
//    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
    String queueName = channel.queueDeclare().getQueue();
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
    System.out.println(444444444);
//    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    System.out.println(666666666);
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
    System.out.println(555555555);
  }
}
package com.stackroute.favouriteservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {
    private String exchangeName = "user_exchange";
    private String registerQueue = "user_queue";
    private String recommendedQueue = "user_recommended_queue";

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Queue queueRegister() {
        return new Queue(registerQueue, false);
    }


    @Bean
    Queue queueRecommendedBook() {
        return new Queue(recommendedQueue, false);
    }

    @Bean
    Binding bindingUser(Queue queueRegister, DirectExchange exchange) {
        return BindingBuilder.bind(queueRegister()).to(exchange).with("user_routing");
    }

    @Bean
    Binding bindingRecommendedBook(Queue queueRecommendedBook, DirectExchange exchange) {
        return BindingBuilder.bind(queueRecommendedBook()).to(exchange).with("book_recommended_routing");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
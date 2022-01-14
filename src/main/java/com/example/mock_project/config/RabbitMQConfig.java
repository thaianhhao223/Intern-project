package com.example.mock_project.config;

import com.example.mock_project.entity.ClaimRequest;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String CLAIMREQUEST_EXCHANGE = "claimrequest-exchange";

    public static final String CLAIMREQUEST_CREATE_UPDATE = "claimrequest.create_update";

    public static final String CLAIMREQUEST_DELETE = "claimrequest.delete";

    @Bean
    public Declarables topicBindings() {
        Queue claimrequestQueueCreateAndUpdate = new Queue(CLAIMREQUEST_CREATE_UPDATE, false);
        Queue claimrequestQueueDelete = new Queue(CLAIMREQUEST_DELETE, false);
        TopicExchange topicExchange = new TopicExchange(CLAIMREQUEST_EXCHANGE);
        return new Declarables(
                claimrequestQueueCreateAndUpdate,
                claimrequestQueueDelete,
                topicExchange,
                bind(claimrequestQueueCreateAndUpdate).to(topicExchange).with(CLAIMREQUEST_CREATE_UPDATE),
                bind(claimrequestQueueDelete).to(topicExchange).with(CLAIMREQUEST_DELETE)
        );
    }
}

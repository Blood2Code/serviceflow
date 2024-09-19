package uz.devid.serviceflow.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    public static final String SERVICE_FLOW_REQUEST_QUEUE = "service.request.flow";
    public static final String SERVICE_FLOW_RESPONSE_QUEUE = "service.response.flow";
    public static final String SERVICE_FLOW_EXCHANGE = "service.flow.exchange";
    public static final String SERVICE_FLOW_ROUTING_REQUEST_KEY = "flowRequestService";
    public static final String SERVICE_FLOW_ROUTING_RESPONSE_KEY = "flowResponseService";


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue serviceFlowRequestQueue() {
        return new Queue(SERVICE_FLOW_REQUEST_QUEUE, true);
    }
    @Bean
    public Queue serviceFlowResponseQueue() {
        return new Queue(SERVICE_FLOW_RESPONSE_QUEUE, true);
    }

    @Bean
    public DirectExchange serviceFlowExchange() {
        return new DirectExchange(SERVICE_FLOW_EXCHANGE);
    }

    @Bean
    public Binding serviceFlowRequestBinding() {
        return BindingBuilder.bind(serviceFlowRequestQueue()).to(serviceFlowExchange()).with(SERVICE_FLOW_ROUTING_REQUEST_KEY);
    }

    @Bean
    public Binding serviceFlowResponseBinding() {
        return BindingBuilder.bind(serviceFlowResponseQueue()).to(serviceFlowExchange()).with(SERVICE_FLOW_ROUTING_RESPONSE_KEY);
    }

    @Bean
    public RabbitTemplate serviceRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}

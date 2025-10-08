    package tech.vitalis.caringu.config.exchange;

    import org.springframework.amqp.rabbit.connection.ConnectionFactory;
    import org.springframework.amqp.core.TopicExchange;
    import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
    import org.springframework.amqp.rabbit.core.RabbitTemplate;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
    import org.springframework.amqp.core.AcknowledgeMode;


    @Configuration
    public class RabbitMQConfig {

        @Bean
        public TopicExchange notificacoesExchange(@Value("${app.rabbitmq.exchange}") String exchange){
            return new TopicExchange(exchange, true, false);
        }

        @Bean
        public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
            RabbitTemplate template = new RabbitTemplate(connectionFactory);
            template.setMessageConverter(jackson2JsonMessageConverter());
            return template;
        }

        @Bean
        public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
                ConnectionFactory connectionFactory,
                Jackson2JsonMessageConverter messageConverter)
        {
            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(messageConverter); // ⭐ usar o mesmo conversor
            factory.setDefaultRequeueRejected(false);
            factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
            return factory;
        }

    }

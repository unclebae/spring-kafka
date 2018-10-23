package com.example.spring.kafka.springkafka.config;

import com.example.spring.kafka.springkafka.message.ReceiptInfo;
import com.example.spring.kafka.springkafka.message.UserInfo;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

/**
 * Kafka로 메시지를 전송하기 위한 팰토리와 템플릿을 정의한다.
 */
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.address}")
    private String address;

    /**
     * 스트링 기반의 메시지를 전송하기 위한 팰토리 설정
     * 이때 주키퍼 서버의 주소, 키 시리얼라이징 방식, 값 시리얼라이징 방식을 지정한다.
     * @return 생산자 팩토리를 전송한다.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final HashMap<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * KafkaTemplate 을 생성한다. 이때 사용하고자 하는 팩토리를 지정하게 된다.
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * 사용자 정보 객체를 전달한다. 이때 JSON 으로 시리얼라지 되도록 JsonSerializer 를 적용하고 있다.
     * @return 생산자 팩토리를 전송한다.
     */
    @Bean
    public DefaultKafkaProducerFactory receiptInfoProducerFactory() {
        final HashMap<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * KafkaTemplate을 생성한다. 사용자 정보를 전달하도록 지정했다.
     * @return
     */
    @Bean
    public KafkaTemplate<String, ReceiptInfo> receiptInfoKafkaTemplate() {
        return new KafkaTemplate<>(receiptInfoProducerFactory());
    }
}

package com.example.spring.kafka.springkafka.config;

import com.example.spring.kafka.springkafka.message.ReceiptInfo;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.address}")
    private String address;

    /**
     * 컨슈머 팩토리를 생성한다. 이때 그룹 아이디는 컨슈머를 그룹으로 지정하고, 동일한 그룹은 동일한 파티션만을 바라보게 처리할 수 있도록 한다.
     * @param groupId 지정하고자 하는 그룹 아이디
     * @return 컨슈머 팩토리
     */
    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        final HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * UserInfo 를 카프카로부터 받아와서 디시리얼라이징 한다.
     * @return 컨슈머 팩토리
     */
    public ConsumerFactory<String, ReceiptInfo> receiptInfoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "userInfo");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ReceiptInfo.class));
    }

    /**
     * 일반적인 스트링 기반의 디시리얼라이징 처리를 수행하며,
     * 그룹은 결과를 받도록 처리한다.
     * @return 리스너 팩토리 반환
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> resultKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory("result"));
        return factory;
    }

    /**
     * 사용자 정보를 수신받고 디시리얼라이징 한다.
     * @return 리스너 팩토리 반환
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReceiptInfo> userInfoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ReceiptInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(receiptInfoConsumerFactory());
        return factory;
    }

}

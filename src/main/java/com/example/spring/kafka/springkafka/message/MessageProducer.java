package com.example.spring.kafka.springkafka.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ReceiptInfo> receiptInfoKafkaTemplate;

    @Value(value = "${receipt.topic.name}")
    private String receiptTopicName;

    @Value(value = "${result.topic.name}")
    private String resultTopicName;

    @Value(value = "${partition.topic.name}")
    private String partitionTopicName;

    public void sendReceiptInfoMessage(ReceiptInfo receiptInfo) {
        log.info("Send ReceiptInfo to kafka queue {} ", receiptInfo);
        receiptInfoKafkaTemplate.send(receiptTopicName, receiptInfo);
    }

    public void sendResultMessage(String message) {
        log.info("Send ReceiptResult to kafka queue {} ", message);
        kafkaTemplate.send(resultTopicName, message);
    }

    public void sendLogMessateToPartition(String key, String message, int partition) {
        kafkaTemplate.send(partitionTopicName, partition, key, message);
    }

}

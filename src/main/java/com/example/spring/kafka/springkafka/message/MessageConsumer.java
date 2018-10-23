package com.example.spring.kafka.springkafka.message;

import com.example.spring.kafka.springkafka.processor.LoggingService;
import com.example.spring.kafka.springkafka.processor.UserInfoProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    @Autowired
    UserInfoProcessService userInfoProcessService;

    @Autowired
    MessageProducer messageProducer;

    @Autowired
    LoggingService loggingService;

    @KafkaListener(topics = "${receipt.topic.name}", containerFactory = "userInfoKafkaListenerContainerFactory")
    public void listenUserInfo(ReceiptInfo receiptInfo) {
        loggingService.logging("Received Messasge : " + receiptInfo);
        if (receiptInfo != null && receiptInfo.getReceiptId() != null) {
            receiptInfo.setReceiptCode(ReceiptCode.PROCESSING);
            userInfoProcessService.saveUserInfo(receiptInfo);

            loggingService.logging("Process Some Process :" + receiptInfo.getReceiptId());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            receiptInfo.setReceiptCode(ReceiptCode.RECEIPTED);
            userInfoProcessService.saveUserInfo(receiptInfo);

            messageProducer.sendResultMessage(String.valueOf(receiptInfo.getReceiptId()));

        }
        else {
            loggingService.logging("Not Valid User Info : " + receiptInfo);
            return;
        }


    }

    @KafkaListener(topics = "${result.topic.name}", groupId = "result", containerFactory = "resultKafkaListenerContainerFactory")
    public void listenGroupBar(String message) {
        loggingService.logging("Received Messasge in group 'result': " + message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${partition.topic.name}", partitions = { "0", "1", "2", "3", "4" }))
    public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Received LOG Message: " + message + " from partition: " + partition);
    }
}

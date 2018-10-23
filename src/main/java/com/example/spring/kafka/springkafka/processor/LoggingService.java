package com.example.spring.kafka.springkafka.processor;

import com.example.spring.kafka.springkafka.message.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    @Autowired
    MessageProducer messageProducer;

    @Value("${partition.count}")
    private int partitionCount;

    public void logging(String message) {
        final long epoch = System.currentTimeMillis();
        final int partition = (int)(epoch % partitionCount);
        messageProducer.sendLogMessateToPartition("key", epoch + " : " + message, partition);
    }
}

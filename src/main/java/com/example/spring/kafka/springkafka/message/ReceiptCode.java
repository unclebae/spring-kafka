package com.example.spring.kafka.springkafka.message;

import lombok.Getter;

@Getter
public enum ReceiptCode {

    RECEIPTED,
    PROCESSING,
    COMPLETED,
    FAILED

}

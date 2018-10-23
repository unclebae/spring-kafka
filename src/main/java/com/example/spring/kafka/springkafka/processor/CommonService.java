package com.example.spring.kafka.springkafka.processor;

import com.example.spring.kafka.springkafka.message.ReceiptInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CommonService {

    static ConcurrentHashMap userInfoMap = new ConcurrentHashMap();

    static AtomicLong receiptIdInfo = new AtomicLong();

    public static Long getReceiptId() {
        return receiptIdInfo.incrementAndGet();
    }

    public static void saveReceiptInfo(ReceiptInfo receiptInfo) {
        userInfoMap.putIfAbsent(receiptInfo.getReceiptId(), receiptInfo);
    }

    public static ReceiptInfo findReceiptInfo(Long receiptId) {
        return (ReceiptInfo)userInfoMap.get(receiptId);
    }
}

package com.example.spring.kafka.springkafka.processor;

import com.example.spring.kafka.springkafka.message.ReceiptInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserInfoProcessService {

    @Autowired
    LoggingService loggingService;

    public void saveUserInfo(ReceiptInfo receiptInfo) {
        loggingService.logging("Save ReceiptInfo Info : " + receiptInfo);
        CommonService.saveReceiptInfo(receiptInfo);
    }

    public ReceiptInfo getUserInfoByReceiptId(Long receiptId) {
        return CommonService.findReceiptInfo(receiptId);
    }
}

package com.example.spring.kafka.springkafka.processor;

import com.example.spring.kafka.springkafka.message.MessageProducer;
import com.example.spring.kafka.springkafka.message.ReceiptCode;
import com.example.spring.kafka.springkafka.message.ReceiptInfo;
import com.example.spring.kafka.springkafka.message.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    @Autowired
    private MessageProducer messageProducer;

    public Long receiptUserInfo(UserInfo userInfo) {
        final Long receiptId = CommonService.getReceiptId();

        final ReceiptInfo receiptInfo = new ReceiptInfo(receiptId, ReceiptCode.RECEIPTED, userInfo);
        messageProducer.sendReceiptInfoMessage(receiptInfo);

        return receiptId;
    }

    public ReceiptInfo getUserInfo(Long receiptId) {

        final ReceiptInfo info = CommonService.findReceiptInfo(receiptId);
        if (info == null) {
            return new ReceiptInfo(receiptId, ReceiptCode.FAILED, null);
        }

        return info;
    }
}

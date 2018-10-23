package com.example.spring.kafka.springkafka.reception;

import com.example.spring.kafka.springkafka.message.MessageProducer;
import com.example.spring.kafka.springkafka.message.ReceiptInfo;
import com.example.spring.kafka.springkafka.message.UserInfo;
import com.example.spring.kafka.springkafka.processor.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    @Autowired
    MessageProducer producer;

    @Autowired
    ReceiptService receiptService;

    @PostMapping("/user-info")
    public Long receiptUserInfo(@RequestBody UserInfo userInfo) {
        return receiptService.receiptUserInfo(userInfo);
    }

    @GetMapping("/user-info/{receiptId}")
    public ReceiptInfo getUserInfo(@PathVariable Long receiptId) {
        return receiptService.getUserInfo(receiptId);
    }
}

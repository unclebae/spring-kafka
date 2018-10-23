package com.example.spring.kafka.springkafka.message;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReceiptInfo {

    /**
     * 접수번호
     */
    private Long receiptId;

    /**
     * 접수상태코드
     */
    private ReceiptCode receiptCode;

    /**
     * 접수정보
     */
    private UserInfo userInfo;

    public ReceiptInfo(){};

    public ReceiptInfo(Long receiptId, ReceiptCode receiptCode, UserInfo userInfo) {
        this.receiptId = receiptId;
        this.receiptCode = receiptCode;
        this.userInfo = userInfo;
    }
}

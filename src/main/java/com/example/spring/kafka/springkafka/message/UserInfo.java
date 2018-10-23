package com.example.spring.kafka.springkafka.message;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserInfo {
    private String name;
    private Integer age;
    private Float height;
    private Float weight;
}

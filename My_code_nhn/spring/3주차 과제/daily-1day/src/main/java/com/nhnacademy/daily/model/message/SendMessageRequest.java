package com.nhnacademy.daily.model.message;

import com.nhnacademy.daily.model.message.Channel;
import lombok.Getter;

@Getter
public class SendMessageRequest {
    String text;
    Channel channel;
}

package com.nhnacademy.daily.service;

import com.nhnacademy.daily.client.MyFeignClient;
import com.nhnacademy.daily.model.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MyFeignClient client;
    public void send(){
        Message message = new Message("봇", "테스트");
        client.sendMessage(message,"3204376758577275363","4045901689874472590","W0RgKMoPTUO3RejIIJVQcg");
    }
}

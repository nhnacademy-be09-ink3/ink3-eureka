package com.nhnacademy.miniproject.service;


import com.nhnacademy.miniproject.client.MyFeignClient;
import com.nhnacademy.miniproject.domain.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageSendService {
    private final MyFeignClient feignClient;

    public MessageSendService(MyFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    public void send(String accessMemberId){
        Message message = new Message("봇",  "잦은 로그인 실패로 계정 접근이 차단되었습니다.");
//        feignClient.sendMessage(message,"3204376758577275363","4045901689874472590","W0RgKMoPTUO3RejIIJVQcg");
        log.info("계정 차단!!!");
    }

}

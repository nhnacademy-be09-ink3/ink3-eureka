package com.nhnacademy.miniproject.client;

import com.nhnacademy.miniproject.domain.message.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "messageSendClient", url = "https://hook.dooray.com/services")
public interface MyFeignClient {

    @PostMapping("/{serviceId}/{botId}/{botToken}")
    String sendMessage(Message message,
                            @PathVariable String serviceId,
                            @PathVariable String botId,
                            @PathVariable String botToken);
}


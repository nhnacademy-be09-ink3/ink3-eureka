package com.nhnacademy.daily.client;


import com.nhnacademy.daily.model.message.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messageSendClient", url = "https://hook.dooray.com/services")
public interface MyFeignClient {
    @PostMapping("/{serviceId}/{botId}/{botToken}")
    String sendMessage(@RequestBody Message message,@PathVariable String  serviceId,@PathVariable String botId, @PathVariable String botToken);
}

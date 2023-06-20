package com.example.appapi;

import com.example.appapi.client.GatewayFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WebController {
    @Autowired
    private GatewayFeignClient gatewayFeignClient;

    @GetMapping("/getGatewayResource")
    public String getGatewayResource() {
        return gatewayFeignClient.getResource();
    }

    @GetMapping("/resource")
    public String getResource() {
        return "This is a resource from AppApi";
    }
}

package com.example.gatewayapi;

import com.example.gatewayapi.client.AppApiFeignClient;
import com.example.gatewayapi.client.WebApiFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway-api")
public class ResourceController {
    @Autowired
    private WebApiFeignClient webApiFeignClient;

    @Autowired
    private AppApiFeignClient appApiFeignClient;

    @GetMapping("/resource")
    public String getResource() {
        return "This is a resource from GatewayAPI";
    }

    @GetMapping("/getAppApiResource")
    public String getAppApiResource() { return appApiFeignClient.getResource();}
}
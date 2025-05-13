package org.khr.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KK
 * @create 2025-05-13-13:11
 */
@RestController
public class TestController {

    @PostMapping("/order/query")
    public String queryOrder() {
        return "/order/query";
    }

    @GetMapping("/order/update")
    public String update() {
        return "/order/update";
    }

}

package org.khr.securitydemo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("/user")
public class UserController {
    // 方法一
    @RequestMapping("/getid")
    public String getId() {
        try (Entry entry = SphU.entry("getid")) { // 定义资源
            // 正常执行的业务逻辑
            return "Id：" + new Random().nextInt(100);
        } catch (BlockException blockException) {
            // 如果执行到此处，说明当前资源已经被限流或熔断
            return "被限制";
        }
    }


    // 方法二 注解定义资源
    @SentinelResource(value = "getname",
            blockHandler = "myBlockHander")
    @RequestMapping("/getname")
    public String getName() {
        return "Name：" + new Random().nextInt(100);
    }

    // 限流之后执行的方法
    public String myBlockHander(BlockException blockException) {
        return "被限制了";
    }
}
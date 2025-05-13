package org.khr.securitydemo.controller;

import org.khr.securitydemo.common.ErrorCode;
import org.khr.securitydemo.exception.BusinessException;
import org.khr.securitydemo.manager.CounterManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author KK
 * @create 2025-05-13-13:11
 */
@RestController
public class TestController {

    // 仅是为了方便，才把这段代码写到这里
    @Resource
    private CounterManager counterManager;

    /**
     * 检测爬虫
     *
     * @param loginUserId
     */
    private void crawlerDetect(long loginUserId) {
        // 调用多少次时告警
        final int WARN_COUNT = 10;
        // 调用多少次时封号
        final int BAN_COUNT = 20;
        // 拼接访问 key
        String key = String.format("user:access:%s", loginUserId);
        // 统计一分钟内访问次数，180 秒过期
        long count = counterManager.incrAndGetCounter(key, 1, TimeUnit.MINUTES, 180);
        // 是否封号
        if (count > BAN_COUNT) {
            // 封号
            // User updateUser = new User();
            // updateUser.setId(loginUserId);
            // updateUser.setUserRole("ban");
            // userService.updateById(updateUser);
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "访问次数过多，已被封号");
        }
        // 是否告警
        if (count == WARN_COUNT) {
            // 可以改为向管理员发送邮件通知
            throw new BusinessException(110, "警告：访问太频繁");
        }
    }

    @PostMapping("/order/query/{id}")
    public String queryOrder(@PathVariable long id) {
        crawlerDetect(id);
        return "/order/query";
    }

    @GetMapping("/order/update")
    public String update() {
        return "/order/update";
    }

}

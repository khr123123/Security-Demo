package org.khr.securitydemo.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//@Component  //不采用统一处理 个别方法有个别的 处理方式
public class SentinelBlockHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       BlockException e) throws Exception {

        String msg = "未知异常";
        int status = 429;

        if (e instanceof FlowException) {
            msg = "请求被限流了！";
        } else if (e instanceof DegradeException) {
            msg = "请求被降级了！";
        } else if (e instanceof ParamFlowException) {
            msg = "热点参数限流！";
        } else if (e instanceof AuthorityException) {
            msg = "请求没有权限！";
            status = 401;
        }

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status);

        PrintWriter writer = response.getWriter();
        writer.println("{\"message\":\"" + msg + "\", \"status\":" + status + "}");
        writer.flush();
        writer.close();
    }
}

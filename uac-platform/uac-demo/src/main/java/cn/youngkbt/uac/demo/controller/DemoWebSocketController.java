package cn.youngkbt.uac.demo.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.websocket.core.WebSocketMessageContext;
import cn.youngkbt.websocket.helper.WebSocketHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/6/10 03:23:05
 * @note
 */
@RestController
@RequestMapping("/demo/websocket")
public class DemoWebSocketController {

    /**
     * 发布消息
     *
     */
    @GetMapping("/send")
    public Response<String> send(WebSocketMessageContext webSocketMessageContext) {
        WebSocketHelper.publishMessage(webSocketMessageContext);
        return HttpResult.ok("操作成功");
    }
}

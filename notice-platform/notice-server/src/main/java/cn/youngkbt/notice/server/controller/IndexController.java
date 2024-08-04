package cn.youngkbt.notice.server.controller;

import cn.hutool.core.util.StrUtil;
import cn.youngkbt.notice.core.config.NoticeConfig;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 00:54:55
 * @note
 */
@RequiredArgsConstructor
@RestController
public class IndexController {
    /**
     * 系统基础配置
     */
    private final NoticeConfig noticeConfig;

    @GetMapping("/")
    @Operation(summary = "系统首页", description = "访问首页，返回提示语")
    public String index() {
        return StrUtil.format("欢迎使用 {}，当前版本：v{}，请通过前端地址访问。", noticeConfig.getName(), noticeConfig.getVersion());
    }
}

package cn.youngkbt.uac.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.uac.core.config.UacConfig;
import cn.youngkbt.utils.IdsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2023/11/19 18:20
 * @note
 */

@RequiredArgsConstructor
@RestController
public class IndexController {

    /**
     * 系统基础配置
     */
    private final UacConfig uacConfig;

    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public String index() {
        return StrUtil.format("欢迎使用 {} 后台管理框架，当前版本：v{}，请通过前端地址访问。", uacConfig.getName(), uacConfig.getVersion());
    }

    @GetMapping("/uuid")
    public Response<String> uuid() {
        return HttpResult.ok(IdsUtil.simpleUUID());
    }
}

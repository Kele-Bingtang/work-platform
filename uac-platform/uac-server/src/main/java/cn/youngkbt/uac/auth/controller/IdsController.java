package cn.youngkbt.uac.auth.controller;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.utils.IdsUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:11
 * @note
 */
@Slf4j
@RestController
@RequestMapping("/ids")
@RequiredArgsConstructor
public class IdsController {

    @GetMapping("/simpleUuid")
    @Operation(summary = "生成简单 UUID")
    public Response<String> simpleUuid() {
        return HttpResult.ok(IdsUtil.simpleUUID());
    }

    @GetMapping("/uuid")
    @Operation(summary = "生成 UUID")
    public Response<String> uuid() {
        return HttpResult.ok(IdsUtil.UUID());
    }
}

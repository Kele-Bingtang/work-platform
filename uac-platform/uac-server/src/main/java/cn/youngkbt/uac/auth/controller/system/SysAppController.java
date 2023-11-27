package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 22:44
 * @note
 */
@RestController
@RequestMapping("/system/app")
public class SysAppController {

    @GetMapping("/list")
    public Response list() {
        return null;
    }
}

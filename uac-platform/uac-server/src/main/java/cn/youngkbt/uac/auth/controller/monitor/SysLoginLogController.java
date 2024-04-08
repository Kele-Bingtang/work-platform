package cn.youngkbt.uac.auth.controller.monitor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysLoginLogDTO;
import cn.youngkbt.uac.sys.model.vo.SysLoginLogVO;
import cn.youngkbt.uac.sys.service.SysLoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kele-Bingtang
 * @date 2024/4/7 21:22
 * @note 登录日志 Controller
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/loginLog")
public class SysLoginLogController {
    
    private final SysLoginLogService sysLoginLogService;
    
    @RequestMapping("/listPage")
    @Operation(summary = "登录日志列表查询", description = "通过条件查询登录日志列表（支持分页）")
    public Response<TablePage<SysLoginLogVO>> listPage(SysLoginLogDTO sysLoginLogDTO, PageQuery pageQuery) {
        TablePage<SysLoginLogVO> tablePage = sysLoginLogService.listPage(sysLoginLogDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }
}

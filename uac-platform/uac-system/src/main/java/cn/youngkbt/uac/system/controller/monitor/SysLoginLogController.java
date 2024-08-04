package cn.youngkbt.uac.system.controller.monitor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.excel.helper.ExcelHelper;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.log.annotation.OperateLog;
import cn.youngkbt.uac.core.log.enums.BusinessType;
import cn.youngkbt.uac.system.model.dto.SysLoginLogDTO;
import cn.youngkbt.uac.system.model.vo.SysLoginLogVO;
import cn.youngkbt.uac.system.service.SysLoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('system:loginLog:list')")
    public Response<TablePage<SysLoginLogVO>> listPage(SysLoginLogDTO sysLoginLogDTO, PageQuery pageQuery) {
        TablePage<SysLoginLogVO> tablePage = sysLoginLogService.listPage(sysLoginLogDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "登录日志删除", description = "通过主键批量删除登录日志")
    @OperateLog(title = "登录日志管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('system:loginLog:remove')")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysLoginLogService.removeBatch(List.of(ids)));
    }

    @DeleteMapping("/clean")
    @Operation(summary = "登录日志清除", description = "清除所有登录日志")
    @OperateLog(title = "登录日志管理", businessType = BusinessType.CLEAN)
    @PreAuthorize("hasAuthority('system:loginLog:remove')")
    public Response<Boolean> cleanAllOperaLog() {
        return HttpResult.ok(sysLoginLogService.cleanAllOperaLog());
    }

    @PostMapping("/export")
    @Operation(summary = "登录日志数据导出", description = "导出登录日志数据")
    @OperateLog(title = "登录日志管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('system:loginLog:export')")
    public void export(@RequestBody SysLoginLogDTO sysLoginLogDTO, HttpServletResponse response) {
        List<SysLoginLogVO> sysLoginLogVOList = sysLoginLogService.listAll(sysLoginLogDTO);
        ExcelHelper.exportExcel(sysLoginLogVOList, "登录日志", SysLoginLogVO.class, response);
    }
}

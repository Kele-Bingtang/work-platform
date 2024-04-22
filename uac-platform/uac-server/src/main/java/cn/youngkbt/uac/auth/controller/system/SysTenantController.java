package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.constant.TenantConstant;
import cn.youngkbt.uac.sys.model.dto.SysTenantDTO;
import cn.youngkbt.uac.sys.model.vo.SysTenantVO;
import cn.youngkbt.uac.sys.service.SysTenantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 19:23
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant")
public class SysTenantController {

    private final SysTenantService sysTenantService;

    @GetMapping("/list")
    @Operation(summary = "租户列表查询", description = "通过条件查询租户列表")
    @PreAuthorize("hasAuthority('system:tenant:list')")
    public Response<List<SysTenantVO>> list(SysTenantDTO sysTenantDTO) {
        List<SysTenantVO> sysTenantVOList = sysTenantService.queryList(sysTenantDTO);
        return HttpResult.ok(sysTenantVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "租户列表查询", description = "通过条件查询租户列表")
    @PreAuthorize("hasAuthority('system:tenant:list')")
    public Response<TablePage<SysTenantVO>> listPage(SysTenantDTO sysTenantDTO, PageQuery pageQuery) {
        TablePage<SysTenantVO> tablePage = sysTenantService.listPage(sysTenantDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }


    @PostMapping
    @Operation(summary = "租户列表新增", description = "新增租户")
    @PreAuthorize("hasAuthority('system:tenant:add')")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysTenantDTO sysTenantDTO) {
        if (sysTenantService.checkCompanyNameUnique(sysTenantDTO)) {
            return HttpResult.failMessage("新增租户'" + sysTenantDTO.getTenantName() + "'失败，租户已存在");
        }
        return HttpResult.ok(sysTenantService.insertOne(sysTenantDTO));
    }

    @PutMapping
    @Operation(summary = "租户列表修改", description = "修改租户")
    @PreAuthorize("hasAuthority('system:tenant:edit')")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysTenantDTO sysTenantDTO) {
        if (sysTenantService.checkCompanyNameUnique(sysTenantDTO)) {
            return HttpResult.failMessage("修改租户'" + sysTenantDTO.getTenantName() + "'失败，租户已存在");
        }
        return HttpResult.ok(sysTenantService.updateOne(sysTenantDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "租户列表删除", description = "通过主键批量删除租户")
    @PreAuthorize("hasAuthority('system:tenant:remove')")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids, @RequestBody List<String> tenantIds) {
        if (tenantIds.contains(TenantConstant.DEFAULT_TENANT_ID)) {
            return HttpResult.failMessage("初始租户不允许删除");
        }
        return HttpResult.ok(sysTenantService.removeBatch(List.of(ids)));
    }
}

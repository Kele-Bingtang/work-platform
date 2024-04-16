package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.sys.model.dto.SysTenantDTO;
import cn.youngkbt.uac.sys.model.vo.SysTenantVO;
import cn.youngkbt.uac.sys.service.SysTenantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    @Operation(summary = "租户列表查询", description = "通过主键查询租户列表（分页）")
    public Response<SysTenantVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysTenantVO sysTenantVo = sysTenantService.listById(id);
        return HttpResult.ok(sysTenantVo);
    }

    @GetMapping("/list")
    @Operation(summary = "租户列表查询", description = "通过条件查询租户列表")
    public Response<List<SysTenantVO>> list(SysTenantDTO sysTenantDTO) {
        List<SysTenantVO> sysTenantVOList = sysTenantService.queryList(sysTenantDTO);
        return HttpResult.ok(sysTenantVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "租户列表查询", description = "通过条件查询租户列表")
    public Response<TablePage<SysTenantVO>> listPage(SysTenantDTO sysTenantDTO, PageQuery pageQuery) {
        TablePage<SysTenantVO> tablePage = sysTenantService.listPage(sysTenantDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }


    @PostMapping
    @Operation(summary = "租户列表新增", description = "新增租户")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysTenantDTO sysTenantDTO) {
        if (sysTenantService.checkCompanyNameUnique(sysTenantDTO)) {
            return HttpResult.failMessage("新增租户'" + sysTenantDTO.getTenantName() + "'失败，企业名称已存在");
        }
        return HttpResult.ok(sysTenantService.insertOne(sysTenantDTO));
    }

    @PutMapping
    @Operation(summary = "租户列表修改", description = "修改租户")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysTenantDTO sysTenantDTO) {
        return HttpResult.ok(sysTenantService.updateOne(sysTenantDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "租户列表删除", description = "通过主键批量删除租户")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids, @RequestBody List<String> tenantIds) {
        if (tenantIds.contains(AuthConstant.ADMIN_TENANT_ID)) {
            return HttpResult.failMessage("初始租户不允许删除");
        }
        return HttpResult.ok(sysTenantService.removeBatch(List.of(ids)));
    }
}

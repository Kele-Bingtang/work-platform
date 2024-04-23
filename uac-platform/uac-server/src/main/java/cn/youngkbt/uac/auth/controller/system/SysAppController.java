package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysAppDTO;
import cn.youngkbt.uac.sys.model.vo.SysAppVO;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVO;
import cn.youngkbt.uac.sys.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 22:44
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/app")
public class SysAppController {

    private final SysAppService sysAppService;
    private final SysClientService sysClientService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final SysDictTypeService sysDictTypeService;

    @GetMapping("/list")
    @Operation(summary = "应用列表查询", description = "通过应用条件查询应用列表")
    @PreAuthorize("hasAuthority('system:app:list')")
    public Response<List<SysAppVO>> list(SysAppDTO sysAppDTO) {
        List<SysAppVO> sysAppVOList = sysAppService.queryList(sysAppDTO);
        return HttpResult.ok(sysAppVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "应用列表查询", description = "通过应用条件查询应用列表（支持分页）")
    @PreAuthorize("hasAuthority('system:app:list')")
    public Response<TablePage<SysAppVO>> listPage(SysAppDTO sysAppDTO, PageQuery pageQuery) {
        TablePage<SysAppVO> tablePage = sysAppService.listPage(sysAppDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/list/{clientId}")
    @Operation(summary = "应用列表查询", description = "通过客户端 ID 查询 App 清单列表")
    @PreAuthorize("hasAuthority('system:app:list')")
    public Response<TablePage<SysAppVO>> listByClientId(@NotNull(message = "客户端 ID 不能为空") @PathVariable String clientId, PageQuery pageQuery) {
        if (Objects.isNull(sysClientService.checkClientIdThenGet(clientId))) {
            return HttpResult.errorMessage("客户端 ID 不存在");
        }
        SysAppDTO sysAppDTO = SysAppDTO.builder().clientId(clientId).build();
        TablePage<SysAppVO> tablePage = sysAppService.listPage(sysAppDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/treeList")
    @Operation(summary = "应用树形列表查询", description = "查询应用树形列表")
    @PreAuthorize("hasAuthority('system:app:query')")
    public Response<List<AppTreeVO>> listTreeList() {
        List<AppTreeVO> appTreeVOList = sysAppService.listTreeList();
        return HttpResult.ok(appTreeVOList);
    }

    @PostMapping
    @Operation(summary = "应用新增", description = "新增应用")
    @PreAuthorize("hasAuthority('system:app:add')")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysAppDTO sysAppDTO) {
        if (sysAppService.checkAppCodeUnique(sysAppDTO)) {
            return HttpResult.failMessage("新增应用「" + sysAppDTO.getAppName() + "」失败，应用编码「" + sysAppDTO.getAppCode() + "」已存在");
        }
        
        return HttpResult.ok(sysAppService.insertOne(sysAppDTO));
    }

    @PutMapping
    @Operation(summary = "应用修改", description = "修改应用")
    @PreAuthorize("hasAuthority('system:app:edit')")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysAppDTO sysAppDTO) {
        if (sysAppService.checkAppCodeUnique(sysAppDTO)) {
            return HttpResult.failMessage("修改应用「" + sysAppDTO.getAppName() + "」失败，应用编码「" + sysAppDTO.getAppCode() + "」已存在");
        }
        return HttpResult.ok(sysAppService.updateOne(sysAppDTO));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "应用删除", description = "通过主键批量删除应用")
    @PreAuthorize("hasAuthority('system:app:remove')")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids, @RequestBody List<String> appIds) {
        if (sysRoleService.checkAppExitRole(appIds)) {
            return HttpResult.failMessage("存在角色绑定，不允许删除");
        }

        if (sysMenuService.checkAppExitMenu(appIds)) {
            return HttpResult.failMessage("存在菜单绑定，不允许删除");
        }

        if (sysDictTypeService.checkAppExitDictType(appIds)) {
            return HttpResult.failMessage("存在字典类型绑定，不允许删除");
        }

        return HttpResult.ok(sysAppService.removeBatch(List.of(ids)));
    }

}

package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.core.log.annotation.OperateLog;
import cn.youngkbt.uac.core.log.enums.BusinessType;
import cn.youngkbt.uac.sys.model.dto.SysClientDTO;
import cn.youngkbt.uac.sys.model.vo.SysClientVO;
import cn.youngkbt.uac.sys.model.vo.extra.ClientTreeVO;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.uac.sys.service.SysClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 22:47
 * @note
 */
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/system/client")
public class SysClientController {

    private final SysClientService clientService;
    private final SysAppService sysAppService;

    @GetMapping("/list")
    @Operation(summary = "客户端列表查询", description = "通过客户端条件查询客户端列表）")
    @PreAuthorize("hasAuthority('system:client:list')")
    public Response<List<SysClientVO>> list(SysClientDTO sysClientDTO) {
        List<SysClientVO> sysClientVOList = clientService.queryList(sysClientDTO);
        return HttpResult.ok(sysClientVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "客户端列表查询", description = "通过客户端条件查询客户端列表（支持分页）")
    @PreAuthorize("hasAuthority('system:client:list')")
    public Response<TablePage<SysClientVO>> listPage(SysClientDTO sysClientDTO, PageQuery pageQuery) {
        TablePage<SysClientVO> tablePage = clientService.listPage(sysClientDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/treeList")
    @Operation(summary = "客户端树形列表查询", description = "查询客户端树形列表")
    @PreAuthorize("hasAuthority('system:client:query')")
    public Response<List<ClientTreeVO>> listTreeList() {
        List<ClientTreeVO> sysClientVoList = clientService.listTreeList();
        return HttpResult.ok(sysClientVoList);
    }

    @PostMapping
    @Operation(summary = "客户端新增", description = "新增客户端")
    @OperateLog(title = "客户端管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('system:client:add')")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysClientDTO sysClientDTO) {
        if (clientService.checkClientKeyUnique(sysClientDTO)) {
            return HttpResult.failMessage("新增客户端「" + sysClientDTO.getClientName() + "」失败，客户端 Key「" + sysClientDTO.getClientKey() + "」已存在");
        } else if (clientService.checkClientSecretUnique(sysClientDTO)) {
            return HttpResult.failMessage("新增客户端「" + sysClientDTO.getClientName() + "」失败，客户端密钥「" + sysClientDTO.getClientSecret() + "」已存在");
        }

        return HttpResult.ok(clientService.insertOne(sysClientDTO));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    @Operation(summary = "客户端修改", description = "修改客户端")
    @OperateLog(title = "客户端管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('system:client:edit')")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysClientDTO sysClientDTO) {
        if (clientService.checkClientKeyUnique(sysClientDTO)) {
            return HttpResult.failMessage("修改客户端「" + sysClientDTO.getClientName() + "」失败，客户端 Key「" + sysClientDTO.getClientKey() + "」已存在");
        }
        if (clientService.checkClientSecretUnique(sysClientDTO)) {
            return HttpResult.failMessage("修改客户端「" + sysClientDTO.getClientName() + "」失败，客户端密钥「" + sysClientDTO.getClientSecret() + "」已存在");
        }

        return HttpResult.ok(clientService.updateOne(sysClientDTO));
    }

    @PutMapping("/updateStatus")
    @Operation(summary = "客户端状态修改", description = "修改客户端状态")
    @OperateLog(title = "客户端管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('system:client:edit')")
    public Response<Boolean> updateStatus(@RequestBody SysClientDTO sysClientDTO) {
        return HttpResult.ok(clientService.updateStatus(sysClientDTO.getId(), sysClientDTO.getStatus()));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "客户端删除", description = "通过主键批量删除客户端")
    @OperateLog(title = "客户端管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('system:client:remove')")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids, @RequestBody List<String> clientIds) {
        if (sysAppService.checkExitApp(clientIds)) {
            return HttpResult.failMessage("存在 APP 绑定，不允许删除");
        }

        return HttpResult.ok(clientService.removeBatch(List.of(ids)));
    }
}

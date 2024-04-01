package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysClientDTO;
import cn.youngkbt.uac.sys.model.vo.SysClientVO;
import cn.youngkbt.uac.sys.model.vo.extra.ClientTreeVO;
import cn.youngkbt.uac.sys.service.SysClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    @Operation(summary = "客户端列表查询", description = "通过主键查询客户端列表")
    public Response<SysClientVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysClientVO sysClientVo = clientService.listById(id);
        return HttpResult.ok(sysClientVo);
    }

    @GetMapping("/list")
    @Operation(summary = "客户端列表查询", description = "通过客户端条件查询客户端列表）")
    public Response<List<SysClientVO>> list(SysClientDTO sysClientDTO) {
        List<SysClientVO> sysClientVOList = clientService.queryList(sysClientDTO);
        return HttpResult.ok(sysClientVOList);
    }

    @GetMapping("/listPage")
    @Operation(summary = "客户端列表查询", description = "通过客户端条件查询客户端列表（支持分页）")
    public Response<TablePage<SysClientVO>> listPage(SysClientDTO sysClientDTO, PageQuery pageQuery) {
        TablePage<SysClientVO> tablePage = clientService.listPage(sysClientDTO, pageQuery);
        return HttpResult.ok(tablePage);
    }

    @GetMapping("/treeList")
    @Operation(summary = "客户端树形列表查询", description = "查询客户端树形列表")
    public Response<List<ClientTreeVO>> listTreeList() {
        List<ClientTreeVO> sysClientVoList = clientService.listTreeList();
        return HttpResult.ok(sysClientVoList);
    }

    @PostMapping
    @Operation(summary = "客户端新增", description = "新增客户端")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysClientDTO sysClientDTO) {
        return HttpResult.ok(clientService.insertOne(sysClientDTO));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    @Operation(summary = "客户端修改", description = "修改客户端")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysClientDTO sysClientDTO) {
        return HttpResult.ok(clientService.updateOne(sysClientDTO));
    }

    @PutMapping("/updateStatus")
    @Operation(summary = "客户端状态修改", description = "修改客户端状态")
    public Response<Boolean> updateStatus(@RequestBody SysClientDTO sysClientDTO) {
        return HttpResult.ok(clientService.updateStatus(sysClientDTO.getId(), sysClientDTO.getStatus()));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "客户端删除", description = "通过主键批量删除客户端")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        // TODO：如果有 APP 绑定，则无法删除
        return HttpResult.ok(clientService.removeBatch(List.of(ids)));
    }
}

package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysAppDTO;
import cn.youngkbt.uac.sys.model.vo.SysAppVO;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVO;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.uac.sys.service.SysClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    @Operation(summary = "应用列表查询", description = "通过主键查询应用")
    public Response<SysAppVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysAppVO sysClientVo = sysAppService.listById(id);
        return HttpResult.ok(sysClientVo);
    }

    @GetMapping("/list")
    @Operation(summary = "应用列表查询", description = "通过应用条件查询应用列表（支持分页）")
    public Response<List<SysAppVO>> list(SysAppDTO sysAppDto, PageQuery pageQuery) {
        List<SysAppVO> sysAppVOList = sysAppService.listWithPage(sysAppDto, pageQuery);
        return HttpResult.ok(sysAppVOList);
    }

    @GetMapping("/list/{clientId}")
    @Operation(summary = "应用列表查询", description = "通过客户端 ID 查询 App 清单列表")
    public Response<List<SysAppVO>> listByClientId(@NotNull(message = "客户端 ID 不能为空") @PathVariable String clientId, PageQuery pageQuery) {
        if (Objects.isNull(sysClientService.checkClientIdThenGet(clientId))) {
            return HttpResult.errorMessage("客户端 ID 不存在");
        }
        SysAppDTO sysAppDto = SysAppDTO.builder().clientId(clientId).build();
        List<SysAppVO> sysAppVOList = sysAppService.listWithPage(sysAppDto, pageQuery);
        return HttpResult.ok(sysAppVOList);
    }

    @GetMapping("/treeList")
    @Operation(summary = "应用树形列表查询", description = "查询应用树形列表")
    public Response<List<AppTreeVO>> listTreeList() {
        List<AppTreeVO> appTreeVOList = sysAppService.listTreeList();
        return HttpResult.ok(appTreeVOList);
    }

    @PostMapping
    @Operation(summary = "应用新增", description = "新增应用")
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysAppDTO sysAppDto) {
        return HttpResult.ok(sysAppService.insertOne(sysAppDto));
    }

    @PutMapping
    @Operation(summary = "应用修改", description = "修改应用")
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysAppDTO sysAppDto) {
        return HttpResult.ok(sysAppService.updateOne(sysAppDto));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "应用删除", description = "通过主键批量删除应用")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysAppService.removeBatch(List.of(ids)));
    }

}

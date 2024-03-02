package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysAppDto;
import cn.youngkbt.uac.sys.model.vo.SysAppTreeVo;
import cn.youngkbt.uac.sys.model.vo.SysAppVo;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.uac.sys.service.SysClientService;
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
    public Response<SysAppVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysAppVo sysClientVo = sysAppService.queryById(id);
        return HttpResult.ok(sysClientVo);
    }

    /**
     * 应用列表查询
     */
    @GetMapping("/list")
    public Response<List<SysAppVo>> list(SysAppDto sysAppDto, PageQuery pageQuery) {
        List<SysAppVo> sysAppVoList = sysAppService.queryListWithPage(sysAppDto, pageQuery);
        return HttpResult.ok(sysAppVoList);
    }

    @GetMapping("/list/{clientId}")
    public Response<List<SysAppVo>> listFromClient(@NotNull(message = "客户端 ID 不能为空") @PathVariable String clientId, PageQuery pageQuery) {
        if (Objects.isNull(sysClientService.checkClientIdThenGet(clientId))) {
            return HttpResult.errorMessage("客户端 ID 不存在");
        }
        SysAppDto sysAppDto = SysAppDto.builder().clientId(clientId).build();
        List<SysAppVo> sysAppVoList = sysAppService.queryListWithPage(sysAppDto, pageQuery);
        return HttpResult.ok(sysAppVoList);
    }
    
    @GetMapping("/appTreeList")
    public Response<List<SysAppTreeVo>> appTreeList() {
        List<SysAppTreeVo> sysAppTreeVoList = sysAppService.appTreeList();
        return HttpResult.ok(sysAppTreeVoList);
    }

    /**
     * 应用新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysAppDto sysAppDto) {
        return HttpResult.ok(sysAppService.insertOne(sysAppDto));
    }

    /**
     * 应用修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysAppDto sysAppDto) {
        return HttpResult.ok(sysAppService.updateOne(sysAppDto));
    }

    /**
     * 应用删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysAppService.removeBatch(List.of(ids)));
    }

}

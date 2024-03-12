package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysClientDTO;
import cn.youngkbt.uac.sys.model.vo.SysClientVO;
import cn.youngkbt.uac.sys.model.vo.extra.ClientTreeVO;
import cn.youngkbt.uac.sys.service.SysClientService;
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

    /**
     * 查询某个客户端
     */
    @GetMapping("/{id}")
    public Response<SysClientVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysClientVO sysClientVo = clientService.listById(id);
        return HttpResult.ok(sysClientVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysClientVO>> list(SysClientDTO sysClientDto, PageQuery pageQuery) {
        List<SysClientVO> sysClientVOList = clientService.queryListWithPage(sysClientDto, pageQuery);
        return HttpResult.ok(sysClientVOList);
    }

    @GetMapping("/treeList")
    public Response<List<ClientTreeVO>> listTreeList() {
        List<ClientTreeVO> sysClientVoList = clientService.listTreeList();
        return HttpResult.ok(sysClientVoList);
    }
    
    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysClientDTO sysClientDto) {
        return HttpResult.ok(clientService.insertOne(sysClientDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysClientDTO sysClientDto) {
        return HttpResult.ok(clientService.updateOne(sysClientDto));
    }

    /**
     * 客户端状态修改
     */
    @PutMapping("/updateStatus")
    public Response<Boolean> updateStatus(@RequestBody SysClientDTO sysClientDto) {
        return HttpResult.ok(clientService.updateStatus(sysClientDto.getId(), sysClientDto.getStatus()));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        // TODO：如果有 APP 绑定，则无法删除
        return HttpResult.ok(clientService.removeBatch(List.of(ids)));
    }
}

package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysClientDto;
import cn.youngkbt.uac.sys.model.vo.SysClientVo;
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

    @GetMapping("/{id}")
    public Response<SysClientVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysClientVo sysClientVo = clientService.queryById(id);
        return HttpResult.ok(sysClientVo);
    }

    /**
     * 查询客户端列表
     */
    @GetMapping("/list")
    public Response<List<SysClientVo>> list(SysClientDto sysClientDto, PageQuery pageQuery) {
        List<SysClientVo> sysClientVo = clientService.queryListWithPage(sysClientDto, pageQuery);
        return HttpResult.ok(sysClientVo);
    }

    /**
     * 新增客户端管理
     */
    @PostMapping()
    public Response<List<SysClientVo>> add(@RequestBody SysClientDto sysClientDto) {
        return null;
    }

    /**
     * 修改客户端管理
     */
    @PutMapping()
    public Response<List<SysClientVo>> edit(@RequestBody SysClientDto sysClientDto) {
        return null;
    }

    /**
     * 删除客户端管理
     */
    @DeleteMapping()
    public Response<List<SysClientVo>> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return null;
    }
}

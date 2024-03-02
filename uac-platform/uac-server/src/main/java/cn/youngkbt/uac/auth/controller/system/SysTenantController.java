package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysTenantDto;
import cn.youngkbt.uac.sys.model.vo.SysTenantVo;
import cn.youngkbt.uac.sys.service.SysTenantService;
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
    public Response<SysTenantVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysTenantVo sysTenantVo = sysTenantService.queryById(id);
        return HttpResult.ok(sysTenantVo);
    }

    /**
     * 多租户列表查询
     */
    @GetMapping("/list")
    public Response<List<SysTenantVo>> list(SysTenantDto sysTenantDto, PageQuery pageQuery) {
        List<SysTenantVo> sysTenantVoList = sysTenantService.queryListWithPage(sysTenantDto, pageQuery);
        return HttpResult.ok(sysTenantVoList);
    }


    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysTenantDto sysTenantDto) {
        return HttpResult.ok(sysTenantService.insertOne(sysTenantDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysTenantDto sysTenantDto) {
        return HttpResult.ok(sysTenantService.updateOne(sysTenantDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysTenantService.removeBatch(List.of(ids)));
    }
}

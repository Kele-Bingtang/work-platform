package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDeptDto;
import cn.youngkbt.uac.sys.model.vo.SysDeptVo;
import cn.youngkbt.uac.sys.service.SysDeptService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/2 23:22
 * @note 部门 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController {
    
    private final SysDeptService sysDeptService;

    @GetMapping("/{id}")
    public Response<SysDeptVo> queryById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDeptVo sysDeptVo = sysDeptService.queryById(id);
        return HttpResult.ok(sysDeptVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysDeptVo>> list(SysDeptDto sysDeptDto, PageQuery pageQuery) {
        List<SysDeptVo> sysDeptVoList = sysDeptService.queryListWithPage(sysDeptDto, pageQuery);
        return HttpResult.ok(sysDeptVoList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        return HttpResult.ok(sysDeptService.insertOne(sysDeptDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDeptDto sysDeptDto) {
        return HttpResult.ok(sysDeptService.updateOne(sysDeptDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeOne(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDeptService.removeOne(List.of(ids)));
    }
}

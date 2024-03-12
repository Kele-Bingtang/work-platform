package cn.youngkbt.uac.auth.controller.system;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysDictDataDTO;
import cn.youngkbt.uac.sys.model.vo.SysDictDataVO;
import cn.youngkbt.uac.sys.service.SysDictDataService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/12/4 16:15
 * @note
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dictData")
public class SysDictDataController {
    private final SysDictDataService sysDictDataService;

    @GetMapping("/{id}")
    public Response<SysDictDataVO> listById(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        SysDictDataVO sysDictDataVo = sysDictDataService.listById(id);
        return HttpResult.ok(sysDictDataVo);
    }

    /**
     * 客户端列表查询
     */
    @GetMapping("/list")
    public Response<List<SysDictDataVO>> list(SysDictDataDTO sysDictDataDto, PageQuery pageQuery) {
        List<SysDictDataVO> sysDictDataVOList = sysDictDataService.queryListWithPage(sysDictDataDto, pageQuery);
        return HttpResult.ok(sysDictDataVOList);
    }

    /**
     * 客户端新增
     */
    @PostMapping
    public Response<Boolean> insertOne(@Validated(RestGroup.AddGroup.class) @RequestBody SysDictDataDTO sysDictDataDto) {
        return HttpResult.ok(sysDictDataService.insertOne(sysDictDataDto));
    }

    /**
     * 客户端修改
     */
    @PutMapping
    public Response<Boolean> updateOne(@Validated(RestGroup.EditGroup.class) @RequestBody SysDictDataDTO sysDictDataDto) {
        return HttpResult.ok(sysDictDataService.updateOne(sysDictDataDto));
    }

    /**
     * 客户端删除
     */
    @DeleteMapping("/{ids}")
    public Response<Boolean> removeBatch(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return HttpResult.ok(sysDictDataService.removeBatch(List.of(ids)));
    }
}

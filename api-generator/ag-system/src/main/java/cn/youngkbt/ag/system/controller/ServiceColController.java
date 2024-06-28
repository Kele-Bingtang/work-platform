package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.ServiceColDTO;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.ServiceColVO;
import cn.youngkbt.ag.system.service.ServiceColService;
import cn.youngkbt.ag.system.service.ServiceInfoService;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.validate.RestGroup;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 00:24:26
 * @note
 */
@RestController
@RequestMapping("/serviceCol")
@RequiredArgsConstructor
public class ServiceColController {

    private final ServiceColService serviceColService;
    private final ServiceInfoService serviceInfoService;

    @GetMapping("listPage")
    @Operation(summary = "服务列配置项查询（分页）", description = "通过条件查询服务列配置项（分页）")
    public Response<TablePage<ServiceColVO>> listPage(@Validated(RestGroup.QueryGroup.class) ServiceColDTO serviceColDTO, PageQuery pageQuery) {
        TablePage<ServiceColVO> serviceColVOTablePage = serviceColService.listPage(serviceColDTO, pageQuery);
        return HttpResult.ok(serviceColVOTablePage);
    }

    @PostMapping
    @Operation(summary = "服务列配置项新增", description = "新增服务列配置项")
    public Response<Boolean> addServiceCol(@Validated(RestGroup.AddGroup.class) @RequestBody ServiceColDTO serviceColDTO) {
        if(serviceColService.checkServiceColUnique(serviceColDTO)) {
            return HttpResult.failMessage("新增列配置项失败，字段名称已存在");
        }
        
        boolean result = serviceColService.addServiceCol(serviceColDTO);
        return HttpResult.okOrFail(result);
    }

    @PutMapping
    @Operation(summary = "服务列配置项修改", description = "修改服务列配置项")
    public Response<Boolean> editServiceCol(@Validated(RestGroup.EditGroup.class) @RequestBody ServiceColDTO serviceColDTO) {
        if(serviceColService.checkServiceColUnique(serviceColDTO)) {
            return HttpResult.failMessage("新增列配置项失败，字段名称已存在");
        }
        
        boolean result = serviceColService.editServiceCol(serviceColDTO);
        return HttpResult.okOrFail(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "服务列配置项删除", description = "删除服务列配置项")
    public Response<Boolean> removeServiceCol(@PathVariable String id) {
        boolean result = serviceColService.removeServiceColById(id);
        return HttpResult.okOrFail(result);
    }
    
    @PostMapping("/regenCol/{serviceId}")
    @Operation(summary = "重新生成新加入的列配置项", description = "重新生成新加入的列配置项")
    public Response<String> regenCol(@PathVariable String serviceId) {
        // 获取 SQL
        ServiceInfo serviceInfo = serviceInfoService.listOneByServiceId(serviceId);
        
        Integer length = serviceColService.regenCol(serviceInfo.getSelectSql(), serviceId);
        if (length > 0) {
            return HttpResult.okMessage("删除了 " + length + " 条数据");
        } else {
            return HttpResult.okMessage("不需要删除，没有失效的字段");
        }
    }

    @DeleteMapping("/removeInvalidCol/{serviceId}")
    @Operation(summary = "删除不存在的列配置项", description = "删除不存在的列配置项")
    public Response<String> removeInvalidCol(@PathVariable String serviceId) {
        // 获取 SQL
        ServiceInfo serviceInfo = serviceInfoService.listOneByServiceId(serviceId);
        
        Integer length = serviceColService.removeInvalidCol(serviceInfo.getSelectSql(), serviceId);
        if (length > 0) {
            return HttpResult.okMessage("删除了 " + length + " 条数据");
        } else {
            return HttpResult.okMessage("不需要删除，没有失效的字段");
        }
    }
}

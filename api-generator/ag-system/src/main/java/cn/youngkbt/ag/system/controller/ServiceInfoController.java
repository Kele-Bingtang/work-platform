package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.model.dto.ServiceInfoDTO;
import cn.youngkbt.ag.system.model.vo.ServiceInfoVO;
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
 * @date 2024/6/25 23:24:21
 * @note
 */
@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceInfoController {
    
    private final ServiceInfoService serviceInfoService;
    
    @GetMapping("/listPage")
    @Operation(summary = "服务列表查询", description = "通过条件查询服务列表")
    public Response<TablePage<ServiceInfoVO>> listPage(@Validated(RestGroup.QueryGroup.class) ServiceInfoDTO serviceInfoDTO, PageQuery pageQuery) {
        TablePage<ServiceInfoVO> serviceInfoVOTablePage = serviceInfoService.listPage(serviceInfoDTO, pageQuery);
        return HttpResult.ok(serviceInfoVOTablePage);
    }
    
    @PostMapping
    @Operation(summary = "服务新增", description = "新增服务")
    public Response<Boolean> addService(@Validated(RestGroup.AddGroup.class) ServiceInfoDTO serviceInfoDTO) {
        if(serviceInfoService.checkServiceNameUnique(serviceInfoDTO)) {
            return HttpResult.failMessage("新增服务「" + serviceInfoDTO.getServiceName() + "」失败，服务名称已存在");
        }

        if(serviceInfoService.checkServiceUrlUnique(serviceInfoDTO)) {
            return HttpResult.failMessage("新增服务「" + serviceInfoDTO.getServiceName() + "」失败，服务链接已存在");
        }
        
        boolean addService = serviceInfoService.addService(serviceInfoDTO);
        return HttpResult.okOrFail(addService);
    }
    
    @PutMapping
    @Operation(summary = "服务修改", description = "修改服务")
    public Response<Boolean> editService(@Validated(RestGroup.EditGroup.class) ServiceInfoDTO serviceInfoDTO) {
        if(serviceInfoService.checkServiceNameUnique(serviceInfoDTO)) {
            return HttpResult.failMessage("编辑服务「" + serviceInfoDTO.getServiceName() + "」失败，服务名称已存在");
        }

        if(serviceInfoService.checkServiceUrlUnique(serviceInfoDTO)) {
            return HttpResult.failMessage("编辑服务「" + serviceInfoDTO.getServiceName() + "」失败，服务链接已存在");
        }
        
        boolean editService = serviceInfoService.editService(serviceInfoDTO);
        return HttpResult.okOrFail(editService);
    }
    
    @DeleteMapping("/{serviceId}")
    @Operation(summary = "服务删除", description = "删除服务")
    public Response<Boolean> removeService(@PathVariable String serviceId) {
        boolean removeService = serviceInfoService.removeService(serviceId);
        return HttpResult.okOrFail(removeService);
    }
    
}
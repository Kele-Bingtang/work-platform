package cn.youngkbt.integrate.system.service.impl;


import cn.youngkbt.core.http.Assert;
import cn.youngkbt.integrate.core.constants.GlobalConstant;
import cn.youngkbt.integrate.system.context.ContextHolder;
import cn.youngkbt.integrate.system.handler.DispatcherServiceHandler;
import cn.youngkbt.integrate.system.mapper.ConfigPoolMapper;
import cn.youngkbt.integrate.system.model.bo.ConfigPoolBO;
import cn.youngkbt.integrate.system.model.bo.SyncFlowBO;
import cn.youngkbt.integrate.system.model.dto.SyncFlowGetDTO;
import cn.youngkbt.integrate.system.model.dto.SyncFlowPostDTO;
import cn.youngkbt.integrate.system.model.dto.SyncFlowReceiveDataDTO;
import cn.youngkbt.integrate.system.model.po.AppInfo;
import cn.youngkbt.integrate.system.model.po.ConfigPool;
import cn.youngkbt.integrate.system.model.po.FlowMapping;
import cn.youngkbt.integrate.system.service.AppInfoService;
import cn.youngkbt.integrate.system.service.FlowMappingService;
import cn.youngkbt.integrate.system.service.IntegrateService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 00:22:29
 * @note
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IntegrateServiceImpl implements IntegrateService {
    private final DispatcherServiceHandler dispatcherServiceHandler = new DispatcherServiceHandler();
    private final ConfigPoolMapper configPoolMapper;
    private final AppInfoService appInfoService;
    private final FlowMappingService flowMappingService;

    @Override
    public Object getSourceAuth(SyncFlowGetDTO syncBodyGetDTO) {
        FlowMapping flowMapping = getFlowMapping(syncBodyGetDTO.getId());
        ConfigPoolBO sourceConfigPool = initConfig(flowMapping.getSourceId(), flowMapping);
        // GET 请求的 List 转成 Map
        Map<String, Object> sourceAuthInfoMap = listConvertMap(syncBodyGetDTO.getSourceAuthInfo());
        SyncFlowBO syncFlowBO = SyncFlowBO.builder().sourceInfo(sourceConfigPool).sourceAuthInfo(sourceAuthInfoMap).onlyGetAuth(true).build();
        ContextHolder.set(syncFlowBO);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, false);
        ContextHolder.remove();
        return response;
    }

    @Override
    public Object requestSourceForGet(SyncFlowGetDTO syncBodyGetDTO) {
        FlowMapping flowMapping = getFlowMapping(syncBodyGetDTO.getId());
        ConfigPoolBO sourceConfigPool = initConfig(flowMapping.getSourceId(), flowMapping);
        // GET 请求的 List 转成 Map 
        Map<String, Object> sourceAuthInfoMap = listConvertMap(syncBodyGetDTO.getSourceAuthInfo());
        Map<String, Object> sourceDeliverInfo = listConvertMap(syncBodyGetDTO.getSourceDeliverInfo());
        SyncFlowBO syncFlowBO = SyncFlowBO.builder().sourceInfo(sourceConfigPool).sourceAuthInfo(sourceAuthInfoMap).sourceDeliverInfo(sourceDeliverInfo).build();
        ContextHolder.set(syncFlowBO);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, false);
        ContextHolder.remove();
        return response;
    }

    @Override
    public Object requestSourceForPost(SyncFlowPostDTO syncFlowPostDTO) {
        FlowMapping flowMapping = getFlowMapping(syncFlowPostDTO.getId());
        ConfigPoolBO sourceConfigPool = initConfig(flowMapping.getSourceId(), flowMapping);
        SyncFlowBO syncFlowBO = MapstructUtil.convert(syncFlowPostDTO, SyncFlowBO.class);
        syncFlowBO.setSourceInfo(sourceConfigPool);
        ContextHolder.set(syncFlowBO);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, false);
        ContextHolder.remove();
        return response;
    }

    @Override
    public Object toTargetFlowForReceiveData(SyncFlowReceiveDataDTO syncFlowReceiveDataDTO) {
        FlowMapping flowMapping = getFlowMapping(syncFlowReceiveDataDTO.getId());
        ConfigPoolBO targetConfigPool = initConfig(flowMapping.getTargetId(), flowMapping);
        List<ConfigPoolBO> extraConfigPool = initExtraConfig(flowMapping.getExtraId());
        SyncFlowBO syncFlowBO = MapstructUtil.convert(syncFlowReceiveDataDTO, SyncFlowBO.class);

        syncFlowBO.setTargetInfo(targetConfigPool);
        syncFlowBO.setExtraPoolBOList(extraConfigPool);
        // 全局一次拦截器 Aspect 需要，因为异常会跳出多线程
        ContextHolder.set(syncFlowBO);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ContextHolder.set(syncFlowBO);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, true);
        ContextHolder.remove();
        return response;
    }

    @Override
    public Object sourceToTargetFlowForGet(SyncFlowGetDTO syncBodyGetDTO) {
        FlowMapping flowMapping = getFlowMapping(syncBodyGetDTO.getId());
        ConfigPoolBO sourceConfigPool = initConfig(flowMapping.getSourceId(), flowMapping);
        ConfigPoolBO targetConfigPool = initConfig(flowMapping.getTargetId(), flowMapping);
        List<ConfigPoolBO> extraConfigPool = initExtraConfig(flowMapping.getExtraId());
        // GET 请求的 List 转成 Map 
        Map<String, Object> sourceAuthInfoMap = listConvertMap(syncBodyGetDTO.getSourceAuthInfo());
        Map<String, Object> targetAuthInfo = listConvertMap(syncBodyGetDTO.getTargetAuthInfo());
        Map<String, Object> sourceDeliverInfo = listConvertMap(syncBodyGetDTO.getSourceDeliverInfo());
        Map<String, Object> targetDeliverInfo = listConvertMap(syncBodyGetDTO.getTargetDeliverInfo());
        SyncFlowBO syncFlowBO = SyncFlowBO.builder().sourceInfo(sourceConfigPool).targetInfo(targetConfigPool).extraPoolBOList(extraConfigPool).sourceAuthInfo(sourceAuthInfoMap).sourceDeliverInfo(sourceDeliverInfo).targetAuthInfo(targetAuthInfo).targetDeliverInfo(targetDeliverInfo).build();
        ContextHolder.set(syncFlowBO);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ContextHolder.set(syncFlowBO);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, true);
        ContextHolder.remove();
        return response;
    }

    @Override
    public Object sourceToTargetFlowForPost(SyncFlowPostDTO syncFlowPostDTO) {
        FlowMapping flowMapping = getFlowMapping(syncFlowPostDTO.getId());
        ConfigPoolBO sourceConfigPool = initConfig(flowMapping.getSourceId(), flowMapping);
        ConfigPoolBO targetConfigPool = initConfig(flowMapping.getTargetId(), flowMapping);
        List<ConfigPoolBO> extraConfigPool = initExtraConfig(flowMapping.getExtraId());
        SyncFlowBO syncFlowBO = MapstructUtil.convert(syncFlowPostDTO, SyncFlowBO.class);
        syncFlowBO.setSourceInfo(sourceConfigPool);
        syncFlowBO.setTargetInfo(targetConfigPool);
        syncFlowBO.setExtraPoolBOList(extraConfigPool);
        ContextHolder.set(syncFlowBO);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ContextHolder.set(syncFlowBO);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        Object response = dispatcherServiceHandler.doDispatch(syncFlowBO, true);
        ContextHolder.remove();
        return response;
    }

    public FlowMapping getFlowMapping(Integer id) {
        FlowMapping flowMapping = flowMappingService.getById(id);
        Assert.nonNull(flowMapping, "流程不存在：" + id);
        Assert.isTrue(Objects.nonNull(flowMapping.getSourceId()) || Objects.nonNull(flowMapping.getTargetId()), "系统不存在：" + id);
        Assert.isTrue(flowMapping.getStatus() == 1, "系统状态不可用");
        ContextHolder.set(GlobalConstant.FLOW_ID, id);
        return flowMapping;
    }

    public ConfigPoolBO initConfig(Long id, FlowMapping flowMapping) {
        Assert.nonNull(id, "流程不存在：" + id);
        ConfigPool configPool = configPoolMapper.selectById(id);
        Assert.nonNull(configPool, "流程不存在：" + id);
        Assert.nonNull(configPool.getId(), "流程不存在：" + configPool.getId());
        AppInfo appInfo = appInfoService.getOne(Wrappers.<AppInfo>lambdaQuery()
                .eq(AppInfo::getId, configPool.getAppId()));

        Assert.nonNull(appInfo, "流程不存在");
        Assert.nonNull(appInfo.getId(), "流程不存在：" + configPool.getId());
        Assert.isTrue(configPool.getStatus() == 1, "系统状态不可用");
        Assert.isTrue(appInfo.getStatus() == 1, "系统状态不可用");

        ConfigPoolBO configPoolBO = MapstructUtil.convert(configPool, ConfigPoolBO.class);
        configPoolBO.setMailTo(flowMapping.getMailTo());
        configPoolBO.setMailType(flowMapping.getMailType());
        
        return configPoolBO;
    }

    public List<ConfigPoolBO> initExtraConfig(String extraId) {
        if (StringUtil.hasEmpty(extraId)) {
            return null;
        }
        List<Integer> ids = Arrays.stream(extraId.split(",")).map((id) -> Integer.parseInt(id.trim())).collect(Collectors.toList());
        List<ConfigPool> configPoolBOList = configPoolMapper.queryListJoinApp(ids);
        return MapstructUtil.convert(configPoolBOList, ConfigPoolBO.class);
    }

    public Map<String, Object> listConvertMap(List<String> list) {
        if (Objects.isNull(list)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(16);
        // GET 请求的 List 转成 Map 
        list.forEach(sourceAuthInfo -> {
            String[] split = sourceAuthInfo.trim().split("-");
            if (StringUtil.hasText(split[0], split[1])) {
                map.put(split[0], split[1]);
            }
        });
        return map;
    }

}
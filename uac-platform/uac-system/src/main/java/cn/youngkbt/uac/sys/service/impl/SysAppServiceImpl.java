package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysAppMapper;
import cn.youngkbt.uac.sys.model.dto.SysAppDTO;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.SysAppVO;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVO;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_app(应用表)】的数据库操作Service实现
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {

    @Override
    public SysApp checkAppIdThenGet(String tenantId, String appId) {
        return baseMapper.selectOne(Wrappers.<SysApp>lambdaQuery()
                .eq(SysApp::getTenantId, tenantId)
                .eq(SysApp::getAppId, appId)
        );
    }

    @Override
    public SysAppVO listById(Long id) {
        SysApp sysApp = baseMapper.selectById(id);
        Assert.nonNull(sysApp, "应用不存在");
        return MapstructUtil.convert(sysApp, SysAppVO.class);
    }

    @Override
    public List<SysAppVO> queryList(SysAppDTO sysAppDTO) {
        LambdaQueryWrapper<SysApp> wrapper = buildQueryWrapper(sysAppDTO);
        List<SysApp> sysAppList = baseMapper.selectList(wrapper);
        return MapstructUtil.convert(sysAppList, SysAppVO.class);
    }

    @Override
    public TablePage<SysAppVO> listPage(SysAppDTO sysAppDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysApp> wrapper = buildQueryWrapper(sysAppDTO);
        IPage<SysApp> sysClientList = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysClientList, SysAppVO.class);
    }

    private LambdaQueryWrapper<SysApp> buildQueryWrapper(SysAppDTO sysAppDTO) {
        return Wrappers.<SysApp>lambdaQuery()
                .eq(StringUtils.hasText(sysAppDTO.getAppId()), SysApp::getAppId, sysAppDTO.getAppId())
                .eq(StringUtils.hasText(sysAppDTO.getAppCode()), SysApp::getAppCode, sysAppDTO.getAppCode())
                .eq(StringUtils.hasText(sysAppDTO.getAppName()), SysApp::getAppName, sysAppDTO.getAppName())
                .eq(Objects.nonNull(sysAppDTO.getStatus()), SysApp::getStatus, sysAppDTO.getStatus())
                .eq(StringUtils.hasText(sysAppDTO.getDeptId()), SysApp::getDeptId, sysAppDTO.getDeptId())
                .eq(StringUtils.hasText(sysAppDTO.getClientId()), SysApp::getClientId, sysAppDTO.getClientId())
                .orderByAsc(SysApp::getOrderNum);
    }

    @Override
    public List<AppTreeVO> listTreeList() {
        // TODO 是否分页
        List<SysApp> sysAppList = baseMapper.selectList(Wrappers.<SysApp>lambdaQuery()
                .select(SysApp::getAppId, SysApp::getAppName)
                .orderByAsc(SysApp::getOrderNum));

        return MapstructUtil.convert(sysAppList, AppTreeVO.class);
    }

    @Override
    public boolean insertOne(SysAppDTO sysAppDTO) {
        SysApp sysApp = MapstructUtil.convert(sysAppDTO, SysApp.class);
        return baseMapper.insert(sysApp) > 0;
    }

    @Override
    public boolean updateOne(SysAppDTO sysAppDTO) {
        SysApp sysApp = MapstructUtil.convert(sysAppDTO, SysApp.class);
        return baseMapper.updateById(sysApp) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean checkExitApp(List<String> clientIds) {
        return baseMapper.exists(Wrappers.<SysApp>lambdaQuery()
                .in(SysApp::getClientId, clientIds));
    }

    @Override
    public boolean checkAppCodeUnique(SysAppDTO sysAppDTO) {
        return baseMapper.exists(Wrappers.<SysApp>lambdaQuery()
                .eq(SysApp::getAppCode, sysAppDTO.getAppCode())
                .ne(Objects.nonNull(sysAppDTO.getAppId()), SysApp::getAppId, sysAppDTO.getAppId()));
    }
}





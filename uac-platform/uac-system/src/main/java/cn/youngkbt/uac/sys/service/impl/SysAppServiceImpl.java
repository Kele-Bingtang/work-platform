package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysAppMapper;
import cn.youngkbt.uac.sys.model.dto.SysAppDTO;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVO;
import cn.youngkbt.uac.sys.model.vo.SysAppVO;
import cn.youngkbt.uac.sys.service.SysAppService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public SysApp checkAppIdThenGet(String appId) {
        return baseMapper.selectOne(Wrappers.<SysApp>lambdaQuery().eq(SysApp::getAppId, appId));
    }

    @Override
    public SysAppVO listById(Long id) {
        SysApp sysApp = baseMapper.selectById(id);
        Assert.nonNull(sysApp, "应用不存在");
        return MapstructUtil.convert(sysApp, SysAppVO.class);
    }

    @Override
    public List<SysAppVO> listWithPage(SysAppDTO sysAppDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysApp> wrapper = Wrappers.<SysApp>lambdaQuery()
                .eq(StringUtils.hasText(sysAppDto.getAppId()), SysApp::getAppId, sysAppDto.getAppId())
                .eq(StringUtils.hasText(sysAppDto.getAppCode()), SysApp::getAppCode, sysAppDto.getAppCode())
                .eq(StringUtils.hasText(sysAppDto.getAppName()), SysApp::getAppName, sysAppDto.getAppName())
                .eq(Objects.nonNull(sysAppDto.getStatus()), SysApp::getStatus, sysAppDto.getStatus())
                .eq(StringUtils.hasText(sysAppDto.getDeptId()), SysApp::getDeptId, sysAppDto.getDeptId())
                .eq(StringUtils.hasText(sysAppDto.getClientId()), SysApp::getClientId, sysAppDto.getClientId())
                .orderByAsc(SysApp::getOrderNum);

        List<SysApp> sysClientList;
        if (Objects.isNull(pageQuery)) {
            sysClientList = baseMapper.selectList(wrapper);
        } else {
            sysClientList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysClientList, SysAppVO.class);
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
    public boolean insertOne(SysAppDTO sysAppDto) {
        SysApp sysApp = MapstructUtil.convert(sysAppDto, SysApp.class);
        return baseMapper.insert(sysApp) > 0;
    }

    @Override
    public boolean updateOne(SysAppDTO sysAppDto) {
        SysApp sysApp = MapstructUtil.convert(sysAppDto, SysApp.class);
        return baseMapper.updateById(sysApp) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}





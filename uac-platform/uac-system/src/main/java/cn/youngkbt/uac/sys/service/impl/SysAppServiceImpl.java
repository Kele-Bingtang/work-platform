package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysAppMapper;
import cn.youngkbt.uac.sys.model.dto.SysAppDto;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.SysAppTreeVo;
import cn.youngkbt.uac.sys.model.vo.SysAppVo;
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
    public SysAppVo listById(Long id) {
        SysApp sysApp = baseMapper.selectById(id);
        Assert.nonNull(sysApp, "应用不存在");
        SysAppVo result = MapstructUtil.convert(sysApp, SysAppVo.class);
        result.setGrantTypeList(List.of(result.getGrantTypes().split(",")));
        return result;
    }

    @Override
    public List<SysAppVo> queryListWithPage(SysAppDto sysAppDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysApp> wrapper = Wrappers.<SysApp>lambdaQuery()
                .eq(StringUtils.hasText(sysAppDto.getAppId()), SysApp::getAppId, sysAppDto.getAppId())
                .eq(StringUtils.hasText(sysAppDto.getAppCode()), SysApp::getAppCode, sysAppDto.getAppCode())
                .eq(StringUtils.hasText(sysAppDto.getAppName()), SysApp::getAppName, sysAppDto.getAppName())
                .eq(Objects.nonNull(sysAppDto.getStatus()), SysApp::getStatus, sysAppDto.getStatus())
                .eq(StringUtils.hasText(sysAppDto.getDeptId()), SysApp::getDeptId, sysAppDto.getDeptId())
                .eq(StringUtils.hasText(sysAppDto.getClientId()), SysApp::getClientId, sysAppDto.getClientId())
                .in(Objects.nonNull(sysAppDto.getGrantTypeList()), SysApp::getGrantTypes, sysAppDto.getGrantTypeList())
                .orderByAsc(SysApp::getOrderNum);

        List<SysApp> sysClientList;
        if (Objects.isNull(pageQuery)) {
            sysClientList = baseMapper.selectList(wrapper);
        } else {
            sysClientList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        List<SysAppVo> result = MapstructUtil.convert(sysClientList, SysAppVo.class);
        result.forEach(r -> r.setGrantTypeList(List.of(r.getGrantTypes().split(","))));
        return result;
    }

    @Override
    public List<SysAppTreeVo> listTreeList() {
        // TODO 是否分页
        List<SysApp> sysAppList = baseMapper.selectList(Wrappers.<SysApp>lambdaQuery()
                .select(SysApp::getAppId, SysApp::getAppName));
        
        return MapstructUtil.convert(sysAppList, SysAppTreeVo.class);
    }

    @Override
    public boolean insertOne(SysAppDto sysAppDto) {
        SysApp sysApp = MapstructUtil.convert(sysAppDto, SysApp.class);
        sysApp.setGrantTypes(String.join(",", sysAppDto.getGrantTypeList()));
        return baseMapper.insert(sysApp) > 0;
    }

    @Override
    public boolean updateOne(SysAppDto sysAppDto) {
        SysApp sysApp = MapstructUtil.convert(sysAppDto, SysApp.class);
        sysApp.setGrantTypes(String.join(",", sysAppDto.getGrantTypeList()));
        return baseMapper.updateById(sysApp) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}





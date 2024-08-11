package cn.youngkbt.file.system.service.impl;

import cn.youngkbt.file.system.mapper.AppInfoMapper;
import cn.youngkbt.file.system.model.dto.AppInfoDTO;
import cn.youngkbt.file.system.model.po.AppInfo;
import cn.youngkbt.file.system.model.po.FileInfo;
import cn.youngkbt.file.system.model.vo.AppInfoVO;
import cn.youngkbt.file.system.model.vo.FileInfoVO;
import cn.youngkbt.file.system.service.AppInfoService;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-08-06 21:08:58
 * @note 针对表「t_app_info（app 配置表）」的数据库操作 Service 实现
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

    @Override
    public TablePage<AppInfoVO> listPage(AppInfoDTO appInfoDTO, PageQuery pageQuery) {
        Page<AppInfo> appInfoPage = baseMapper.selectPage(pageQuery.buildPage(), Wrappers.<AppInfo>lambdaQuery()
                .eq(StringUtil.hasText(appInfoDTO.getAppId()), AppInfo::getAppId, appInfoDTO.getAppId())
                .like(StringUtil.hasText(appInfoDTO.getAppName()), AppInfo::getAppName, appInfoDTO.getAppName())
                .eq(StringUtil.hasText(appInfoDTO.getOwner()), AppInfo::getOwner, appInfoDTO.getOwner())
        );

        return TablePage.build(appInfoPage, AppInfoVO.class);

    }

    @Override
    public boolean addApp(AppInfoDTO appInfoDTO) {
        AppInfo appInfo = MapstructUtil.convert(appInfoDTO, AppInfo.class);

        return baseMapper.insert(appInfo) > 0;
    }

    @Override
    public boolean editApp(AppInfoDTO appInfoDTO) {
        AppInfo appInfo = MapstructUtil.convert(appInfoDTO, AppInfo.class);

        return baseMapper.update(appInfo, Wrappers.<AppInfo>lambdaQuery()
                .eq(AppInfo::getAppId, appInfoDTO.getAppId())) > 0;
    }

    @Override
    public boolean removeApp(String appId) {
        return baseMapper.delete(Wrappers.<AppInfo>lambdaQuery()
                .eq(AppInfo::getAppId, appId)) > 0;
    }
}





package cn.youngkbt.file.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.file.system.model.po.AppInfo;
import cn.youngkbt.file.system.service.AppInfoService;
import cn.youngkbt.file.system.mapper.AppInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-08-06 21:08:58
 * @note 针对表「t_app_info（app 配置表）」的数据库操作 Service 实现
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

}





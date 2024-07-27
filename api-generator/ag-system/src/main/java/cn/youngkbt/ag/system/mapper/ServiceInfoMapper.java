package cn.youngkbt.ag.system.mapper;

import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.ServiceInfoVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author Kele-Bingtang
 * @date 2024-23-22 00:23:11
 * @note 针对表「t_service（服务表）」的数据库操作 Mapper
 */
public interface ServiceInfoMapper extends BaseMapper<ServiceInfo> {

    Page<ServiceInfoVO> selectPageData(Page<ServiceInfo> objectPage, @Param(Constants.WRAPPER) Wrapper<ServiceInfo> queryWrapper);
}





package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysAppDto;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.SysAppVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_app(应用表)】的数据库操作Service
 */
public interface SysAppService extends IService<SysApp> {

    SysApp checkAppIdThenGet(String appId);

    SysAppVo queryById(Long id);

    List<SysAppVo> queryListWithPage(SysAppDto sysAppDto, PageQuery pageQuery);

    Boolean insertOne(SysAppDto sysAppDto);

    Boolean updateOne(SysAppDto sysAppDto);

    Boolean removeOne(List<Long> ids);

}

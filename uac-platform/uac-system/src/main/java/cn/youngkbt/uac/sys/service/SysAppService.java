package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysAppDto;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVo;
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

    SysAppVo listById(Long id);

    List<SysAppVo> queryListWithPage(SysAppDto sysAppDto, PageQuery pageQuery);

    boolean insertOne(SysAppDto sysAppDto);

    boolean updateOne(SysAppDto sysAppDto);

    boolean removeBatch(List<Long> ids);

    List<AppTreeVo> listTreeList();
}

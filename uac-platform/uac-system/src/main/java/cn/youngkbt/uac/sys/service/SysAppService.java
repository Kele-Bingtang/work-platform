package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysAppDTO;
import cn.youngkbt.uac.sys.model.po.SysApp;
import cn.youngkbt.uac.sys.model.vo.SysAppVO;
import cn.youngkbt.uac.sys.model.vo.extra.AppTreeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_app(应用表)】的数据库操作Service
 */
public interface SysAppService extends IService<SysApp> {
    /**
     * 检查应用 ID 是否存在，如果存在则返回应用信息
     *
     * @param appId 应用 ID
     * @return 应用信息
     */
    SysApp checkAppIdThenGet(String appId);

    /**
     * 根据主键获取应用信息
     *
     * @param id 主键
     * @return 应用信息
     */
    SysAppVO listById(Long id);

    /**
     * 通过条件查询 App 清单列表
     *
     * @param sysAppDto 应用信息查询条件
     * @param pageQuery 分页查询条件
     * @return 应用信息清单
     */
    List<SysAppVO> listWithPage(SysAppDTO sysAppDto, PageQuery pageQuery);

    /**
     * 新增一条应用信息
     *
     * @param sysAppDto 应用信息
     * @return 是否成功
     */
    boolean insertOne(SysAppDTO sysAppDto);

    /**
     * 更新一条应用信息
     *
     * @param sysAppDto 应用信息
     * @return 是否成功
     */
    boolean updateOne(SysAppDTO sysAppDto);

    /**
     * 批量删除应用信息
     *
     * @param ids 应用 ID 清单
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 获取应用树形清单
     *
     * @return 应用树形清单
     */
    List<AppTreeVO> listTreeList();
}

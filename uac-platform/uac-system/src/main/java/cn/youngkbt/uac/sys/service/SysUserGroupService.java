package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.extra.UserGroupBindUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user_group(用户组表)】的数据库操作Service
 */
public interface SysUserGroupService extends IService<SysUserGroup> {

    List<SysUserGroupVO> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery);

    List<SysUserGroupVO> listUserGroupByUserId(String appId, String userId, PageQuery pageQuery);

    List<UserGroupBindUserVO> listUserGroupWithDisabledByUserId(String appId, String userId);
}

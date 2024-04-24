package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import cn.youngkbt.uac.sys.model.po.SysDept;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-22-12 02:22:04
 * @note 针对表 t_role_dept_link(角色关联部门表)的数据库操作 Mapper
*/
public interface RoleDeptLinkMapper extends BaseMapper<RoleDeptLink> {

    List<SysDept> listDeptListByRoleId(@Param(Constants.WRAPPER) Wrapper<RoleDeptLink> queryWrapper);
}





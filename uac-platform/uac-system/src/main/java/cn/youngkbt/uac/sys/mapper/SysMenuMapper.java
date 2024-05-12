package cn.youngkbt.uac.sys.mapper;

import cn.youngkbt.uac.sys.model.po.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-24-12 02:24:13
 * @note 针对表 t_sys_menu(菜单表)的数据库操作 Mapper
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listMenuListByUserId(@Param("appId") String appId, @Param("userId") String userId, @Param("onlyGetMenu") boolean onlyGetMenu);
}





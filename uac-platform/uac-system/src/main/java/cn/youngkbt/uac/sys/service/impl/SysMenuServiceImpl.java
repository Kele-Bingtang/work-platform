package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysMenuMapper;
import cn.youngkbt.uac.sys.model.dto.SysMenuDto;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVo;
import cn.youngkbt.uac.sys.service.SysMenuService;
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
 * @note 针对表【t_sys_menu(菜单表)】的数据库操作Service实现
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public SysMenuVo queryById(Long id) {
        SysMenu sysMenu = baseMapper.selectById(id);
        Assert.nonNull(sysMenu, "菜单不存在");
        return MapstructUtil.convert(sysMenu, SysMenuVo.class);
    }

    @Override
    public List<SysMenuVo> queryListWithPage(SysMenuDto sysMenuDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysMenu> wrapper = Wrappers.<SysMenu>lambdaQuery()
                .eq(StringUtils.hasText(sysMenuDto.getMenuCode()), SysMenu::getMenuCode, sysMenuDto.getMenuCode())
                .eq(Objects.nonNull(sysMenuDto.getStatus()), SysMenu::getStatus, sysMenuDto.getStatus())
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getOrderNum);

        List<SysMenu> sysMenuList;
        if (Objects.isNull(pageQuery)) {
            sysMenuList = baseMapper.selectList(wrapper);
        } else {
            sysMenuList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysMenuList, SysMenuVo.class);
    }

    /**
     * 构建前端需要的路由菜单
     */
    @Override
    public List<SysMenuVo> buildMenuTree(List<SysMenu> sysMenuList) {
        
        return null;
    }

    @Override
    public Boolean checkMenuNameUnique(SysMenuDto sysMenuDto) {
        boolean exist = baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuName, sysMenuDto.getMenuName())
                .eq(SysMenu::getParentId, sysMenuDto.getParentId())
                .ne(ObjectUtil.isNotNull(sysMenuDto.getMenuId()), SysMenu::getMenuId, sysMenuDto.getMenuId()));

        return !exist;
    }

    @Override
    public Boolean hasChild(Long menuId) {
        return baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParam, menuId));
    }

    @Override
    public Boolean checkMenuExistRole(Long menuId) {
        
        return null;
    }

    @Override
    public Boolean insertOne(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDto, SysMenu.class);
        return baseMapper.insert(sysMenu) > 0;
    }

    @Override
    public Boolean updateOne(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDto, SysMenu.class);
        return baseMapper.updateById(sysMenu) > 0;
    }

    @Override
    public Boolean removeOne(Long menuId) {
        return baseMapper.delete(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuId, menuId)) > 0;
    }

    @Override
    public Boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

}





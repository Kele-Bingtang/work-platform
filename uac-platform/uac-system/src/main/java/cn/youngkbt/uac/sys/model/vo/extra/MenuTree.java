package cn.youngkbt.uac.sys.model.vo.extra;

import cn.youngkbt.uac.sys.model.po.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/2/22 22:07
 * @note
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysMenu.class, convertGenerate = false)
public class MenuTree extends SysMenu {
    private List<SysMenu> children = new ArrayList<>();
}

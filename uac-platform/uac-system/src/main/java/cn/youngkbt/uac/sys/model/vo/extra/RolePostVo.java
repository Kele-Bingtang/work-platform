package cn.youngkbt.uac.sys.model.vo.extra;

import cn.youngkbt.uac.sys.model.vo.SysPostVO;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/2/5 23:25
 * @note
 */
@Data
@Accessors(chain = true)
public class RolePostVo {
    private List<SysPostVO> postList;
    private List<SysRoleVO> roleList;
}

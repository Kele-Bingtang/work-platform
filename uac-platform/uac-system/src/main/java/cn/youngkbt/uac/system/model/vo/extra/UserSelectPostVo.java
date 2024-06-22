package cn.youngkbt.uac.system.model.vo.extra;

import cn.youngkbt.uac.system.model.vo.SysPostVO;
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
public class UserSelectPostVo {
    private List<SysPostVO> postList;
    private List<String> postIds;
}

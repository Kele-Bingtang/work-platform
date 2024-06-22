package cn.youngkbt.ag.system.model.vo.router;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/4/28 00:02:27
 * @note
 */
@Data
public class Transition {
    /**
     * 当前路由动画效果
     */
    private String name;
    /**
     * 进场动画
     */
    private String enterTransition;
    /**
     * 离场动画
     */
    private String leaveTransition;
}

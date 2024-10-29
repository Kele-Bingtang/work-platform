package cn.youngkbt.integrate.system.service;

import cn.youngkbt.integrate.system.model.bo.ConfigPoolBO;

/**
 * @author Kele-Bingtang
 * @date 2024/10/28 00:30:35
 * @note
 */
public interface ServiceHandlerAdapt extends FlowService {
    boolean match(ConfigPoolBO config);
}
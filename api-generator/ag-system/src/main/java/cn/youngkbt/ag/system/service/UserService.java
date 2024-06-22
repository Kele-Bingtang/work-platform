package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.UserDTO;
import cn.youngkbt.ag.system.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_user（用户表）」的数据库操作 Service
 */
public interface UserService extends IService<User> {

    boolean updateByUserId(UserDTO sysUserDTO);
}

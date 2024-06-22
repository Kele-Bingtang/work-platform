package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.model.dto.UserDTO;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.ag.system.model.po.User;
import cn.youngkbt.ag.system.service.UserService;
import cn.youngkbt.ag.system.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_user（用户表）」的数据库操作 Service 实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean updateByUserId(UserDTO sysUserDTO) {
        User user = MapstructUtil.convert(sysUserDTO, User.class);
        user.setPassword(null);
        return baseMapper.update(user, Wrappers.<User>lambdaUpdate()
                .eq(User::getUsername, sysUserDTO.getUsername())) > 0;
    }
}





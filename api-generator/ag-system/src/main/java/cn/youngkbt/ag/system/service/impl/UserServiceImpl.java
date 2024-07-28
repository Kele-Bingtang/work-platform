package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.UserMapper;
import cn.youngkbt.ag.system.model.dto.UserDTO;
import cn.youngkbt.ag.system.model.po.User;
import cn.youngkbt.ag.system.model.vo.UserVO;
import cn.youngkbt.ag.system.service.UserService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_user（用户表）」的数据库操作 Service 实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${default.password}")
    private String password;

    @Override
    public List<UserVO> listByKeyword(String keyword) {
        List<User> userList = baseMapper.selectList(Wrappers.<User>lambdaQuery()
                .like(User::getUsername, keyword)
                .or()
                .like(User::getNickname, keyword)
        );

        return MapstructUtil.convert(userList, UserVO.class);
    }

    @Override
    public boolean updateByUsername(UserDTO userDTO) {
        User user = MapstructUtil.convert(userDTO, User.class);
        user.setPassword(null);
        return baseMapper.update(user, Wrappers.<User>lambdaUpdate()
                .eq(User::getUsername, userDTO.getUsername())) > 0;
    }

    @Override
    public boolean checkUsernameUnique(UserDTO userDTO) {
        return baseMapper.exists(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, userDTO.getUsername())
                .ne(Objects.nonNull(userDTO.getId()), User::getId, userDTO.getId()));
    }

    @Override
    public Boolean register(UserDTO userDTO) {
        User user = MapstructUtil.convert(userDTO, User.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setRegisterTime(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (Objects.isNull(user.getPassword())) {
            // 默认密码
            user.setPassword(passwordEncoder.encode(password));
        }
        return baseMapper.insert(user) > 0;
    }

    @Override
    public Boolean edit(UserDTO userDTO) {
        User user = MapstructUtil.convert(userDTO, User.class);
        // userId、username、password 不允许编辑
        user.setUserId(null);
        user.setUsername(null);
        user.setPassword(null);
        return baseMapper.updateById(user) > 0;
    }
}





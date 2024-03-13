package cn.youngkbt.uac.auth.convertor;

import cn.youngkbt.core.base.BaseMapperConvertor;
import cn.youngkbt.uac.auth.model.vo.LoginVO;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Kele-Bingtang
 * @date 2023/11/16 0:45
 * @note
 */
@Mapper
public interface LoginBOToVOConvertor extends BaseMapperConvertor<LoginSuccessBO, LoginVO> {
    
    LoginBOToVOConvertor INSTANCE = Mappers.getMapper(LoginBOToVOConvertor.class);
}

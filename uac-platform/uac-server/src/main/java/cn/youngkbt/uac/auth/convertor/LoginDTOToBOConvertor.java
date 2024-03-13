package cn.youngkbt.uac.auth.convertor;

import cn.youngkbt.uac.auth.model.dto.LoginUserDTO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.sys.model.po.SysApp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Kele-Bingtang
 * @date 2023/11/14 23:23
 * @note
 */
@Mapper
public interface LoginDTOToBOConvertor {
    LoginDTOToBOConvertor INSTANCE = Mappers.getMapper(LoginDTOToBOConvertor.class);

    @Mapping(source = "loginUserDto.appId", target = "appId")
    @Mapping(source = "loginUserDto.tenantId", target = "tenantId")
    LoginUserBO convert(LoginUserDTO loginUserDto, SysApp sysApp);
}

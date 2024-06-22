package cn.youngkbt.uac.auth.convertor;

import cn.youngkbt.uac.auth.model.dto.LoginUserDTO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.system.model.po.SysApp;
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

    @Mapping(source = "loginUserDTO.appId", target = "appId")
    @Mapping(source = "loginUserDTO.tenantId", target = "tenantId")
    LoginUserBO convert(LoginUserDTO loginUserDTO, SysApp sysApp);
}

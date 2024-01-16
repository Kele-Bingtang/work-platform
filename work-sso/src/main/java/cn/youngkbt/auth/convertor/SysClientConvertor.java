package cn.youngkbt.auth.convertor;

import cn.youngkbt.core.base.BaseMapperConvertor;
import cn.youngkbt.auth.dto.OpenApiClientDTO;
import cn.youngkbt.auth.entity.SysAuthClient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Kele-Bingtang
 * @date 2023-41-24 02:41:38
 * @note 
*/
@Mapper
public interface SysClientConvertor extends BaseMapperConvertor<OpenApiClientDTO, SysAuthClient> {
    SysClientConvertor INSTANCE = Mappers.getMapper(SysClientConvertor.class);
}
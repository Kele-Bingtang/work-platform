package cn.youngkbt.uac.sys.convertor;

import cn.youngkbt.core.base.BaseMapperConvertor;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.model.vo.SysClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 23:47
 * @note
 */
@Mapper
public interface SysClientToVoConvertor extends BaseMapperConvertor<SysClient, SysClientVo> {
    SysClientToVoConvertor INSTANCE = Mappers.getMapper(SysClientToVoConvertor.class);
}

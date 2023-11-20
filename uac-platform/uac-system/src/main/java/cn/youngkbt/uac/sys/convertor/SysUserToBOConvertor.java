package cn.youngkbt.uac.sys.convertor;

import cn.youngkbt.core.base.BaseMapperConvertor;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.uac.sys.model.po.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Kele-Bingtang
 * @date 2023/11/16 1:12
 * @note
 */
@Mapper
public interface SysUserToBOConvertor extends BaseMapperConvertor<SysUser, SysUserBO> {
    SysUserToBOConvertor INSTANCE = Mappers.getMapper(SysUserToBOConvertor.class);
}
